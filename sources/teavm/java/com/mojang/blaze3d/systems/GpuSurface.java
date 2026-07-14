package com.mojang.blaze3d.systems;

import java.util.Collection;
import java.util.Optional;

public class GpuSurface implements AutoCloseable {
    private Configuration config;

    public GpuSurface(long windowHandle) {}

    public void configure(Configuration config) throws SurfaceException { this.config = config; }
    public Optional<Configuration> currentConfiguration() { return Optional.ofNullable(config); }
    public Collection<PresentMode> supportedPresentModes() { return java.util.List.of(PresentMode.FIFO); }
    public boolean isSuboptimal() { return false; }
    public boolean isAcquired() { return false; }
    public void acquireNextTexture() throws SurfaceException {}
    public void blitFromTexture(CommandEncoder encoder, com.mojang.blaze3d.textures.GpuTextureView textureView) {}
    public void present() {}
    @Override public void close() {}

    public enum PresentMode {
        IMMEDIATE, MAILBOX, FIFO, FIFO_RELAXED;

        private static final PresentMode[] VSYNC = {FIFO_RELAXED, FIFO};
        private static final PresentMode[] NO_VSYNC = {IMMEDIATE, MAILBOX, FIFO};

        public static PresentMode getSupportedVsyncMode(Collection<PresentMode> supported, boolean vsync) {
            PresentMode[] preferred = vsync ? VSYNC : NO_VSYNC;
            for (PresentMode p : preferred) {
                if (supported.contains(p)) return p;
            }
            return FIFO;
        }
    }

    public record Configuration(int width, int height, PresentMode presentMode) {}
}
