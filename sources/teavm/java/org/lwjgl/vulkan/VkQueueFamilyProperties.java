package org.lwjgl.vulkan;
import org.lwjgl.system.Struct;
public class VkQueueFamilyProperties extends Struct {
    public static final int SIZEOF = 0;
    public VkQueueFamilyProperties() {}
    public VkQueueFamilyProperties(long address, int capacity) { super(address, capacity); }
    public int queueFlags() { return 0; }
    public int queueCount() { return 0; }
    public boolean queueGraphics() { return false; }
    public boolean queueCompute() { return false; }
    public boolean queueTransfer() { return false; }
    public static Buffer calloc(int count, org.lwjgl.system.MemoryStack stack) { return new Buffer(0, count); }
    public static class Buffer extends Struct.Buffer {
        public Buffer(long address, int capacity) { super(address, capacity); }
        @Override public VkQueueFamilyProperties get(int index) { return new VkQueueFamilyProperties(); }
    }
}
