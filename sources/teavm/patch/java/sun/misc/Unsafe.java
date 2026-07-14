package sun.misc;

public class Unsafe {
    private static final Unsafe UNSAFE = new Unsafe();
    public static Unsafe getUnsafe() { return UNSAFE; }

    public long getLongVolatile(Object obj, long offset) { return 0; }
    public int getIntVolatile(Object obj, long offset) { return 0; }
    public Object getObjectVolatile(Object obj, long offset) { return null; }
    public void putLongVolatile(Object obj, long offset, long value) {}
    public void putIntVolatile(Object obj, long offset, int value) {}
    public void putObjectVolatile(Object obj, long offset, Object value) {}
    public void putOrderedLong(Object obj, long offset, long value) {}
    public void putOrderedObject(Object obj, long offset, Object value) {}
    public void storeFence() {}
    public void loadFence() {}
    public void fullFence() {}
    public long objectFieldOffset(java.lang.reflect.Field f) { return 0; }
    public long staticFieldOffset(java.lang.reflect.Field f) { return 0; }
    public Object staticFieldBase(java.lang.reflect.Field f) { return null; }
    public void putLong(Object obj, long offset, long value) {}
    public long getLong(Object obj, long offset) { return 0; }
    public void putInt(Object obj, long offset, int value) {}
    public int getInt(Object obj, long offset) { return 0; }
    public void putObject(Object obj, long offset, Object value) {}
    public Object getObject(Object obj, long offset) { return null; }
    public int addressSize() { return 8; }
    public int arrayBaseOffset(Class<?> arrayClass) { return 0; }
    public int arrayIndexScale(Class<?> arrayClass) { return 1; }
    public boolean compareAndSwapInt(Object obj, long offset, int expect, int update) { return false; }
    public boolean compareAndSwapLong(Object obj, long offset, long expect, long update) { return false; }
    public boolean compareAndSwapObject(Object obj, long offset, Object expect, Object update) { return false; }
    public long allocateMemory(long bytes) { return 0; }
    public void freeMemory(long address) {}
    public Object allocateInstance(Class<?> cls) throws InstantiationException {
        try { return cls.getDeclaredConstructor().newInstance(); }
        catch (Exception e) { throw new InstantiationException(e.getMessage()); }
    }
}
