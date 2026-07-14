package com.mojang.blaze3d.platform;

import org.jspecify.annotations.Nullable;

public class Monitor {
    private final long monitor;

    public Monitor(long monitor) { this.monitor = monitor; }

    public long getMonitor() { return monitor; }

    @Nullable
    public static Monitor tryCreate(long monitor) {
        return new Monitor(monitor);
    }
}
