package org.lwjgl.vulkan;
import org.lwjgl.PointerBuffer;
import java.nio.IntBuffer;
public class VK12 {
    public static final int VK_SUCCESS = 0;
    public static final int VK_API_VERSION_1_1 = 4198400;
    public static final int VK_API_VERSION_1_2 = 4202496;
    public static int vkCreateInstance(VkInstanceCreateInfo info, VkAllocationCallbacks alloc, PointerBuffer instance) { return VK_SUCCESS; }
    public static int vkEnumeratePhysicalDevices(VkInstance instance, IntBuffer count, PointerBuffer devices) { return VK_SUCCESS; }
    public static void vkDestroyInstance(VkInstance instance, VkAllocationCallbacks alloc) {}
    public static int vkEnumeratePhysicalDeviceGroups(VkInstance instance, IntBuffer count, java.nio.ByteBuffer groups) { return VK_SUCCESS; }
    public static void vkGetPhysicalDeviceFeatures2(VkPhysicalDevice device, VkPhysicalDeviceFeatures2 features) {}
    public static void vkGetPhysicalDeviceProperties2(VkPhysicalDevice device, VkPhysicalDeviceProperties2 props) {}
    public static int vkEnumerateDeviceExtensionProperties(VkPhysicalDevice device, CharSequence layerName, IntBuffer count, VkExtensionProperties.Buffer props) { return VK_SUCCESS; }
    public static void vkGetPhysicalDeviceQueueFamilyProperties(VkPhysicalDevice device, IntBuffer count, VkQueueFamilyProperties.Buffer props) {}

    public static int vkEnumerateInstanceExtensionProperties(CharSequence layerName, java.nio.IntBuffer count, VkExtensionProperties.Buffer props) { return VK_SUCCESS; }
    public static int vkEnumerateInstanceLayerProperties(java.nio.IntBuffer count, VkLayerProperties.Buffer props) { return VK_SUCCESS; }
    public static void vkGetPhysicalDeviceProperties(VkPhysicalDevice device, VkPhysicalDeviceProperties props) {}
}
