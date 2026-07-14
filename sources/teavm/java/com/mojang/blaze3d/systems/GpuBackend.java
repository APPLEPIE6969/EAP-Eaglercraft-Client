package com.mojang.blaze3d.systems;

import com.mojang.blaze3d.shaders.ShaderSource;
import com.mojang.blaze3d.shaders.GpuDebugOptions;

/**
 * EaglerCraft stub for GpuBackend.
 * Browser-side: backed by WebGL2.
 *
 * In MC 26.2, GpuBackend is both an interface AND has a static
 * factory method createDevice(long, ShaderSource, GpuDebugOptions).
 * We make GpuBackend a class with the static method, and GpuDevice
 * an interface that extends it.
 */
public interface GpuBackend {
    default boolean isDisplay() { return false; }
    default boolean isVulkan() { return false; }
    default boolean isMetal() { return false; }
    default boolean isOpenGL() { return true; }
    default boolean isWebGPU() { return false; }
    String getName();
    String getVendor();
    String getVersion();

    /**
     * MC 26.2: Factory method that creates a GpuDevice from a window handle.
     * Browser: returns a stub GpuDevice backed by WebGL2.
     *
     * 26.2 signature: createDevice(long, ShaderSource, GpuDebugOptions, Runnable)
     * The Runnable is a critical shader loader callback.
     */
    static GpuDevice createDevice(long windowHandle, ShaderSource shaderSource, GpuDebugOptions debugOptions, Runnable criticalShaderLoader) {
        if (criticalShaderLoader != null) {
            criticalShaderLoader.run();
        }
        return new com.mojang.blaze3d.systems.GpuDevice() {
            @Override
            public String getName() { return "WebGL2"; }
            @Override
            public String getVendor() { return "Browser"; }
            @Override
            public String getVersion() { return "2.0"; }
            @Override
            public java.util.List<String> getEnabledExtensions() {
                return new java.util.ArrayList<>();
            }
        };
    }

    /** @deprecated Use the 4-arg version. Kept for backward compatibility. */
    @Deprecated
    static GpuDevice createDevice(long windowHandle, ShaderSource shaderSource, GpuDebugOptions debugOptions) {
        return createDevice(windowHandle, shaderSource, debugOptions, null);
    }
}
