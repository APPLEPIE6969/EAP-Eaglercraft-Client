package com.mojang.blaze3d;

/**
 * EaglerCraft stub for MC 26.2 GpuFormat.
 * Enum of GPU texture/vertex formats. Browser: WebGL2 uses these.
 */
public enum GpuFormat {
    R8_UNORM(1), R8_SNORM(1),
    RG8_UNORM(2), RG8_SNORM(2),
    RGB8_UNORM(3), RGB8_SNORM(3),
    RGBA8_UNORM(4), RGBA8_SNORM(4),
    R16_UNORM(2), R16_SNORM(2),
    RG16_UNORM(4), RG16_SNORM(4),
    RGB16_UNORM(6), RGB16_SNORM(6),
    RGBA16_UNORM(8), RGBA16_SNORM(8),
    R8_UINT(1), R8_SINT(1), RG8_UINT(2), RG8_SINT(2), RGBA8_UINT(4), RGBA8_SINT(4),
    R16_UINT(2), R16_SINT(2), RG16_UINT(4), RG16_SINT(4), RGBA16_UINT(8), RGBA16_SINT(8),
    R32_UINT(4), RG32_UINT(8), RGB32_UINT(12), RGBA32_UINT(16),
    R16_FLOAT(2), RG16_FLOAT(4), RGBA16_FLOAT(8),
    R32_FLOAT(4), RG32_FLOAT(8), RGB32_FLOAT(12), RGBA32_FLOAT(16),
    DEPTH16(2), DEPTH24(3), DEPTH32(4),
    DEPTH24_STENCIL8(4),
    D32_FLOAT(4);

    private final int bytes;

    GpuFormat(int bytes) { this.bytes = bytes; }

    public int bytes() { return bytes; }
    public int blockSize() { return bytes; }

    public enum ComponentType { UNORM_8, SNORM_8, UNORM_16, SNORM_16, FLOAT_16, FLOAT_32, UINT_8, UINT_16, UINT_32, DEPTH }
}
