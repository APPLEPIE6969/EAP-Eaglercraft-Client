package com.mojang.blaze3d;

/**
 * EaglerCraft stub for MC 26.2 PrimitiveTopology.
 */
public enum PrimitiveTopology {
    LINES(2, 2, false),
    DEBUG_LINES(2, 2, false),
    DEBUG_LINE_STRIP(2, 1, true),
    POINTS(1, 1, false),
    TRIANGLES(3, 3, false),
    TRIANGLE_STRIP(3, 1, true),
    TRIANGLE_FAN(3, 1, true),
    QUADS(4, 4, false);

    public final int primitiveLength;
    public final int primitiveStride;
    public final boolean connectedPrimitives;

    PrimitiveTopology(int len, int stride, boolean connected) {
        // indexCount in constructor
        this.primitiveLength = len;
        this.primitiveStride = stride;
        this.connectedPrimitives = connected;
    }

    public int indexCount(int vertexCount) { return vertexCount; }
}
