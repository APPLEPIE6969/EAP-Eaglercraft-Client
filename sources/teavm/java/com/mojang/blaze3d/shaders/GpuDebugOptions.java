package com.mojang.blaze3d.shaders;

/**
 * EaglerCraft stub for MC 26.2 GpuDebugOptions.
 *
 * MC 26.2: GpuDebugOptions is a Record (not a class):
 *   record GpuDebugOptions(int logLevel, boolean synchronousLogs,
 *                          boolean useLabels, boolean useValidationLayers)
 *
 * Browser: all options default off. This record is used only during GPU
 * device creation; the EaglerCraft WebGL2 backend ignores the values.
 */
public record GpuDebugOptions(int logLevel, boolean synchronousLogs, boolean useLabels, boolean useValidationLayers) {
    public static final GpuDebugOptions DEFAULT = new GpuDebugOptions(0, false, false, false);
}
