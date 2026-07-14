#!/usr/bin/env python3
"""
Patch teavm-relocated-libs-asm-0.15.0.jar to accept class file version 70
(Java 26) in org.teavm.asm.ClassReader.

TeaVM 0.15.0's repackaged ASM only supports up to class file version 69
(Java 25 = Opcodes.V25). When running on JDK 26, TeaVM's
MetaprogrammingClassLoader tries to parse JDK 26's own classes (which
are version 70) and throws:
    IllegalArgumentException: Unsupported class file major version 70

The version check is in ClassReader.<init>(byte[], int, int):
    bipush 69       ; push Opcodes.V25
    if_icmple +22   ; if version <= 69, skip throw
    new IllegalArgumentException
    ...

This script patches the `bipush 69` (0x10 0x45) to `bipush 127` (0x10 0x7F),
changing the upper bound from 69 to 127. This accepts all class file
versions up to Java 83 (version 127), which covers all foreseeable JDK
releases.

The lower bound check (V1_1 = 45) is left intact since version 70 is
well above it.

NOTE: This script is IDEMPOTENT. If the JAR has already been patched
(bipush 127 already present at the version check location), it reports
"already patched" and exits successfully. This is important because the
Gradle cache persists between CI runs.
"""
import zipfile
import shutil
import os
import sys

JAR_PATH = sys.argv[1] if len(sys.argv) > 1 else '/tmp/teavm-asm.jar'
OUTPUT_PATH = sys.argv[2] if len(sys.argv) > 2 else '/tmp/teavm-asm-patched.jar'

# Branch opcodes that compare against the version number
BRANCH_OPS = {
    0x99: 'ifeq', 0x9A: 'ifne', 0x9B: 'iflt', 0x9C: 'ifge',
    0x9D: 'ifgt', 0x9E: 'ifle',
    0xA1: 'if_icmplt', 0xA2: 'if_icmpge', 0xA3: 'if_icmpgt',
    0xA4: 'if_icmple',
}

# Read the JAR
shutil.copy(JAR_PATH, OUTPUT_PATH)

# Read ClassReader.class from the JAR
with zipfile.ZipFile(JAR_PATH, 'r') as zin:
    cr_data = bytearray(zin.read('org/teavm/asm/ClassReader.class'))

print(f"Original ClassReader.class size: {len(cr_data)} bytes")

# Find ALL relevant patterns:
# - `bipush 69 + branch` (0x10 0x45 + branch_op) = UNPATCHED version check
# - `bipush 127 + branch` (0x10 0x7F + branch_op) = ALREADY PATCHED version check
unpatched = []
already_patched = []
for i in range(len(cr_data) - 2):
    if cr_data[i] == 0x10:
        next_byte = cr_data[i+1]
        third_byte = cr_data[i+2]
        if third_byte in BRANCH_OPS:
            if next_byte == 0x45:  # 69
                unpatched.append((i, BRANCH_OPS[third_byte]))
            elif next_byte == 0x7F:  # 127
                already_patched.append((i, BRANCH_OPS[third_byte]))

print(f"\nbipush 69 + branch (unpatched version checks): {len(unpatched)}")
for offset, branch in unpatched:
    print(f"  offset {offset}: bipush 69 + {branch}")
print(f"bipush 127 + branch (already patched): {len(already_patched)}")
for offset, branch in already_patched:
    print(f"  offset {offset}: bipush 127 + {branch}")

if already_patched and not unpatched:
    print("\nJAR is ALREADY PATCHED. No changes needed.")
    # Still write the output (it's a copy of the input)
    with zipfile.ZipFile(OUTPUT_PATH, 'r') as zin:
        with zipfile.ZipFile(OUTPUT_PATH + '.tmp', 'w', zipfile.ZIP_DEFLATED) as zout:
            for item in zin.infolist():
                zout.writestr(item, zin.read(item.filename))
    os.replace(OUTPUT_PATH + '.tmp', OUTPUT_PATH)
    print(f"Output (unchanged): {OUTPUT_PATH}")
    sys.exit(0)

if not unpatched:
    print("\nERROR: No version check pattern found (neither bipush 69 nor bipush 127).")
    print("The ASM ClassReader.class structure has changed. Manual inspection needed:")
    print("  1. Extract org/teavm/asm/ClassReader.class from the JAR")
    print("  2. Run: javap -c -p ClassReader.class | grep -B2 -A5 'bipush'")
    print("  3. Find the version check (bipush followed by a branch)")
    print("  4. Update this script to match the new pattern")
    sys.exit(1)

# Patch all unpatched version checks
patched_count = 0
for offset, branch in unpatched:
    print(f"\n  Patching version check at offset {offset}: bipush 69 + {branch}")
    cr_data[offset + 1] = 0x7F  # 69 -> 127
    patched_count += 1
    print(f"  Patched: bipush 69 -> bipush 127 (0x10 0x7F)")

print(f"\nPatched {patched_count} version check(s)")

# Write the patched JAR
with zipfile.ZipFile(OUTPUT_PATH, 'r') as zin:
    with zipfile.ZipFile(OUTPUT_PATH + '.tmp', 'w', zipfile.ZIP_DEFLATED) as zout:
        for item in zin.infolist():
            if item.filename == 'org/teavm/asm/ClassReader.class':
                zout.writestr(item, bytes(cr_data))
                print(f"Wrote patched ClassReader.class ({len(cr_data)} bytes)")
            else:
                zout.writestr(item, zin.read(item.filename))

os.replace(OUTPUT_PATH + '.tmp', OUTPUT_PATH)
print(f"\nPatched JAR saved to {OUTPUT_PATH}")
