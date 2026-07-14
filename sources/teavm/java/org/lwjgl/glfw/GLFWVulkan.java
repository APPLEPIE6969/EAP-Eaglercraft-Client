package org.lwjgl.glfw;

public class GLFWVulkan {
    public static boolean glfwVulkanSupported() { return false; }
    public static java.util.Set<String> getRequiredInstanceExtensions() { return java.util.Set.of(); }

    public static org.lwjgl.PointerBuffer glfwGetRequiredInstanceExtensions() { return null; }

    public static boolean glfwGetPhysicalDevicePresentationSupport(org.lwjgl.vulkan.VkInstance instance, org.lwjgl.vulkan.VkPhysicalDevice device, int queueFamilyIndex) { return false; }
}
