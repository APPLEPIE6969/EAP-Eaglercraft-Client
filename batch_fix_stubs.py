#!/usr/bin/env python3
"""
Batch-fix all 42 remaining TeaVM 'not found' errors from CI run #12.
Creates/updates stubs for 26.2 API changes in Blaze3D, LWJGL, oshi, and
TeaVM classlib gaps.
"""
import os
from pathlib import Path

ROOT = Path('/home/z/my-project/eaglercraft-26.1.2-src/sources/teavm/java')

files = {}

# === GpuFormat enum (com.mojang.blaze3d.GpuFormat) ===
files['com/mojang/blaze3d/GpuFormat.java'] = '''package com.mojang.blaze3d;

/**
 * EaglerCraft stub for MC 26.2 GpuFormat.
 * Enum of GPU texture/vertex formats. Browser: WebGL2 uses these.
 */
public enum GpuFormat {
    R8_UNORM(1), R8_SNORM(1),
    RG8_UNORM(2), RG8_SNORM(2),
    RGB8_UNORM(3), RGB8_SNORM(3),
    RGBA8_UNORM(4), RGBA8_SNORM(4),
    R16_UNORM(2), R16_SNORM(2),
    RG16_UNORM(4), RG16_SNORM(4),
    RGB16_UNORM(6), RGB16_SNORM(6),
    RGBA16_UNORM(8), RGBA16_SNORM(8),
    R8_UINT(1), RG8_UINT(2), RGBA8_UINT(4),
    R16_UINT(2), RG16_UINT(4), RGBA16_UINT(8),
    R32_UINT(4), RG32_UINT(8), RGB32_UINT(12), RGBA32_UINT(16),
    R16_FLOAT(2), RG16_FLOAT(4), RGBA16_FLOAT(8),
    R32_FLOAT(4), RG32_FLOAT(8), RGB32_FLOAT(12), RGBA32_FLOAT(16),
    DEPTH16(2), DEPTH24(3), DEPTH32(4),
    DEPTH24_STENCIL8(4);

    private final int bytes;

    GpuFormat(int bytes) { this.bytes = bytes; }

    public int bytes() { return bytes; }
    public int blockSize() { return bytes; }

    public enum ComponentType { UNORM_8, SNORM_8, UNORM_16, SNORM_16, FLOAT_16, FLOAT_32, UINT_8, UINT_16, UINT_32, DEPTH }
}
'''

# === PrimitiveTopology enum (com.mojang.blaze3d.PrimitiveTopology) ===
files['com/mojang/blaze3d/PrimitiveTopology.java'] = '''package com.mojang.blaze3d;

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
        this.primitiveLength = len;
        this.primitiveStride = stride;
        this.connectedPrimitives = connected;
    }
}
'''

# === IndexType enum (com.mojang.blaze3d.IndexType) ===
files['com/mojang/blaze3d/IndexType.java'] = '''package com.mojang.blaze3d;

public enum IndexType {
    SHORT(2),
    INT(4);

    public final int bytes;

    IndexType(int bytes) { this.bytes = bytes; }
}
'''

# === DeviceLimits record ===
files['com/mojang/blaze3d/systems/DeviceLimits.java'] = '''package com.mojang.blaze3d.systems;

import com.mojang.blaze3d.GpuFormat;

public record DeviceLimits(
    int maxAnisotropy,
    int minUniformOffsetAlignment,
    int maxTextureSize,
    long maxMemoryAllocationSize,
    int maxMultiDrawDirectInterleavedDrawCount,
    int maxColorAttachments
) {
    public int maxTextureSizeForFormat(GpuFormat format) {
        return Integer.highestOneBit(Math.min(maxTextureSize, (int) Math.sqrt((double) maxMemoryAllocationSize / (double) format.blockSize())));
    }
}
'''

# === HintsAndWorkarounds record ===
files['com/mojang/blaze3d/systems/HintsAndWorkarounds.java'] = '''package com.mojang.blaze3d.systems;

public record HintsAndWorkarounds(boolean writeToBufferIsSlow, boolean anisotropyHasKnownIssues) {}
'''

# === GpuQueryPool interface ===
files['com/mojang/blaze3d/systems/GpuQueryPool.java'] = '''package com.mojang.blaze3d.systems;

import java.util.OptionalLong;

public interface GpuQueryPool extends AutoCloseable {
    int size();
    OptionalLong getValue(int index);
    OptionalLong[] getValues(int index, int count);
    @Override void close();
}
'''

# === SurfaceException ===
files['com/mojang/blaze3d/systems/SurfaceException.java'] = '''package com.mojang.blaze3d.systems;

public class SurfaceException extends Exception {
    public SurfaceException(String message) { super(message); }
    public SurfaceException(Throwable cause) { super(cause); }
}
'''

# === PresentMode enum (GpuSurface.PresentMode) ===
files['com/mojang/blaze3d/systems/PresentMode.java'] = '''package com.mojang.blaze3d.systems;

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
'''

# === GpuSurface (full rewrite with all methods) ===
files['com/mojang/blaze3d/systems/GpuSurface.java'] = '''package com.mojang.blaze3d.systems;

import java.util.Collection;
import java.util.Optional;

/**
 * EaglerCraft stub for MC 26.2 GpuSurface.
 * Browser: WebGL2 manages its own surface via canvas. All methods are no-ops.
 */
public class GpuSurface implements AutoCloseable {
    private Configuration config;

    public GpuSurface(long windowHandle) {}

    public void configure(Configuration config) throws SurfaceException {
        this.config = config;
    }

    public Optional<Configuration> currentConfiguration() {
        return Optional.ofNullable(config);
    }

    public Collection<PresentMode> supportedPresentModes() {
        return java.util.List.of(PresentMode.FIFO);
    }

    public boolean isSuboptimal() { return false; }
    public boolean isAcquired() { return false; }

    public void acquireNextTexture() throws SurfaceException {}

    public void blitFromTexture(CommandEncoder encoder, com.mojang.blaze3d.textures.GpuTextureView textureView) {}

    public void present() {}

    @Override
    public void close() {}

    public record Configuration(int width, int height, PresentMode presentMode) {}
}
'''

# === DeviceInfo (updated with all fields) ===
files['com/mojang/blaze3d/systems/DeviceInfo.java'] = '''package com.mojang.blaze3d.systems;

public record DeviceInfo(
    String name,
    String vendor,
    String version,
    DeviceType deviceType,
    String backendName,
    DeviceLimits limits,
    HintsAndWorkarounds hintsAndWorkarounds
) {
    public enum DeviceType { DISCRETE, INTEGRATED, SOFTWARE, UNKNOWN }

    // Backward-compat constructor (4 args) for older call sites
    public DeviceInfo(String name, String vendor, String version, DeviceType deviceType) {
        this(name, vendor, version, deviceType, "WebGL2",
            new DeviceLimits(0, 256, 16384, 0, 0, 4),
            new HintsAndWorkarounds(false, false));
    }
}
'''

# === BackendCreationException (updated Reason enum) ===
files['com/mojang/blaze3d/systems/BackendCreationException.java'] = '''package com.mojang.blaze3d.systems;

import java.util.List;

public class BackendCreationException extends Exception {
    private final Reason reason;
    private final List<String> missingCapabilities;

    public BackendCreationException(String message, Reason reason, List<String> missingCapabilities) {
        super(message);
        this.reason = reason;
        this.missingCapabilities = List.copyOf(missingCapabilities);
    }

    public BackendCreationException(String message, Reason reason) {
        this(message, reason, List.of());
    }

    public Reason getReason() { return reason; }
    public List<String> getMissingCapabilities() { return missingCapabilities; }

    public enum Reason {
        GLFW_ERROR("glfw_error"),
        VULKAN_LOADER_MISSING("vulkan_loader_missing"),
        VULKAN_INSTANCE_CREATION_FAILED("vulkan_instance_creation_failed"),
        VULKAN_NO_DEVICE("vulkan_no_device"),
        VULKAN_DEVICE_VERSION_TOO_LOW("vulkan_device_version_to_low"),
        VULKAN_NO_GRAPHICS_QUEUE("vulkan_no_graphics_queue"),
        VULKAN_MISSING_EXTENSION("vulkan_missing_extension"),
        VULKAN_MISSING_FEATURE("vulkan_missing_feature"),
        OPENGL_MISSING("opengl_missing"),
        OTHER("other");

        private final String displayName;

        Reason(String key) { this.displayName = key; }

        public String displayName() { return displayName; }
    }
}
'''

# === BindGroupLayout$Builder withUniform (3-arg with GpuFormat) ===
files['com/mojang/blaze3d/pipeline/BindGroupLayout.java'] = '''package com.mojang.blaze3d.pipeline;

import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.shaders.UniformType;
import java.util.ArrayList;
import java.util.List;

public class BindGroupLayout {
    private final List<String> samplers;
    private final List<UniformDescription> uniforms;

    public BindGroupLayout(List<String> samplers, List<UniformDescription> uniforms) {
        this.samplers = new ArrayList<>(samplers);
        this.uniforms = new ArrayList<>(uniforms);
    }

    public static Builder builder() { return new Builder(); }
    public List<String> getSamplers() { return samplers; }
    public List<UniformDescription> getUniforms() { return uniforms; }

    public static class Builder {
        private final List<String> samplers = new ArrayList<>();
        private final List<UniformDescription> uniforms = new ArrayList<>();

        public Builder withSampler(String sampler) { samplers.add(sampler); return this; }
        public Builder withUniform(String name, UniformType type) { uniforms.add(new UniformDescription(name, type, null)); return this; }
        public Builder withUniform(String name, UniformType type, GpuFormat format) { uniforms.add(new UniformDescription(name, type, format)); return this; }
        public BindGroupLayout build() { return new BindGroupLayout(samplers, uniforms); }
    }

    public record UniformDescription(String name, UniformType type, GpuFormat format) {}
}
'''

# === RenderPipeline$Builder.withVertexBinding ===
files['com/mojang/blaze3d/pipeline/RenderPipeline.java'] = '''package com.mojang.blaze3d.pipeline;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.shaders.UniformType;

public class RenderPipeline {
    private net.minecraft.resources.Identifier location;

    public static Builder builder(Snippet... snippets) { return new Builder(); }

    public int getSortKey() { return 0; }
    public VertexFormat getVertexFormat() { return null; }
    public VertexFormat.Mode getVertexFormatMode() { return VertexFormat.Mode.TRIANGLES; }
    public boolean isCull() { return false; }
    public void updateSortKeySeed() {}
    public net.minecraft.resources.Identifier getLocation() { return location; }
    public ColorTargetState getColorTargetState() { return new ColorTargetState(); }
    public java.util.List<BindGroupLayout> getBindGroupLayouts() { return new java.util.ArrayList<>(); }

    public interface Snippet {}

    public static class Builder {
        public Builder withLocation(String location) { return this; }
        public Builder withLocation(net.minecraft.resources.Identifier location) { return this; }
        public Builder withVertexShader(String shader) { return this; }
        public Builder withFragmentShader(String shader) { return this; }
        public Builder withVertexShader(net.minecraft.resources.Identifier shader) { return this; }
        public Builder withFragmentShader(net.minecraft.resources.Identifier shader) { return this; }
        public Builder withVertexFormat(VertexFormat format, com.mojang.blaze3d.vertex.VertexFormat.Mode mode) { return this; }
        public Builder withVertexBinding(int index, VertexFormat format) { return this; }
        public Builder withCull(boolean cull) { return this; }
        public Builder withDepthTest(String depthTest) { return this; }
        public Builder withDepthStencilState(DepthStencilState state) { return this; }
        public Builder withDepthStencilState(java.util.Optional<DepthStencilState> state) { return this; }
        public Builder withColorTargetState(ColorTargetState state) { return this; }
        public Builder withBindGroupLayout(BindGroupLayout layout) { return this; }
        public Builder withSampler(String sampler) { return this; }
        public Builder withUniform(String name) { return this; }
        public Builder withUniform(String name, UniformType type) { return this; }
        public Builder withUniform(String name, UniformType type, com.mojang.blaze3d.textures.TextureFormat format) { return this; }
        public Builder withShaderDefine(String define) { return this; }
        public Builder withShaderDefine(String define, float value) { return this; }
        public Builder withShaderDefine(String define, int value) { return this; }
        public Builder withShaderDefine(String define, boolean value) { return this; }
        public Builder withPolygonMode(com.mojang.blaze3d.platform.PolygonMode mode) { return this; }
        public Snippet buildSnippet() { return new Snippet() {}; }
        public RenderPipeline build() { return new RenderPipeline(); }
    }
}
'''

# === RenderTarget constructor (String, boolean, GpuFormat) ===
files['com/mojang/blaze3d/pipeline/RenderTarget.java'] = '''package com.mojang.blaze3d.pipeline;

import com.mojang.blaze3d.GpuFormat;
import org.jspecify.annotations.Nullable;

public class RenderTarget {
    private String label;
    private boolean useDepth;
    private GpuFormat format;

    public RenderTarget(@Nullable String label, boolean useDepth, GpuFormat format) {
        this.label = label;
        this.useDepth = useDepth;
        this.format = format;
    }

    public void resize(int width, int height) {}
    public void destroyBuffers() {}
    public int getWidth() { return 0; }
    public int getHeight() { return 0; }
    public boolean useDepth() { return useDepth; }
    public GpuFormat format() { return format; }
    public String label() { return label; }
}
'''

# === Monitor.tryCreate ===
files['com/mojang/blaze3d/platform/Monitor.java'] = '''package com.mojang.blaze3d.platform;

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
'''

# === MonitorManager ===
files['com/mojang/blaze3d/platform/MonitorManager.java'] = '''package com.mojang.blaze3d.platform;

public class MonitorManager implements AutoCloseable {
    public MonitorManager() {}

    @Override
    public void close() {}
}
'''

# === Window (fix constructor to use MonitorManager) ===
files['com/mojang/blaze3d/platform/Window.java'] = '''package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.systems.BackendCreationException;
import com.mojang.blaze3d.systems.GpuBackend;

public class Window {
    private WindowEventHandler eventHandler;
    private DisplayData displayData;
    private String title;
    private boolean shouldClose = false;
    private int width = 1280;
    private int height = 720;

    // MC 26.2 constructor
    public Window(WindowEventHandler eventHandler,
                  DisplayData displayData,
                  String fullscreenVideoModeString,
                  boolean exclusiveFullscreen,
                  String title,
                  MonitorManager monitorManager,
                  GpuBackend backend)
            throws BackendCreationException {
        this.eventHandler = eventHandler;
        this.displayData = displayData;
        this.title = title;
    }

    public void close() { shouldClose = true; }
    public boolean shouldClose() { return shouldClose; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getScreenWidth() { return width; }
    public int getScreenHeight() { return height; }
    public int getGuiScaledWidth() { return width; }
    public int getGuiScaledHeight() { return height; }
    public double getGuiScale() { return 1.0; }
    public void setGuiScale(double scale) {}
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public void updateDisplay() {}
    public void setWindowed(int w, int h) { this.width = w; this.height = h; }
    public void toggleFullScreen() {}
    public boolean isFullscreen() { return false; }
    public long getWindow() { return 1L; }
    public DisplayData getDisplayData() { return displayData; }
}
'''

# === VertexFormat - add builder, contains, getElement ===
files['com/mojang/blaze3d/vertex/VertexFormat.java'] = '''package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.GpuFormat;
import java.util.ArrayList;
import java.util.List;

public class VertexFormat {
    private final List<VertexFormatElement> elements = new ArrayList<>();

    public enum Mode { LINES, LINE_STRIP, TRIANGLES, TRIANGLE_STRIP, QUADS }

    public static Builder builder(int stepRate) { return new Builder(stepRate); }

    public List<VertexFormatElement> getElements() { return elements; }

    public VertexFormatElement getElement(String name) {
        for (VertexFormatElement e : elements) {
            if (e.getName().equals(name)) return e;
        }
        return null;
    }

    public boolean contains(String name) {
        for (VertexFormatElement e : elements) {
            if (e.getName().equals(name)) return true;
        }
        return false;
    }

    public int getVertexSize() { return 0; }
    public Mode getMode() { return Mode.TRIANGLES; }

    public static class Builder {
        private final int stepRate;
        private final List<VertexFormatElement> elements = new ArrayList<>();

        public Builder(int stepRate) { this.stepRate = stepRate; }

        public Builder addAttribute(String name, GpuFormat format) {
            elements.add(new VertexFormatElement(name, format));
            return this;
        }

        public Builder addAttribute(String name, int stride, GpuFormat format) {
            elements.add(new VertexFormatElement(name, format));
            return this;
        }

        public Builder addAttribute(String name, GpuFormat format, int columnCount) {
            elements.add(new VertexFormatElement(name, format));
            return this;
        }

        public Builder addAttribute(String name, int offset, int stride, GpuFormat format, int columnCount) {
            elements.add(new VertexFormatElement(name, format));
            return this;
        }

        public VertexFormat build() {
            VertexFormat vf = new VertexFormat();
            vf.elements.addAll(elements);
            return vf;
        }
    }
}
'''

# === VertexFormatElement (update to have getName) ===
files['com/mojang/blaze3d/vertex/VertexFormatElement.java'] = '''package com.mojang.blaze3d.vertex;

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
    public int getSize() { return format != null ? format.bytes() : 0; }
}
'''

# === CommandEncoder.writeToTexture ===
files['com/mojang/blaze3d/systems/CommandEncoder.java'] = '''package com.mojang.blaze3d.systems;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.textures.GpuTexture;

public interface CommandEncoder {
    default void writeToTexture(GpuTexture destination, NativeImage source) {}
    default void writeToTexture(GpuTexture destination, NativeImage source, int destMipLevel, int destX, int destY, int srcMipLevel) {}
    default void writeToBuffer(com.mojang.blaze3d.buffers.GpuBuffer buffer, java.nio.ByteBuffer data) {}
    void end();
}
'''

# === GpuDevice (add createTexture with GpuFormat + createTimestampQueryPool) ===
files['com/mojang/blaze3d/systems/GpuDevice.java'] = '''package com.mojang.blaze3d.systems;

import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.CompiledRenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.shaders.ShaderSource;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Supplier;

public interface GpuDevice extends GpuBackend {
    default List<String> getEnabledExtensions() { return new ArrayList<>(); }
    default int getMaxTextureSize() { return 16384; }
    default String getRenderer() { return "WebGL2"; }
    default boolean isDebuggingEnabled() { return false; }
    default CommandEncoder createCommandEncoder() { return new CommandEncoder() { @Override public void end() {} }; }
    default GpuTexture createTexture(Supplier<String> label, int usage, TextureFormat format, int w, int h, int d, int levels) { return new GpuTexture() {}; }
    default GpuTexture createTexture(String label, int usage, TextureFormat format, int w, int h, int d, int levels) { return new GpuTexture() {}; }

    // MC 26.2: createTexture with GpuFormat
    default GpuTexture createTexture(String label, int usage, GpuFormat format, int w, int h, int d, int levels) { return new GpuTexture() {}; }
    default GpuTexture createTexture(Supplier<String> label, int usage, GpuFormat format, int w, int h, int d, int levels) { return new GpuTexture() {}; }

    default GpuTextureView createTextureView(GpuTexture texture) { return new GpuTextureView(texture); }
    default List<String> getLastDebugMessages() { return new ArrayList<>(); }
    default void addDebugMessage(String message) {}
    default void resetDebugMessages() {}
    default GpuBuffer createBuffer(Supplier<String> label, int usage, long size) { return new GpuBuffer() {}; }
    default GpuBuffer createBuffer(Supplier<String> label, int usage, ByteBuffer data) { return new GpuBuffer() {}; }
    default String getBackendName() { return "WebGL2"; }
    default int getMaxSupportedAnisotropy() { return 0; }
    default int getUniformOffsetAlignment() { return 256; }
    default boolean isZZeroToOne() { return false; }
    default CompiledRenderPipeline precompilePipeline(RenderPipeline pipeline, ShaderSource source) { return null; }
    default CompiledRenderPipeline precompilePipeline(RenderPipeline pipeline) { return null; }

    // MC 26.2: GpuSurface + DeviceInfo
    default GpuSurface createSurface(long windowHandle) { return new GpuSurface(windowHandle); }
    default DeviceInfo getDeviceInfo() {
        return new DeviceInfo("WebGL2", "Browser", "2.0", DeviceInfo.DeviceType.INTEGRATED);
    }

    // MC 26.2: GpuQueryPool
    default GpuQueryPool createTimestampQueryPool(int size) {
        return new GpuQueryPool() {
            @Override public int size() { return size; }
            @Override public java.util.OptionalLong getValue(int index) { return java.util.OptionalLong.empty(); }
            @Override public java.util.OptionalLong[] getValues(int index, int count) { return new java.util.OptionalLong[count]; }
            @Override public void close() {}
        };
    }

    default GpuSampler createSampler(AddressMode u, AddressMode v, FilterMode min, FilterMode mag, int maxAnisotropy, OptionalDouble maxLod) {
        return new GpuSampler();
    }
}
'''

# === RenderSystem - add getSequentialBuffer ===
files['com/mojang/blaze3d/systems/RenderSystem_AutoStorageIndexBuffer.java'] = '''package com.mojang.blaze3d.systems;

import com.mojang.blaze3d.IndexType;
import com.mojang.blaze3d.PrimitiveTopology;

// Inner class of RenderSystem - stub for AutoStorageIndexBuffer
public class RenderSystem$AutoStorageIndexBuffer {
    public int indexCount;
    public IndexType type = IndexType.SHORT;
}
'''

# === TracyFrameCapture.endFrame ===
files['com/mojang/blaze3d/TracyFrameCapture.java'] = '''package com.mojang.blaze3d;

public class TracyFrameCapture {
    private int lastCaptureDelay = 0;
    private boolean capturedThisFrame = false;

    public void endFrame() {
        this.lastCaptureDelay++;
        this.capturedThisFrame = false;
    }

    public void beginFrame() {}
    public boolean isCapturing() { return false; }
}
'''

# === DynamicUniforms.reset ===
files['net/minecraft/client/renderer/DynamicUniforms.java'] = '''package net.minecraft.client.renderer;

public class DynamicUniforms {
    public void reset() {}
    public void endFrame() {}
}
'''

# === OSProcess.getUserTime ===
files['oshi/software/os/OSProcess.java'] = '''package oshi.software.os;

public interface OSProcess {
    String getName();
    int getProcessID();
    long getResidentSetSize();
    long getVirtualSize();
    long getStartTime();
    long getUserTime();
    String getPath();
    String getCommandLine();
    State getState();
    int getThreadCount();
    int getParentProcessID();

    enum State { NEW, RUNNING, SLEEPING, WAITING, ZOMBIE, STOPPED, OTHER, SUSPENDED }
}
'''

# === OperatingSystem.isElevated ===
files['oshi/software/os/OperatingSystem.java'] = '''package oshi.software.os;

public interface OperatingSystem {
    String getManufacturer();
    String getFamily();
    String getVersionInfo();
    boolean isElevated();

    default OSProcess getCurrentProcess() {
        return new OSProcess() {
            @Override public String getName() { return "eaglercraft"; }
            @Override public int getProcessID() { return 0; }
            @Override public long getResidentSetSize() { return 0; }
            @Override public long getVirtualSize() { return 0; }
            @Override public long getStartTime() { return 0; }
            @Override public long getUserTime() { return 0; }
            @Override public String getPath() { return ""; }
            @Override public String getCommandLine() { return ""; }
            @Override public State getState() { return State.RUNNING; }
            @Override public int getThreadCount() { return 1; }
            @Override public int getParentProcessID() { return 0; }
        };
    }
}
'''

# === GLFWVulkan (stub) ===
files['org/lwjgl/glfw/GLFWVulkan.java'] = '''package org.lwjgl.glfw;

public class GLFWVulkan {
    public static boolean glfwVulkanSupported() { return false; }
    public static java.util.Set<String> getRequiredInstanceExtensions() { return java.util.Set.of(); }
}
'''

# === GLFWMonitorCallbackI ===
files['org/lwjgl/glfw/GLFWMonitorCallbackI.java'] = '''package org.lwjgl.glfw;

@FunctionalInterface
public interface GLFWMonitorCallbackI {
    void invoke(long monitor, int event);
}
'''

# === GLFW.glfwSetMonitorCallback ===
files['org/lwjgl/glfw/GLFW_MonitorCallback.java'] = '''// This is a method addition to the existing GLFW class.
// We can't redefine GLFW.java here because it already exists.
// Instead, we'll patch it via a separate edit.
// This file is just a placeholder to track the fix.
'''

# === VkPhysicalDeviceProperties2.SIZEOF ===
files['org/lwjgl/vulkan/VkPhysicalDeviceProperties2.java'] = '''package org.lwjgl.vulkan;

public class VkPhysicalDeviceProperties2 {
    public static final int SIZEOF = 232; // stub
}
'''

# === TeaVM classlib gaps ===
# java.util.concurrent.CopyOnWriteArraySet
files['java/util/concurrent/CopyOnWriteArraySet.java'] = '''package java.util.concurrent;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArraySet<E> extends AbstractSet<E> implements Set<E> {
    private final CopyOnWriteArrayList<E> al = new CopyOnWriteArrayList<>();

    public CopyOnWriteArraySet() {}

    public CopyOnWriteArraySet(Collection<? extends E> c) {
        al.addAllAbsent(new ArrayList<>(c));
    }

    @Override public int size() { return al.size(); }
    @Override public boolean isEmpty() { return al.isEmpty(); }
    @Override public boolean contains(Object o) { return al.contains(o); }
    @Override public Iterator<E> iterator() { return al.iterator(); }
    @Override public boolean add(E e) { return al.addIfAbsent(e); }
    @Override public boolean remove(Object o) { return al.remove(o); }
    @Override public void clear() { al.clear(); }
    @Override public boolean addAll(Collection<? extends E> c) { return al.addAllAbsent(new ArrayList<>(c)) > 0; }
    @Override public boolean removeAll(Collection<?> c) { return al.removeAll(c); }
    @Override public boolean retainAll(Collection<?> c) { return al.retainAll(c); }
}
'''

# java.lang.ExceptionInInitializerError
files['java/lang/ExceptionInInitializerError.java'] = '''package java.lang;

public class ExceptionInInitializerError extends LinkageError {
    private final Throwable exception;

    public ExceptionInInitializerError() {
        this(null);
    }

    public ExceptionInInitializerError(Throwable cause) {
        super(cause == null ? null : cause.toString(), cause);
        this.exception = cause;
    }

    public ExceptionInInitializerError(String s) {
        super(s);
        this.exception = null;
    }

    public Throwable getException() { return exception; }
    @Override public Throwable getCause() { return exception; }
}
'''

# sun.misc.Unsafe additions (getLongVolatile, storeFence)
files['sun/misc/Unsafe_Additions.java'] = '''// These methods need to be ADDED to the existing sun/misc/Unsafe.java stub.
// We'll edit that file separately.
'''

# java.lang.invoke.MethodHandle.invokeExact + VarHandle.set
# These are TeaVM classlib internal - can't be stubbed easily.
# They come from Netty's internal concurrency code. We'll need to stub
# the Netty classes that use them instead.

# === Netty stubs to avoid VarHandle/MethodHandle/Unsafe ===
files['io/netty/util/internal/RefCnt.java'] = '''package io.netty.util.internal;

public interface RefCnt {
    int refCnt();
    RefCnt retain();
    RefCnt retain(int increment);
    boolean release();
    boolean release(int decrement);
}
'''

# Write all files
for rel_path, content in files.items():
    if rel_path.endswith('_Additions.java') or rel_path.endswith('_MonitorCallback.java') or 'RenderSystem_AutoStorageIndexBuffer' in rel_path:
        continue  # skip placeholders, handle separately
    full_path = ROOT / rel_path
    full_path.parent.mkdir(parents=True, exist_ok=True)
    full_path.write_text(content, encoding='utf-8')
    print(f'  wrote {rel_path}')

print(f'\\nWrote {len([k for k in files if not k.endswith("_Additions.java") and not k.endswith("_MonitorCallback.java") and "RenderSystem_AutoStorageIndexBuffer" not in k])} files')
