package com.mojang.blaze3d.pipeline;

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
