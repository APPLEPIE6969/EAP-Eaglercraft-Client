package io.netty.util.internal;

public interface RefCnt {
    int refCnt();
    RefCnt retain();
    RefCnt retain(int increment);
    boolean release();
    boolean release(int decrement);
}
