package org.lwjgl.vulkan;
import org.lwjgl.system.MemoryStack;
public class VkPhysicalDeviceProperties {
    public static final int SIZEOF = 0;
    public static VkPhysicalDeviceProperties calloc(MemoryStack stack) { return new VkPhysicalDeviceProperties(); }
    public String deviceName() { return ""; }
    public String deviceNameString() { return ""; }
    public int vendorID() { return 0; }
    public int deviceID() { return 0; }
    public int apiVersion() { return 0; }
    public int driverVersion() { return 0; }
    public int deviceType() { return 0; }
}
