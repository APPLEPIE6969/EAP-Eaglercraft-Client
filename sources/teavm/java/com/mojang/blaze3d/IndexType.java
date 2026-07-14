package com.mojang.blaze3d;

public enum IndexType {
    SHORT(2),
    INT(4);

    public final int bytes;

    IndexType(int bytes) { this.bytes = bytes; }

    public static IndexType least(int vertexCount) { return vertexCount > 65535 ? INT : SHORT; }
}
