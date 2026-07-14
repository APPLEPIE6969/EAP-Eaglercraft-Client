package org.lwjgl.vulkan;
public class VkPhysicalDeviceDriverProperties {
    public static VkPhysicalDeviceDriverProperties calloc() { return new VkPhysicalDeviceDriverProperties(); }
    public static final int SIZEOF = 0;
    public int driverID() { return 0; }
    public String driverName() { return ""; }
    public String driverInfo() { return ""; }

    public void free() {}
    public VkPhysicalDeviceDriverProperties sType$Default() { return this; }
}
