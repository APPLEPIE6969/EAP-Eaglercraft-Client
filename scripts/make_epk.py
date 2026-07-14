#!/usr/bin/env python3
"""
Create an EPK (Eaglercraft Package) file from a directory of assets.

EPK format:
  - Magic: "EAGPKG!" (7 bytes)
  - Version: 1 byte (0x01)
  - File count: 4 bytes (big-endian int32)
  - For each file:
    - Name length: 2 bytes (big-endian uint16)
    - Name: UTF-8 bytes
    - Data length: 4 bytes (big-endian int32)
    - Data: raw bytes
  - Footer: "EAGPK_END" (9 bytes)

Usage:
    python3 make_epk.py <input_dir> <output.epk>
"""
import os
import sys
import struct
from pathlib import Path

def create_epk(input_dir, output_path):
    input_path = Path(input_dir)
    if not input_path.is_dir():
        print(f"ERROR: {input_dir} is not a directory")
        return 1

    # Collect all files
    files = []
    for root, dirs, filenames in os.walk(input_path):
        for fn in filenames:
            full = os.path.join(root, fn)
            rel = os.path.relpath(full, input_path).replace(os.sep, '/')
            files.append((rel, full))

    print(f"Packing {len(files)} files into EPK...")

    with open(output_path, 'wb') as f:
        # Write magic header
        f.write(b'EAGPKG!')
        # Version
        f.write(struct.pack('>B', 1))
        # File count
        f.write(struct.pack('>i', len(files)))

        # Write each file
        for rel, full in files:
            name_bytes = rel.encode('utf-8')
            with open(full, 'rb') as inf:
                data = inf.read()

            # Name length (uint16 big-endian)
            f.write(struct.pack('>H', len(name_bytes)))
            # Name
            f.write(name_bytes)
            # Data length (int32 big-endian)
            f.write(struct.pack('>i', len(data)))
            # Data
            f.write(data)

        # Footer
        f.write(b'EAGPK_END')

    size = os.path.getsize(output_path)
    print(f"EPK created: {output_path} ({size:,} bytes)")
    return 0

if __name__ == '__main__':
    if len(sys.argv) != 3:
        print(f"Usage: {sys.argv[0]} <input_dir> <output.epk>")
        sys.exit(1)
    sys.exit(create_epk(sys.argv[1], sys.argv[2]))
