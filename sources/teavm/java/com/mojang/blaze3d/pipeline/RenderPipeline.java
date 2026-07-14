package com.mojang.blaze3d.pipeline;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.shaders.UniformType;

public class RenderPipeline {
    private net.minecraft.resources.Identifier location;

    public static Builder builder(Snippet... snippets) { return new Builder(); }

    public com.mojang.blaze3d.PrimitiveTopology getPrimitiveTopology() { return com.mojang.blaze3d.PrimitiveTopology.TRIANGLES; }
    public int getSortKey() { return 0; }
    public VertexFormat getVertexFormat() { return null; }
    public VertexFormat.Mode getVertexFormatMode() { return VertexFormat.Mode.TRIANGLES; }
    public boolean isCull() { return false; }
    public void updateSortKeySeed() {}
    public net.minecraft.resources.Identifier getLocation() { return location; }
    public ColorTargetState getColorTargetState() { return new ColorTargetState(); }
    public java.util.List<BindGroupLayout> getBindGroupLayouts() { return new java.util.ArrayList<>(); }
    public com.mojang.blaze3d.vertex.VertexFormat getVertexFormatBinding(int index) { return null; }

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
        public Builder withPrimitiveTopology(com.mojang.blaze3d.PrimitiveTopology topology) { return this; }
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
