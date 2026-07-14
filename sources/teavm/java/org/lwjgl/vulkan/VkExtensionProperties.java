package org.lwjgl.vulkan;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Struct;
public class VkExtensionProperties extends Struct {
    public static final int SIZEOF = 0;
    public VkExtensionProperties() {}
    public VkExtensionProperties(long address, int capacity) { super(address, capacity); }
    public String extensionName() { return ""; }
    public String extensionNameString() { return ""; }
    public int specVersion() { return 0; }
    public static Buffer calloc(int count) { return new Buffer(0, count); }
    public static Buffer calloc(int count, MemoryStack stack) { return new Buffer(0, count); }
    public static class Buffer extends Struct.Buffer {
        public Buffer(long address, int capacity) { super(address, capacity); }
        @Override public VkExtensionProperties get(int index) { return new VkExtensionProperties(); }
        public void free() {}
        
    }
}
