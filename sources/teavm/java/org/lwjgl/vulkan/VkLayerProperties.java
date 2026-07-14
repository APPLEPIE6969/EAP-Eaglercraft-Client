package org.lwjgl.vulkan;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Struct;
public class VkLayerProperties extends Struct {
    public static final int SIZEOF = 0;
    public VkLayerProperties() {}
    public VkLayerProperties(long address, int capacity) { super(address, capacity); }
    public String layerName() { return ""; }
    public String layerNameString() { return ""; }
    public String description() { return ""; }
    public static Buffer calloc(int count, MemoryStack stack) { return new Buffer(0, count); }
    public static class Buffer extends Struct.Buffer {
        public Buffer(long address, int capacity) { super(address, capacity); }
        @Override public VkLayerProperties get(int index) { return new VkLayerProperties(); }
        public void free() {}
        
    }
}
