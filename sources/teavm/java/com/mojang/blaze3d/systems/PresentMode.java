package com.mojang.blaze3d.systems;

import java.util.Collection;

public enum PresentMode {
    IMMEDIATE, MAILBOX, FIFO, FIFO_RELAXED;

    private static final PresentMode[] VSYNC = {FIFO_RELAXED, FIFO};
    private static final PresentMode[] NO_VSYNC = {IMMEDIATE, MAILBOX, FIFO};

    public static PresentMode getSupportedVsyncMode(Collection<PresentMode> supported, boolean vsync) {
        PresentMode[] preferred = vsync ? VSYNC : NO_VSYNC;
        for (PresentMode p : preferred) {
            if (supported.contains(p)) return p;
        }
        return FIFO;
    }
}
