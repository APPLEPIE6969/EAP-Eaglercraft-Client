package org.lwjgl.vulkan;
import org.lwjgl.system.MemoryStack;
public class VkPhysicalDeviceProperties2 {
    public static final int SIZEOF = 0;
    public static VkPhysicalDeviceProperties2 calloc() { return new VkPhysicalDeviceProperties2(); }
    public static VkPhysicalDeviceProperties2 calloc(MemoryStack stack) { return new VkPhysicalDeviceProperties2(); }
    public VkPhysicalDeviceProperties2 sType$Default() { return this; }
    public int nsType(long address) { return 0; }
    public void nsType(long address, int value) {}
    public long npNext(long address) { return 0; }
    public void npNext(long address, long value) {}
    public VkPhysicalDeviceProperties2 pNext(VkPhysicalDeviceMultiDrawPropertiesEXT ext) { return this; }
    public VkPhysicalDeviceProperties2 pNext(VkPhysicalDeviceDriverProperties ext) { return this; }
    public VkPhysicalDeviceProperties2 pNext(VkPhysicalDeviceSynchronization2Features ext) { return this; }
    public VkPhysicalDeviceProperties2 pNext(VkPhysicalDeviceVulkan11Properties ext) { return this; }
    public VkPhysicalDeviceProperties2 pNext(VkPhysicalDeviceDynamicRenderingFeatures ext) { return this; }
    public VkPhysicalDeviceProperties properties() { return new VkPhysicalDeviceProperties(); }

    public void free() {}
}
