package sun.misc;
public class Unsafe {
    private static final Unsafe UNSAFE = new Unsafe();
    public static Unsafe getUnsafe() { return UNSAFE; }
    public long getLongVolatile(Object obj, long offset) { return 0; }
    public void storeFence() {}
    public void loadFence() {}
    public void fullFence() {}
    public long objectFieldOffset(java.lang.reflect.Field f) { return 0; }
}
