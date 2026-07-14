package org.lwjgl.vulkan;
@FunctionalInterface
public interface VkDebugUtilsMessengerCallbackEXTI {
    int invoke(int severity, int types, Object data);
}
