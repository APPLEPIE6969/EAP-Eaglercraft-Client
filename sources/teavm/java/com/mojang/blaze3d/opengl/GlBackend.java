package com.mojang.blaze3d.opengl;

import com.mojang.blaze3d.shaders.GpuDebugOptions;
import com.mojang.blaze3d.shaders.ShaderSource;
import com.mojang.blaze3d.systems.BackendCreationException;
import com.mojang.blaze3d.systems.GpuBackend;
import com.mojang.blaze3d.systems.GpuDevice;
import java.util.List;

/**
 * EaglerCraft stub for GlBackend.
 * Browser: delegates to WebGL2 instead of real OpenGL.
 */
public class GlBackend implements GpuBackend {
    @Override
    public String getName() { return "WebGL2"; }
    @Override
    public String getVendor() { return "Browser"; }
    @Override
    public String getVersion() { return "2.0"; }

    public void setWindowHints() {}

    public GpuDevice createDevice(long windowHandle, ShaderSource shaderSource, GpuDebugOptions debugOptions, Runnable criticalShaderLoader) throws BackendCreationException {
        if (criticalShaderLoader != null) criticalShaderLoader.run();
        return new GpuDevice() {
            @Override
            public String getName() { return "WebGL2"; }
            @Override
            public String getVendor() { return "Browser"; }
            @Override
            public String getVersion() { return "2.0"; }
            @Override
            public java.util.List<String> getEnabledExtensions() { return new java.util.ArrayList<>(); }
        };
    }

    public static BackendCreationException checkBackendAvailable() {
        return null; // WebGL2 is always available in browser
    }
}
