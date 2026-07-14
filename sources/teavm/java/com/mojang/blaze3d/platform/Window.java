package com.mojang.blaze3d.platform;

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
    public int getGuiScale() { return 1; }
    
    public void setGuiScale(double scale) {}
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public void updateDisplay() {}
    public void setWindowed(int w, int h) { this.width = w; this.height = h; }
    public void toggleFullScreen() {}
    public boolean isFullscreen() { return false; }
    public long getWindow() { return 1L; }
    public DisplayData getDisplayData() { return displayData; }

    public int getFramerateLimit() { return 60; }
    public void setFramerateLimit(int limit) {}
    public com.mojang.blaze3d.systems.GpuBackend backend() { return new com.mojang.blaze3d.systems.GpuBackend() { @Override public String getName() { return "WebGL2"; } @Override public String getVendor() { return "Browser"; } @Override public String getVersion() { return "2.0"; } }; }
    public long handle() { return 1L; }
    public void setIcon(net.minecraft.server.packs.PackResources pack, IconSet iconSet) {}
    public void setErrorSection(String section) {}
    public void updateRawMouseInput(boolean enable) {}
    public void setAllowCursorChanges(boolean allow) {}
    public int calculateScale(int guiScale, boolean force) { return guiScale; }
    public java.util.Optional<VideoMode> getPreferredFullscreenVideoMode() { return java.util.Optional.empty(); }

    public void setWindowCloseCallback(Runnable callback) {}
    public void setDefaultErrorCallback() {}
    public void setGuiScale(int scale) {}
    public boolean isFocused() { return true; }

    public String getPlatform() { return "browser"; }
    public void updateFullscreenIfChanged() {}

    public boolean isMinimized() { return false; }
    public void selectCursor(com.mojang.blaze3d.platform.cursor.CursorType type) {}

    public float getAppropriateLineWidth() { return 1.0f; }
}
