package org.lwjgl.vulkan;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
public class VkInstanceCreateInfo {
    public static final int SIZEOF = 0;
    public static VkInstanceCreateInfo calloc(MemoryStack stack) { return new VkInstanceCreateInfo(); }
    public VkInstanceCreateInfo sType$Default() { return this; }
    public VkInstanceCreateInfo flags(int f) { return this; }
    public VkInstanceCreateInfo pApplicationInfo(VkApplicationInfo info) { return this; }
    public VkInstanceCreateInfo ppEnabledExtensionNames(PointerBuffer buf) { return this; }
    public VkInstanceCreateInfo ppEnabledLayerNames(PointerBuffer buf) { return this; }
    public VkInstanceCreateInfo pNext(VkDebugUtilsMessengerCreateInfoEXT ext) { return this; }
    public long address() { return 0; }
}
