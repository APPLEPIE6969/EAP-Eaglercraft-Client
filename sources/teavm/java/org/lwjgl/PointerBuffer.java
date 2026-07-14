package org.lwjgl;

import java.nio.ByteBuffer;
import org.lwjgl.system.CustomBuffer;

public class PointerBuffer extends org.lwjgl.system.CustomBuffer<PointerBuffer> {
    private long[] data = new long[0];
    private int position = 0;

    public PointerBuffer(long address, int capacity) { super(address, capacity); }
    public PointerBuffer(java.nio.ByteBuffer container) { super(container, container != null ? container.capacity() / 8 : 0); }
    public PointerBuffer() {}




    public long get() { return data[position++]; }
    public long get(int index) { return data[index]; }
    public PointerBuffer put(long value) { return this; }
    public PointerBuffer put(int index, long value) { return this; }
    public int remaining() { return capacity() - position(); }
    public PointerBuffer put(java.nio.ByteBuffer buf) { return this; }
    public PointerBuffer put(int index, java.nio.ByteBuffer buf) { return this; }
    public PointerBuffer putUTF8(String s) { return this; }
    public String getStringUTF8() { return ""; }
    public ByteBuffer UTF8(CharSequence s) { return ByteBuffer.allocate(0); }
}
