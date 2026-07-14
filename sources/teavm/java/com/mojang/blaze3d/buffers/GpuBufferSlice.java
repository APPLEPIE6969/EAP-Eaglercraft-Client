package com.mojang.blaze3d.buffers;

public class GpuBufferSlice {
    private final GpuBuffer buffer;
    private final long offset;
    private final long size;

    public GpuBufferSlice(GpuBuffer buffer, long offset, long size) {
        this.buffer = buffer;
        this.offset = offset;
        this.size = size;
    }

    public GpuBufferSlice() {
        this.buffer = null;
        this.offset = 0;
        this.size = 0;
    }

    public GpuBuffer buffer() { return buffer; }
    public long offset() { return offset; }
    public long size() { return size; }

    public MappedView map(boolean read, boolean write) { return null; }

    public static class MappedView {
        public void close() {}
        public java.nio.ByteBuffer data() { return java.nio.ByteBuffer.allocate(0); }
    }
}
