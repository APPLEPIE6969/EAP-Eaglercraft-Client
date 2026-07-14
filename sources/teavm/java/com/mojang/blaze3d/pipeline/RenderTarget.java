package com.mojang.blaze3d.pipeline;

import com.mojang.blaze3d.GpuFormat;
import org.jspecify.annotations.Nullable;

public class RenderTarget {
    protected String label;
    protected boolean useDepth;
    protected GpuFormat format;
    protected int width;
    protected int height;
    protected int frameBufferId;

    // MC 26.2 constructor
    public RenderTarget(@Nullable String label, boolean useDepth, GpuFormat format) {
        this.label = label;
        this.useDepth = useDepth;
        this.format = format;
    }

    // Legacy constructor (used by MainTarget)
    public RenderTarget(int width, int height, boolean useDepth, boolean depthOnly) {
        this.width = width;
        this.height = height;
        this.useDepth = useDepth;
        this.format = GpuFormat.RGBA8_UNORM;
    }

    public void resize(int width, int height) { this.width = width; this.height = height; }
    public void destroyBuffers() {}
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean useDepth() { return useDepth; }
    public GpuFormat format() { return format; }
    public String label() { return label; }

    // Methods used by MainTarget
    public void bindWrite(boolean clear) {}
    public void bindRead() {}
    public boolean isBound() { return false; }
    public int getFrameBufferId() { return frameBufferId; }
    public void unbindRead() {}
    public void blitAndBlendToTexture(com.mojang.blaze3d.textures.GpuTextureView src, com.mojang.blaze3d.textures.GpuTextureView dst) {}
    public void copyDepthFrom(RenderTarget other) {}
    public com.mojang.blaze3d.textures.GpuTextureView getColorTextureView() { return null; }
    public com.mojang.blaze3d.textures.GpuTextureView getDepthTextureView() { return null; }
    public com.mojang.blaze3d.textures.GpuTexture getColorTexture() { return null; }
    public com.mojang.blaze3d.textures.GpuTexture getDepthTexture() { return null; }
}
