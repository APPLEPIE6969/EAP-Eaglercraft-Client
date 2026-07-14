package org.lwjgl.vulkan;
import org.lwjgl.system.MemoryStack;
public class VkApplicationInfo {
    public static final int SIZEOF = 0;
    public static VkApplicationInfo calloc(MemoryStack stack) { return new VkApplicationInfo(); }
    public VkApplicationInfo sType$Default() { return this; }
    public VkApplicationInfo apiVersion(int v) { return this; }
    public VkApplicationInfo applicationVersion(int v) { return this; }
    public VkApplicationInfo pApplicationName(java.nio.ByteBuffer name) { return this; }
    public VkApplicationInfo pEngineName(java.nio.ByteBuffer name) { return this; }
    public VkApplicationInfo engineVersion(int v) { return this; }
    public long address() { return 0; }
}
