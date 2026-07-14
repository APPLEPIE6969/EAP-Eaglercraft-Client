package org.lwjgl.vulkan;
import org.lwjgl.system.Struct;
public class VkPhysicalDeviceVulkan11Properties extends Struct {
    public static final int SIZEOF = 0;
    public VkPhysicalDeviceVulkan11Properties() {}
    public VkPhysicalDeviceVulkan11Properties(long address, int capacity) { super(address, capacity); }
    public static VkPhysicalDeviceVulkan11Properties calloc() { return new VkPhysicalDeviceVulkan11Properties(); }
    public void free() {}
    public VkPhysicalDeviceVulkan11Properties sType$Default() { return this; } {}
}
