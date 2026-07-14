package com.mojang.blaze3d;

public class TracyFrameCapture {
    private int lastCaptureDelay = 0;
    private boolean capturedThisFrame = false;

    public void endFrame() {
        this.lastCaptureDelay++;
        this.capturedThisFrame = false;
    }

    public void beginFrame() {}
    public void upload() {}
    public void capture(com.mojang.blaze3d.pipeline.RenderTarget target) {}
    public boolean isCapturing() { return false; }
}
