package org.lwjgl.system;

public class Struct extends CustomBuffer<Struct> {
    public Struct() {}
    public Struct(long address, int capacity) { super(address, capacity); }

    public static class Buffer extends CustomBuffer<Buffer> {
        public Buffer() {}
        public Buffer(long address, int capacity) { super(address, capacity); }
        public Struct get(int index) { return new Struct(); }
        public void free() {}
        public java.util.Iterator<Struct> iterator() { return java.util.Collections.emptyIterator(); }
    }
}
