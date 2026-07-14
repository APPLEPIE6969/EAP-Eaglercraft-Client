package net.minecraft.client.renderer;

public class DynamicUniforms {
    public void reset() {}
    public void endFrame() {}

    public com.mojang.blaze3d.buffers.GpuBufferSlice writeTransform(org.joml.Matrix4f matrix) { return null; }
    public com.mojang.blaze3d.buffers.GpuBufferSlice writeTransform(org.joml.Matrix4f matrix, org.joml.Vector4f vec) { return null; }
    public com.mojang.blaze3d.buffers.GpuBufferSlice writeTransform(org.joml.Matrix4f m1, org.joml.Matrix4f m2) { return null; }
    public com.mojang.blaze3d.buffers.GpuBufferSlice writeTransform(org.joml.Matrix4f m1, org.joml.Vector4f v1, org.joml.Vector3f v2, org.joml.Matrix4f m2) { return null; }
    public com.mojang.blaze3d.buffers.GpuBufferSlice[] writeChunkSections(ChunkSectionInfo[] sections) { return null; }

    public record ChunkSectionInfo(org.joml.Matrix4fc matrix, int x, int y, int z, float f, int i1, int i2) {}

}
