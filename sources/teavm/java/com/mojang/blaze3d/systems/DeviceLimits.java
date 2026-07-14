package com.mojang.blaze3d.systems;

import com.mojang.blaze3d.GpuFormat;

public record DeviceLimits(
    int maxAnisotropy,
    int minUniformOffsetAlignment,
    int maxTextureSize,
    long maxMemoryAllocationSize,
    int maxMultiDrawDirectInterleavedDrawCount,
    int maxColorAttachments
) {
    public int maxTextureSizeForFormat(GpuFormat format) {
        return Integer.highestOneBit(Math.min(maxTextureSize, (int) Math.sqrt((double) maxMemoryAllocationSize / (double) format.blockSize())));
    }
}
