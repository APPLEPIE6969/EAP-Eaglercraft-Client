package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.GpuFormat;

public class VertexFormatElement {
    private final String name;
    private final GpuFormat format;

    public VertexFormatElement(String name, GpuFormat format) {
        this.name = name;
        this.format = format;
    }

    public String getName() { return name; }
    public GpuFormat format() { return format; }
    public int offset() { return 0; }
    public int getSize() { return format != null ? format.bytes() : 0; }
}
