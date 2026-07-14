#!/usr/bin/env python3
"""
Patch the diddy62626 EaglercraftX source for 26.2.

This script updates all 26.2-specific references in the Eaglercraft patches
to their 26.2 values. The new 26.2 blocks/items/mobs/packets come from the
decompiled 26.2 JAR automatically (no patching needed for those — they're
plain Java classes that compile via TeaVM).

What this script patches:
  1. SharedConstants.java — version numbers (WORLD_VERSION, PROTOCOL_VERSION, etc.)
  2. PlatformApplication.java — VERSION string
  3. All "26.2" string literals in comments and crash messages → "26.2"
  4. ClientMain.java — crash screen title
  5. gradle.properties — already bumped (projectName, minecraftVersion, protocolVersion)

What this script does NOT patch:
  - The new 26.2 blocks/items/mobs/packets themselves — they're in the JAR
  - Registry conflicts — if 26.2 moved a class, the build will report it
  - Protocol codec changes — if 26.2 changed packet serialization, manual fix needed

Run this BEFORE ./build.sh
"""

import re
import sys
from pathlib import Path

ROOT = Path('/home/z/my-project/eaglercraft-26.2-src')

# 26.2 → 26.2 version mapping (from minecraft.wiki)
VERSION_MAP = {
    '26.2': '26.2',
    '4790':   '4903',  # world_version
    '775':    '776',   # protocol_version
    '3955':   '4903',  # data_version (was incorrectly 3955 in patch; should match world_version)
    '84':     '88',    # resource_pack_format (major)
    '101':    '107',   # data_pack_format (major)
}

# Files that need explicit version-number updates
FILES_TO_PATCH = [
    # SharedConstants — the canonical version source
    'sources/teavm/java/net/minecraft/SharedConstants.java',
    # Platform layer — version string
    'sources/teavm/java/net/lax1dude/eaglercraft/v2_6/internal/PlatformApplication.java',
    # Crash screen / startup messages
    'sources/teavm/java/net/lax1dude/eaglercraft/v2_6/internal/teavm/ClientMain.java',
    'sources/teavm/java/net/lax1dude/eaglercraft/v2_6/internal/teavm/MainClass.java',
    # Comment-only changes (cosmetic but worth updating)
    'sources/teavm/java/net/lax1dude/eaglercraft/v2_6/internal/PlatformFilesystem.java',
    'sources/teavm/java/net/lax1dude/eaglercraft/v2_6/internal/PlatformAudio.java',
    'sources/teavm/java/net/lax1dude/eaglercraft/v2_6/internal/PlatformNetworking.java',
    'sources/teavm/java/net/lax1dude/eaglercraft/v2_6/internal/teavm/EPKLoader.java',
    'sources/teavm/java/net/lax1dude/eaglercraft/v2_6/internal/teavm/WebGL2RenderingContext.java',
    'sources/teavm/java/net/lax1dude/eaglercraft/v2_6/internal/teavm/opts/IClientConfig.java',
    'sources/teavm/java/net/lax1dude/eaglercraft/v2_6/internal/PlatformRuntime.java',
]


def patch_shared_constants(text: str) -> str:
    """Update SharedConstants.java with 26.2 version numbers."""
    # The original patch has WRONG numbers (3955 for data version, 26 for pack formats).
    # These are 1.20-era numbers, not 26.2. The 26.2 wiki-correct values are:
    #   WORLD_VERSION = 4903
    #   PROTOCOL_VERSION = 776
    #   DATA_VERSION = 4903
    #   RESOURCE_PACK_FORMAT = 88
    #   DATA_PACK_FORMAT = 107
    text = re.sub(
        r'public static final int WORLD_VERSION = \d+;',
        'public static final int WORLD_VERSION = 4903;',
        text
    )
    text = re.sub(
        r'public static final int PROTOCOL_VERSION = \d+;',
        'public static final int PROTOCOL_VERSION = 776;',
        text
    )
    text = re.sub(
        r'public static final int DATA_VERSION = \d+;',
        'public static final int DATA_VERSION = 4903;',
        text
    )
    text = re.sub(
        r'public static final int RESOURCE_PACK_FORMAT = \d+;',
        'public static final int RESOURCE_PACK_FORMAT = 88;',
        text
    )
    text = re.sub(
        r'public static final int DATA_PACK_FORMAT = \d+;',
        'public static final int DATA_PACK_FORMAT = 107;',
        text
    )
    # Update the createBuiltIn call
    text = text.replace(
        'DetectedVersion.createBuiltIn("26.2", "26.2", true)',
        'DetectedVersion.createBuiltIn("26.2", "26.2", true)'
    )
    # Update getCurrentVersionName
    text = text.replace(
        'return "26.2";',
        'return "26.2";'
    )
    return text


def patch_platform_application(text: str) -> str:
    """Update PlatformApplication VERSION string."""
    return re.sub(
        r'private static final String VERSION = "[^"]+";',
        'private static final String VERSION = "26.2";',
        text
    )


def patch_string_literals(text: str) -> str:
    """Replace 26.2 string literals with 26.2 (cosmetic for comments/messages)."""
    # Only replace exact "26.2" tokens (word boundary to avoid matching 26.1.20 etc.)
    return re.sub(r'\b26\.1\.2\b', '26.2', text)


def main():
    print('Patching diddy62626 source for 26.2...')
    print()

    for rel_path in FILES_TO_PATCH:
        path = ROOT / rel_path
        if not path.exists():
            print(f'  SKIP (not found): {rel_path}')
            continue

        original = path.read_text(encoding='utf-8')
        patched = original

        if 'SharedConstants' in rel_path:
            patched = patch_shared_constants(patched)
            print(f'  patched version constants: {rel_path}')
        elif 'PlatformApplication' in rel_path:
            patched = patch_platform_application(patched)
            print(f'  patched VERSION string: {rel_path}')

        # Apply cosmetic 26.2 → 26.2 replacement to all files
        before = patched
        patched = patch_string_literals(patched)
        if patched != before:
            print(f'  replaced 26.2 string literals: {rel_path}')

        if patched != original:
            path.write_text(patched, encoding='utf-8')
        else:
            print(f'  (no changes): {rel_path}')

    print()
    print('Patch complete. New 26.2 version constants:')
    sc = (ROOT / 'sources/teavm/java/net/minecraft/SharedConstants.java').read_text()
    for line in sc.split('\n'):
        if 'VERSION' in line and '=' in line and 'final' in line:
            print(f'  {line.strip()}')

    print()
    print('NOTE: The new 26.2 blocks (cinnabar, sulfur, potent_sulfur, sulfur_spike),')
    print('items, and the SulfurCube mob are NOT patched here — they come from the')
    print('decompiled 26.2 JAR automatically when you run ./build.sh.')
    print()
    print('New 26.2 content that will be in the build:')
    print('  Blocks: SULFUR, POTENT_SULFUR, SULFUR_SLAB, SULFUR_STAIRS, SULFUR_WALL,')
    print('          POLISHED_SULFUR (+ slab/stairs/wall), SULFUR_BRICKS (+ slab/stairs/wall),')
    print('          CHISELED_SULFUR, CINNABAR (+ slab/stairs/wall), POLISHED_CINNABAR')
    print('          (+ slab/stairs/wall), CINNABAR_BRICKS (+ slab/stairs/wall),')
    print('          CHISELED_CINNABAR, SULFUR_SPIKE')
    print('  Mob: SulfurCube (extends AbstractCubeMob, like Slime/MagmaCube)')
    print('  Items: sulfur_spike, sulfur_cube_spawn_egg, cinnabar, sulfur, etc.')
    print('  Particles: sulfur_bubbles, sulfur_cube_goo')
    print('  Sounds: SoundType.SULFUR, SoundType.POTENT_SULFUR, SoundType.CINNABAR,')
    print('          SoundType.SULFUR_SPIKE')


if __name__ == '__main__':
    main()
