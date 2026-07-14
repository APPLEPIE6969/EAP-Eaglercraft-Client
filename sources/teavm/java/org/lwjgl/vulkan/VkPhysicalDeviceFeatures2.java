package org.lwjgl.vulkan;
import org.lwjgl.system.MemoryStack;
public class VkPhysicalDeviceFeatures2 {
    public static VkPhysicalDeviceFeatures2 calloc() { return new VkPhysicalDeviceFeatures2(); }
    public static final int SIZEOF = 0;
    public static final long FEATURES = 0;
    public static VkPhysicalDeviceFeatures2 calloc(MemoryStack stack) { return new VkPhysicalDeviceFeatures2(); }
    public void free() {}
    public long address() { return 0; }
    public VkPhysicalDeviceFeatures2 sType$Default() { return this; }
}
