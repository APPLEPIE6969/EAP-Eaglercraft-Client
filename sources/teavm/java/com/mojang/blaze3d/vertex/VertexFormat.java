package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.GpuFormat;
import java.util.ArrayList;
import java.util.List;

public class VertexFormat {
    private final List<VertexFormatElement> elements = new ArrayList<>();

    public enum Mode { LINES, LINE_STRIP, TRIANGLES, TRIANGLE_STRIP, QUADS }

    public enum IndexType { SHORT, INT }

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
