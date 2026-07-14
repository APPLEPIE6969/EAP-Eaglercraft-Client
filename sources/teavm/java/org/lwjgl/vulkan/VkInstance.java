package org.lwjgl.vulkan;
public class VkInstance {
    private long handle;
    public VkInstance() {}
    public VkInstance(long handle, VkInstanceCreateInfo createInfo) { this.handle = handle; }
    public long address() { return handle; }
}
