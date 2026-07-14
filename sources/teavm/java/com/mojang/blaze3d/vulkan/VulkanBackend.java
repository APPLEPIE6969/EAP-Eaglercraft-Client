package com.mojang.blaze3d.vulkan;

import com.mojang.blaze3d.shaders.GpuDebugOptions;
import com.mojang.blaze3d.shaders.ShaderSource;
import com.mojang.blaze3d.systems.BackendCreationException;
import com.mojang.blaze3d.systems.GpuBackend;
import com.mojang.blaze3d.systems.GpuDevice;

/**
 * EaglerCraft stub for VulkanBackend.
 * Browser: Vulkan is not supported, always returns failure.
 */
public class VulkanBackend implements GpuBackend {
    @Override
    public String getName() { return "Vulkan"; }
    @Override
    public String getVendor() { return "N/A"; }
    @Override
    public String getVersion() { return "0"; }

    public void setWindowHints() {}

    public GpuDevice createDevice(long windowHandle, ShaderSource shaderSource, GpuDebugOptions debugOptions, Runnable criticalShaderLoader) {
        throw new BackendCreationException("Vulkan not supported in browser", BackendCreationException.Reason.VULKAN_LOADER_MISSING);
    }

    public static BackendCreationException checkBackendAvailable() {
        return new BackendCreationException("Vulkan not supported in browser", BackendCreationException.Reason.VULKAN_LOADER_MISSING);
    }
}
