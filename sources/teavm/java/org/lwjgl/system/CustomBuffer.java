package org.lwjgl.system;

import java.nio.ByteBuffer;

public class CustomBuffer<T extends CustomBuffer<T>> {
    protected long address;
    protected int capacity;
    protected int position;

    public CustomBuffer() {}

    public CustomBuffer(long address, int capacity) {
        this.address = address;
        this.capacity = capacity;
    }

    public CustomBuffer(ByteBuffer container, int capacity) {
        this.address = 0;
        this.capacity = capacity;
    }

    public int capacity() { return capacity; }
    public int position() { return position; }
    @SuppressWarnings("unchecked")
    public T position(int pos) { this.position = pos; return (T) this; }
    public long address() { return address; }
    @SuppressWarnings("unchecked")
    public T flip() { return (T) this; }
    @SuppressWarnings("unchecked")
    public T clear() { position = 0; return (T) this; }
}
