package com.mojang.blaze3d.pipeline;

import com.mojang.blaze3d.platform.BlendFactor;

public record BlendFunction(BlendFactor srcColor, BlendFactor dstColor, BlendFactor srcAlpha, BlendFactor dstAlpha) {
    public static final BlendFunction LIGHTNING = new BlendFunction(BlendFactor.SRC_ALPHA, BlendFactor.ONE);
    public static final BlendFunction GLINT = new BlendFunction(BlendFactor.SRC_COLOR, BlendFactor.ONE, BlendFactor.ZERO, BlendFactor.ONE);
    public static final BlendFunction OVERLAY = new BlendFunction(BlendFactor.SRC_ALPHA, BlendFactor.ONE, BlendFactor.ONE, BlendFactor.ZERO);
    public static final BlendFunction TRANSLUCENT = new BlendFunction(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA, BlendFactor.ONE, BlendFactor.ONE_MINUS_SRC_ALPHA);
    public static final BlendFunction TRANSLUCENT_PREMULTIPLIED_ALPHA = new BlendFunction(BlendFactor.ONE, BlendFactor.ONE_MINUS_SRC_ALPHA, BlendFactor.ONE, BlendFactor.ONE_MINUS_SRC_ALPHA);
    public static final BlendFunction ADDITIVE = new BlendFunction(BlendFactor.ONE, BlendFactor.ONE);
    public static final BlendFunction ENTITY_OUTLINE_BLIT = new BlendFunction(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA, BlendFactor.ZERO, BlendFactor.ONE);
    public static final BlendFunction INVERT = new BlendFunction(BlendFactor.ONE_MINUS_DST_COLOR, BlendFactor.ONE_MINUS_SRC_COLOR, BlendFactor.ONE, BlendFactor.ZERO);

    public BlendFunction(BlendFactor srcColor, BlendFactor dstColor) {
        this(srcColor, dstColor, srcColor, dstColor);
    }
}
