package com.mojang.blaze3d.platform.cursor;
public enum CursorType {
    DEFAULT, POINTER, CROSSHAIR, TEXT, WAIT, RESIZE;

    public static CursorType createStandardCursor(int shape, String name, CursorType fallback) {
        return DEFAULT;
    }
}
