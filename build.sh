#!/bin/bash
# ============================================================================
# EaglerCraft 26.2 Port — Build Script
# ============================================================================
# This script runs the full diddy62626 EaglercraftX build pipeline against
# the Minecraft 26.2 JAR (instead of the 26.2 JAR it was originally written
# for).
#
# REQUIREMENTS:
#   - Java 26 (JDK 25 has a compiler bug that breaks DFU patches; JDK 26 fixes it)
#     Download from: https://www.azul.com/downloads/?version=jdk-26-latest&package=jdk
#   - 16 GB RAM (TeaVM compile uses 12 GB Gradle heap)
#   - 5 GB free disk
#   - Internet access (downloads TeaVM 0.15.0 + dependencies, ~500 MB)
#
# TIME: ~30 minutes (decompile 5-15 min + TeaVM compile 15-20 min + packaging)
#
# WHAT THIS DOES:
#   1. Patches the broken Vineflower download URL in build_init.sh
#   2. Runs build_init.sh — decompiles the 26.2 JAR with Vineflower
#   3. Downloads MC library dependencies (Netty, Guava, DFU, etc.)
#   4. Patches TeaVM + DFU + ASM JARs (Python scripts in scripts/)
#   5. Compiles TeaVM classlib patches (java.util.concurrent, java.nio.file, etc.)
#   6. Runs TeaVM compilation — produces classes.js (~17 MB)
#   7. Post-processes classes.js (default method workaround patches)
#   8. Compiles EPK assets from the 26.2 JAR's assets/ directory
#   9. Assembles final output in public/ (index.html + classes.js + assets.epk)
#
# EXPECTED FAILURES:
#   Some Eaglercraft patches written for 26.2 may fail to apply against 26.2
#   source. The build will report which patches failed. You'll need to manually
#   fix those — usually it's a method signature change or a moved class.
#   Common 26.2 → 26.2 changes that break patches:
#     - New blocks/items (cinnabar, sulfur, sulfur_cube) — registry additions
#     - Protocol 775 → 776 packet changes
#     - Data version 4790 → 4903 fixer additions
#
# USAGE:
#   ./build.sh            # full build
#   ./build.sh --debug    # debug build (source maps, no obfuscation)
#   ./build.sh --release  # release build (optimized, obfuscated, minified)
# ============================================================================

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

# ── Check Java version ────────────────────────────────────────────────────────
if ! command -v java &>/dev/null; then
    echo "ERROR: Java not found. Install Java 26 from:"
    echo "  https://www.azul.com/downloads/?version=jdk-26-latest&package=jdk"
    exit 1
fi

JAVA_MAJOR=$(java -version 2>&1 | head -1 | sed 's/.*"\([0-9]*\)\..*/\1/')
echo "Java version: $JAVA_MAJOR"
if [ "$JAVA_MAJOR" -lt 26 ]; then
    echo "ERROR: Java 26+ required (have $JAVA_MAJOR). JDK 25 has a compiler bug."
    echo "Download Java 26 from: https://www.azul.com/downloads/?version=jdk-26-latest&package=jdk"
    exit 1
fi

# ── Check RAM ─────────────────────────────────────────────────────────────────
RAM_GB=$(free -g 2>/dev/null | awk '/^Mem:/{print $2}' || echo 0)
echo "Available RAM: ${RAM_GB} GB"
if [ "$RAM_GB" -lt 12 ]; then
    echo "WARNING: Less than 12 GB RAM. TeaVM compile may OOM."
    echo "         If it fails, run on a machine with 16 GB+ RAM."
fi

# ── Check for the 26.2 JAR ────────────────────────────────────────────────────
if [ ! -f minecraft-26.2.jar ]; then
    echo "ERROR: minecraft-26.2.jar not found in $SCRIPT_DIR"
    echo "       Place the vanilla 26.2 client JAR here."
    exit 1
fi

# ── Apply 26.2 patches to Eaglercraft source ─────────────────────────────────
# This updates SharedConstants.java (version numbers), PlatformApplication (VERSION
# string), and all 26.1.2 string literals across 54+ Java files + 44+ script files.
# The new 26.2 blocks/items/mobs/packets come from the JAR automatically — no
# patching needed for those.
echo "Applying 26.2 patches to Eaglercraft source..."
python3 patch_for_262.py || {
    echo "ERROR: patch_for_262.py failed"
    exit 1
}

# ── Fix the broken Vineflower download URL in build_init.sh ───────────────────
# The upstream script downloads from GitHub releases, which redirects and fails.
# Replace with the Maven Central URL.
VINEFLOWER_URL="https://repo1.maven.org/maven2/org/vineflower/vineflower/1.10.1/vineflower-1.10.1.jar"
if ! grep -q "repo1.maven.org" build_init.sh 2>/dev/null; then
    echo "Patching Vineflower download URL in build_init.sh..."
    sed -i "s|https://github.com/Vineflower/vineflower/releases/latest/download/vineflower-1.10.1.jar|$VINEFLOWER_URL|g" build_init.sh
fi

# Pre-download Vineflower so we can verify it works
mkdir -p buildtools/lib
if [ ! -f buildtools/lib/vineflower-1.10.1.jar ] || [ "$(stat -c%s buildtools/lib/vineflower-1.10.1.jar)" -lt 1000000 ]; then
    echo "Downloading Vineflower 1.10.1..."
    curl -sL -o buildtools/lib/vineflower-1.10.1.jar "$VINEFLOWER_URL"
fi

# ── Make scripts executable ───────────────────────────────────────────────────
chmod +x gradlew build_*.sh CompileLatestClient.sh run_chmod.sh 2>/dev/null || true
[ -f run_chmod.sh ] && ./run_chmod.sh 2>/dev/null || true

# ── Copy 26.2 JAR to expected locations ──────────────────────────────────────
mkdir -p sources/libs
cp minecraft-26.2.jar sources/libs/minecraft-26.2.jar

# ── Step 1: Initialize workspace (decompile + patches) ────────────────────────
echo ""
echo "=== Step 1: Initialize workspace ==="
echo "This decompiles the 26.2 JAR with Vineflower (5-15 min)..."
echo ""
./build_init.sh --jar "$SCRIPT_DIR/minecraft-26.2.jar" || {
    echo "build_init.sh failed."
    echo "If Vineflower OOM'd, increase the heap in build_init.sh (search for -Xmx2G)."
    exit 1
}

# ── Step 2: Download MC library dependencies ─────────────────────────────────
echo ""
echo "=== Step 2: Download MC library dependencies ==="
# The CI workflow does this with a Python script. Reproduce it here.
mkdir -p sources/libs
python3 -c "
import urllib.request, json, os
# 26.2 version manifest URL
manifest_url = 'https://piston-meta.mojang.com/v1/packages/4c3cd3500ce8b9ea104c358a784634fedb2a610f/26.2.json'
try:
    with urllib.request.urlopen(manifest_url) as r:
        data = json.loads(r.read())
except Exception as e:
    print(f'WARNING: Could not fetch 26.2 manifest: {e}')
    print('Falling back to 26.2 manifest (libraries are mostly the same)...')
    with urllib.request.urlopen('https://piston-meta.mojang.com/v1/packages/978e6c0dd077d0f8d5d53887454928f1d3687ba8/26.2.json') as r:
        data = json.loads(r.read())

libs_to_download = [
    'com.mojang:logging', 'com.mojang:brigadier', 'com.mojang:datafixerupper',
    'com.mojang:authlib', 'com.mojang:blocklist', 'com.mojang:patchy',
    'com.mojang:text2speech', 'com.mojang:jtracy',
    'com.google.code.gson:gson', 'com.google.guava:guava', 'com.google.guava:failureaccess',
    'org.apache.commons:commons-lang3', 'org.apache.commons:commons-compress',
    'commons-io:commons-io', 'commons-codec:commons-codec',
    'it.unimi.dsi:fastutil',
    'org.apache.logging.log4j:log4j-api', 'org.apache.logging.log4j:log4j-core',
    'org.jspecify:jspecify',
    'io.netty:netty-buffer', 'io.netty:netty-common', 'io.netty:netty-transport',
    'io.netty:netty-codec-base', 'io.netty:netty-codec-compression', 'io.netty:netty-handler',
    'net.sf.jopt-simple:jopt-simple'
]
for lib in data.get('libraries', []):
    name = lib.get('name', '')
    if 'natives-' in name:
        continue
    should_download = any(name.startswith(prefix) for prefix in libs_to_download)
    if not should_download:
        continue
    artifact = lib.get('downloads', {}).get('artifact', {})
    url = artifact.get('url', '')
    if url:
        filename = url.split('/')[-1]
        if os.path.exists(f'sources/libs/{filename}'):
            continue
        print(f'Downloading {filename}...')
        for attempt in range(5):
            try:
                urllib.request.urlretrieve(url, f'sources/libs/{filename}')
                break
            except Exception as e:
                print(f'  Attempt {attempt+1} failed: {e}')
                if attempt < 4:
                    import time; time.sleep(5)
                else:
                    raise
print('Done downloading libraries')
"

# ── Step 3: Patch TeaVM, DFU, ASM JARs ───────────────────────────────────────
echo ""
echo "=== Step 3: Patch TeaVM / DFU / ASM JARs ==="
./gradlew --no-daemon :sources:dependencies --configuration teavmClasspath 2>/dev/null || true

TEAVM_CORE=$(find ~/.gradle/caches -name "teavm-core-0.15.0.jar" -not -name "*sources*" -type f 2>/dev/null | head -1)
if [ -n "$TEAVM_CORE" ]; then
    echo "Patching teavm-core..."
    python3 scripts/patch_teavm_jar.py "$TEAVM_CORE" "$TEAVM_CORE.tmp"
    mv "$TEAVM_CORE.tmp" "$TEAVM_CORE"
fi

TEAVM_CL=$(find ~/.gradle/caches -name "teavm-classlib-0.15.0.jar" -not -name "*sources*" -type f 2>/dev/null | head -1)
if [ -n "$TEAVM_CL" ]; then
    echo "Patching teavm-classlib..."
    python3 scripts/patch_classlib_jar.py "$TEAVM_CL" "$TEAVM_CL.tmp"
    mv "$TEAVM_CL.tmp" "$TEAVM_CL"
fi

DFU_JAR=$(find sources/libs ~/.gradle/caches -name "datafixerupper-*.jar" -not -name "*sources*" -type f 2>/dev/null | head -1)
if [ -n "$DFU_JAR" ]; then
    echo "Creating DFU patches..."
    python3 scripts/patch_dfu_final.py "$DFU_JAR" sources/teavm/patch/dfu-classes
    echo "Patching DFU jar..."
    python3 scripts/patch_dfu_jar.py "$DFU_JAR" "$DFU_JAR.tmp"
    mv "$DFU_JAR.tmp" "$DFU_JAR"
fi

TEAVM_ASM=$(find ~/.gradle/caches -name "teavm-relocated-libs-asm-0.15.0.jar" -not -name "*sources*" -type f 2>/dev/null | head -1)
if [ -n "$TEAVM_ASM" ]; then
    echo "Patching teavm-asm..."
    python3 scripts/patch_teavm_asm.py "$TEAVM_ASM" "$TEAVM_ASM.tmp"
    mv "$TEAVM_ASM.tmp" "$TEAVM_ASM"
fi

# ── Step 4: Build TeaVM classlib patches ─────────────────────────────────────
echo ""
echo "=== Step 4: Build TeaVM classlib patches ==="
mkdir -p sources/teavm/patch/classes
cd sources/teavm/patch
javac --release 25 \
    --patch-module java.base=$(pwd) \
    -d classes \
    $(find java -name "*.java" -type f)
jar cf ../../libs/teavm-patches.jar -C classes .
cd "$SCRIPT_DIR"
echo "teavm-patches.jar built ($(stat -c%s sources/libs/teavm-patches.jar) bytes)"

# ── Step 5: Compile TeaVM JavaScript ─────────────────────────────────────────
echo ""
echo "=== Step 5: TeaVM compilation (15-20 min) ==="
echo "This is the long step. Don't interrupt."
MODE_ARGS="${1:---development}"
./gradlew --no-daemon --rerun-tasks --no-build-cache --stacktrace :sources:generateJavaScript $MODE_ARGS 2>&1 | tee teavm.log

if grep -q "BUILD SUCCESSFUL" teavm.log; then
    echo "TeaVM compilation succeeded!"
    CLASSES_SIZE=$(stat -c%s sources/build/generated/teavm/js/classes.js 2>/dev/null || echo 0)
    echo "classes.js size: $CLASSES_SIZE bytes"

    echo "Patching classes.js with default method workaround..."
    python3 scripts/patch_default_methods.py \
        sources/build/generated/teavm/js/classes.js \
        sources/build/generated/teavm/js/classes.js
else
    echo "TeaVM compilation FAILED. Last 200 lines:"
    tail -200 teavm.log
    exit 1
fi

# ── Step 6: Compile EPK assets ───────────────────────────────────────────────
echo ""
echo "=== Step 6: Compile EPK assets from 26.2 JAR ==="
# Extract assets from the 26.2 JAR into the expected location
mkdir -p sources/setup/workspace_template/src/resources
cd sources/setup/workspace_template/src/resources
jar xf "$SCRIPT_DIR/minecraft-26.2.jar" assets data
cd "$SCRIPT_DIR"

# Run the EPK compiler
if [ -f sources/setup/workspace_template/CompileEPK.sh ]; then
    chmod +x sources/setup/workspace_template/CompileEPK.sh
    (cd sources/setup/workspace_template && ./CompileEPK.sh) || {
        echo "WARNING: EPK compile failed. Building minimal EPK..."
        printf 'EAGPK!!\x00\x00\x00\x00\x00\x00\x00\x00' > public/assets.epk
    }
fi

# ── Step 7: Assemble final output ────────────────────────────────────────────
echo ""
echo "=== Step 7: Assemble final output ==="
mkdir -p output
cp sources/build/generated/teavm/js/classes.js output/classes.js
[ -f sources/build/generated/teavm/js/classes.js.map ] && cp sources/build/generated/teavm/js/classes.js.map output/
[ -f output/assets.epk ] || cp public/assets.epk output/ 2>/dev/null || true
[ -f sources/setup/workspace_template/output/assets.epk ] && cp sources/setup/workspace_template/output/assets.epk output/
cp public/index.html output/

echo ""
echo "=== BUILD COMPLETE ==="
echo "Output in: $SCRIPT_DIR/output/"
ls -la output/
echo ""
echo "To run: cd output && python3 -m http.server 8080"
echo "Then open: http://localhost:8080"
echo ""
echo "NOTE: If some patches failed against 26.2, you'll see compile errors"
echo "in step 5. Fix the failing patches in sources/teavm/java/ and re-run."
