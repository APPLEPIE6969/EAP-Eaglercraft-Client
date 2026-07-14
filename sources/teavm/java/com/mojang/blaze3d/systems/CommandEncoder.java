package com.mojang.blaze3d.systems;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.textures.GpuTexture;

public interface CommandEncoder {
    default void writeToTexture(GpuTexture destination, NativeImage source) {}
    default void writeToTexture(GpuTexture destination, NativeImage source, int destMipLevel, int destX, int destY, int srcMipLevel) {}
    default void writeToBuffer(com.mojang.blaze3d.buffers.GpuBuffer buffer, java.nio.ByteBuffer data) {}
    void end();
    default void copyTextureToBuffer(com.mojang.blaze3d.textures.GpuTexture src, com.mojang.blaze3d.buffers.GpuBuffer dst, long offset, Runnable callback, int mipLevel) {}
    default RenderPass createRenderPass(java.util.function.Supplier<String> label, com.mojang.blaze3d.textures.GpuTextureView color, java.util.Optional<com.mojang.blaze3d.textures.GpuTextureView> depth) { return null; }
    default RenderPass createRenderPass(java.util.function.Supplier<String> label, com.mojang.blaze3d.textures.GpuTextureView color, java.util.Optional<com.mojang.blaze3d.textures.GpuTextureView> depth, com.mojang.blaze3d.textures.GpuTextureView color2, java.util.OptionalDouble clearDepth) { return null; }
    default void copyToBuffer(com.mojang.blaze3d.buffers.GpuBufferSlice src, com.mojang.blaze3d.buffers.GpuBufferSlice dst) {}
    default void writeTimestamp(GpuQueryPool pool, int index) {}
    default void clearColorAndDepthTextures(com.mojang.blaze3d.textures.GpuTexture color, org.joml.Vector4fc colorValue, com.mojang.blaze3d.textures.GpuTexture depth, double depthValue) {}
    default void clearColorAndDepthTextures(com.mojang.blaze3d.textures.GpuTexture color, org.joml.Vector4fc colorValue, com.mojang.blaze3d.textures.GpuTexture depth, double depthValue, int x, int y, int w, int h) {}
    default void clearDepthTexture(com.mojang.blaze3d.textures.GpuTexture depth, double depthValue) {}
    default com.mojang.blaze3d.buffers.GpuFence createFence() { return null; }
    default void clearColorTexture(com.mojang.blaze3d.textures.GpuTexture texture, org.joml.Vector4fc color) {}
    default void submit() {}
    default void writeToBuffer(com.mojang.blaze3d.buffers.GpuBufferSlice buffer, java.nio.ByteBuffer data) {}
    default void clearDepthTexture(com.mojang.blaze3d.textures.GpuTexture texture) {}
    default void copyBufferToBuffer(com.mojang.blaze3d.buffers.GpuBuffer src, com.mojang.blaze3d.buffers.GpuBuffer dst, long srcOffset, long dstOffset, long size) {}
    default void copyTextureToTexture(com.mojang.blaze3d.textures.GpuTexture src, int srcMip, int srcX, int srcY, com.mojang.blaze3d.textures.GpuTexture dst, int dstMip, int dstX, int dstY, int width, int height) {}
}
