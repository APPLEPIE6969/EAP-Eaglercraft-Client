package org.lwjgl.system;

public abstract class Callback implements AutoCloseable {
    private final long pointer;

    public Callback(long pointer) {
        this.pointer = pointer;
    }

    public Callback() {
        this.pointer = 0;
    }

    public long address() {
        return pointer;
    }

    @Override
    public void close() {}
}
