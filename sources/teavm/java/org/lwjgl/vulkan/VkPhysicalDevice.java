package org.lwjgl.vulkan;
public class VkPhysicalDevice {
    public static final int SIZEOF = 0;
    private long handle;
    private VkInstance instance;
    public VkPhysicalDevice() {}
    public VkPhysicalDevice(long handle, VkInstance instance) { this.handle = handle; this.instance = instance; }
    public long address() { return handle; }
    public VkInstance getInstance() { return instance; }
}
