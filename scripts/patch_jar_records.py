#!/usr/bin/env python3
"""
Patch the vanilla Minecraft JAR to replace Java record classes with
regular class versions that TeaVM can process.

TeaVM 0.15.0 has limited support for Java records. The auto-generated
accessor methods (like type() for a field named 'type') may not be
found during TeaVM compilation. This script:

1. Compiles our class-based stubs (DeviceInfo, etc.)
2. Replaces the record .class files in the JAR with our class versions

Usage:
    python3 patch_jar_records.py <jar_path> <stubs_dir> <output_jar>
"""
import sys
import zipfile
import shutil
import os
from pathlib import Path

JAR_PATH = sys.argv[1] if len(sys.argv) > 1 else 'sources/libs/minecraft-26.2.jar'
OUTPUT_PATH = sys.argv[2] if len(sys.argv) > 2 else JAR_PATH

# Classes to replace (record -> class stubs we provide)
REPLACE_CLASSES = [
    'com/mojang/blaze3d/systems/DeviceInfo.class',
    'com/mojang/blaze3d/systems/DeviceLimits.class',
    'com/mojang/blaze3d/systems/HintsAndWorkarounds.class',
    'com/mojang/blaze3d/systems/DeviceFeatures.class',
    'com/mojang/blaze3d/pipeline/BlendFunction.class',
    'com/mojang/blaze3d/pipeline/BindGroupLayout.class',
    'com/mojang/blaze3d/pipeline/BindGroupLayout$UniformDescription.class',
    'com/mojang/blaze3d/pipeline/BindGroupLayout$Builder.class',
    'com/mojang/blaze3d/systems/GpuSurface.class',
    'com/mojang/blaze3d/systems/GpuSurface$Configuration.class',
    'com/mojang/blaze3d/systems/GpuSurface$PresentMode.class',
    'com/mojang/blaze3d/shaders/GpuDebugOptions.class',
    'com/mojang/blaze3d/PrimitiveTopology.class',
    'com/mojang/blaze3d/GpuFormat.class',
    'com/mojang/blaze3d/IndexType.class',
]

print(f"Patching JAR: {JAR_PATH}")
print(f"Replacing {len(REPLACE_CLASSES)} classes")

# Read the original JAR
shutil.copy(JAR_PATH, OUTPUT_PATH + '.tmp')

# We can't replace .class files with source files directly.
# This script is a placeholder - the actual replacement needs to happen
# after our stubs are compiled to .class files.
#
# For now, just report which classes need replacement.
with zipfile.ZipFile(JAR_PATH, 'r') as zf:
    for cls in REPLACE_CLASSES:
        try:
            data = zf.read(cls)
            print(f"  Found: {cls} ({len(data)} bytes)")
        except KeyError:
            print(f"  Not in JAR: {cls}")

print("\nNOTE: This script is a placeholder. The actual JAR patching")
print("needs to happen after the stubs are compiled to .class files.")
print("The CI workflow should compile the stubs, then replace the")
print("record .class files in the JAR with the compiled class versions.")
