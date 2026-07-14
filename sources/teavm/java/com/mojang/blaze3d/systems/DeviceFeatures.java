package com.mojang.blaze3d.systems;

public record DeviceFeatures(
    boolean multiDrawIndirect,
    boolean multiDrawBaseInstance,
    boolean pipelineStatisticsQuery,
    boolean timestampQuery,
    boolean persistentMapping
) {
    public DeviceFeatures() {
        this(false, false, false, false, false);
    }
}
