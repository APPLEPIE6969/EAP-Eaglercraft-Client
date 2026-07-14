package com.mojang.blaze3d.systems;

import java.util.Set;

public class DeviceInfo {
    private final String name;
    private final String vendorName;
    private final String driverInfo;
    private final boolean isZZeroToOne;
    private final String backendName;
    private final float timestampPeriod;
    private final DeviceLimits limits;
    private final DeviceFeatures features;
    private final Set<String> underlyingExtensions;
    private final HintsAndWorkarounds hintsAndWorkarounds;
    private final DeviceType type;

    public DeviceInfo(String name, String vendorName, String driverInfo,
                      boolean isZZeroToOne, String backendName, float timestampPeriod,
                      DeviceLimits limits, DeviceFeatures features,
                      Set<String> underlyingExtensions, HintsAndWorkarounds hintsAndWorkarounds,
                      DeviceType type) {
        this.name = name;
        this.vendorName = vendorName;
        this.driverInfo = driverInfo;
        this.isZZeroToOne = isZZeroToOne;
        this.backendName = backendName;
        this.timestampPeriod = timestampPeriod;
        this.limits = limits;
        this.features = features;
        this.underlyingExtensions = underlyingExtensions;
        this.hintsAndWorkarounds = hintsAndWorkarounds;
        this.type = type;
    }

    public String name() { return name; }
    public String vendorName() { return vendorName; }
    public String driverInfo() { return driverInfo; }
    public boolean isZZeroToOne() { return isZZeroToOne; }
    public String backendName() { return backendName; }
    public float timestampPeriod() { return timestampPeriod; }
    public DeviceLimits limits() { return limits; }
    public DeviceFeatures features() { return features; }
    public Set<String> underlyingExtensions() { return underlyingExtensions; }
    public HintsAndWorkarounds hintsAndWorkarounds() { return hintsAndWorkarounds; }
    public DeviceType type() { return type; }

    // DeviceType is a separate top-level class in the vanilla JAR
}
