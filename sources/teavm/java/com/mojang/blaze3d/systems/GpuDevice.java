package com.mojang.blaze3d.systems;

import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.CompiledRenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.shaders.ShaderSource;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Supplier;

public interface GpuDevice extends GpuBackend {
    default List<String> getEnabledExtensions() { return new ArrayList<>(); }
    default int getMaxTextureSize() { return 16384; }
    default String getRenderer() { return "WebGL2"; }
    default boolean isDebuggingEnabled() { return false; }
    default CommandEncoder createCommandEncoder() { return new CommandEncoder() { @Override public void end() {} }; }
    default GpuTexture createTexture(Supplier<String> label, int usage, TextureFormat format, int w, int h, int d, int levels) { return new GpuTexture() {}; }
    default GpuTexture createTexture(String label, int usage, TextureFormat format, int w, int h, int d, int levels) { return new GpuTexture() {}; }

    // MC 26.2: createTexture with GpuFormat
    default GpuTexture createTexture(String label, int usage, GpuFormat format, int w, int h, int d, int levels) { return new GpuTexture() {}; }
    default GpuTexture createTexture(Supplier<String> label, int usage, GpuFormat format, int w, int h, int d, int levels) { return new GpuTexture() {}; }

    default GpuTextureView createTextureView(GpuTexture texture) { return new GpuTextureView(texture); }
    default List<String> getLastDebugMessages() { return new ArrayList<>(); }
    default void addDebugMessage(String message) {}
    default void resetDebugMessages() {}
    default GpuBuffer createBuffer(Supplier<String> label, int usage, long size) { return new GpuBuffer() {}; }
    default GpuBuffer createBuffer(Supplier<String> label, int usage, ByteBuffer data) { return new GpuBuffer() {}; }
    default String getBackendName() { return "WebGL2"; }
    default int getMaxSupportedAnisotropy() { return 0; }
    default int getUniformOffsetAlignment() { return 256; }
    default boolean isZZeroToOne() { return false; }
    default CompiledRenderPipeline precompilePipeline(RenderPipeline pipeline, ShaderSource source) { return null; }
    default CompiledRenderPipeline precompilePipeline(RenderPipeline pipeline) { return null; }

    // MC 26.2: GpuSurface + DeviceInfo
    default GpuSurface createSurface(long windowHandle) { return new GpuSurface(windowHandle); }
    default DeviceInfo getDeviceInfo() {
        return new DeviceInfo("WebGL2", "Browser", "", false, "WebGL2", 1.0f, new DeviceLimits(0, 256, 16384, 0, 0, 4), new DeviceFeatures(), java.util.Set.of(), new HintsAndWorkarounds(false, false), DeviceType.INTEGRATED);
    }

    // MC 26.2: GpuQueryPool
    default GpuQueryPool createTimestampQueryPool(int size) {
        return new GpuQueryPool() {
            @Override public int size() { return size; }
            @Override public java.util.OptionalLong getValue(int index) { return java.util.OptionalLong.empty(); }
            @Override public java.util.OptionalLong[] getValues(int index, int count) { return new java.util.OptionalLong[count]; }
            @Override public void close() {}
        };
    }

    default GpuSampler createSampler(AddressMode u, AddressMode v, FilterMode min, FilterMode mag, int maxAnisotropy, OptionalDouble maxLod) {
        return new GpuSampler();
    }
}
