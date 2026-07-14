package java.util.concurrent;
import java.util.*;
public class CopyOnWriteArraySet<E> extends AbstractSet<E> implements Set<E> {
    private final CopyOnWriteArrayList<E> al = new CopyOnWriteArrayList<>();
    public CopyOnWriteArraySet() {}
    public CopyOnWriteArraySet(Collection<? extends E> c) { al.addAllAbsent(new ArrayList<>(c)); }
    @Override public int size() { return al.size(); }
    @Override public boolean isEmpty() { return al.isEmpty(); }
    @Override public boolean contains(Object o) { return al.contains(o); }
    @Override public Iterator<E> iterator() { return al.iterator(); }
    @Override public boolean add(E e) { return al.addIfAbsent(e); }
    @Override public boolean remove(Object o) { return al.remove(o); }
    @Override public void clear() { al.clear(); }
}
