package org.lwjgl.vulkan;
import org.lwjgl.system.MemoryStack;
public class VkDebugUtilsMessengerCreateInfoEXT {
    public static final int SIZEOF = 0;
    public static VkDebugUtilsMessengerCreateInfoEXT calloc(MemoryStack stack) { return new VkDebugUtilsMessengerCreateInfoEXT(); }
    public VkDebugUtilsMessengerCreateInfoEXT sType$Default() { return this; }
    public VkDebugUtilsMessengerCreateInfoEXT pfnUserCallback(VkDebugUtilsMessengerCallbackEXTI callback) { return this; }
    public VkDebugUtilsMessengerCreateInfoEXT messageSeverity(int severity) { return this; }
    public VkDebugUtilsMessengerCreateInfoEXT messageType(int type) { return this; }
    public VkDebugUtilsMessengerCreateInfoEXT pUserData(long data) { return this; }
}
