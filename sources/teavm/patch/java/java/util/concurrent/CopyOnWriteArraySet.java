package java.util.concurrent;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class CopyOnWriteArraySet<E> extends AbstractSet<E> implements Set<E> {
    private final ArrayList<E> al = new ArrayList<>();

    public CopyOnWriteArraySet() {}
    public CopyOnWriteArraySet(Collection<? extends E> c) {
        for (E e : c) {
            if (!al.contains(e)) al.add(e);
        }
    }

    @Override public int size() { return al.size(); }
    @Override public boolean isEmpty() { return al.isEmpty(); }
    @Override public boolean contains(Object o) { return al.contains(o); }
    @Override public Iterator<E> iterator() {
        return new ArrayList<>(al).iterator();
    }
    @Override public boolean add(E e) {
        if (!al.contains(e)) { al.add(e); return true; }
        return false;
    }
    @Override public boolean remove(Object o) { return al.remove(o); }
    @Override public void clear() { al.clear(); }
}
