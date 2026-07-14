package org.lwjgl.vulkan;
import java.nio.LongBuffer;
public class EXTDebugUtils {
    public static final String VK_EXT_DEBUG_UTILS_EXTENSION_NAME = "VK_EXT_debug_utils";
    public static int vkCreateDebugUtilsMessengerEXT(VkInstance instance, VkDebugUtilsMessengerCreateInfoEXT info, VkAllocationCallbacks alloc, LongBuffer messenger) { return 0; }
    public static void vkDestroyDebugUtilsMessengerEXT(VkInstance instance, long messenger, VkAllocationCallbacks alloc) {}
}
