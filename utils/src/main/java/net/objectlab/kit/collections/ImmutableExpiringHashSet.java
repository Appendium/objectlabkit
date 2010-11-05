/**
 * 
 */
package net.objectlab.kit.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author xhensevalb
 *
 */
public class ImmutableExpiringHashSet<T> extends AbstractImmutabeExpiringCollection implements ImmutableExpiringSet<T> {
    private final SetLoader<T> loader;
    private Set<T> delegate = new HashSet<T>();

    public ImmutableExpiringHashSet(final ImmutableExpiringHashSetBuilder<T> builder) {
        this.loader = builder.getLoader();
        setExpiryTimeoutMilliseconds(builder.getExpiryTimeoutMilliseconds());
        setLoadOnExpiry(builder.isLoadOnExpiry());
        setLoadOnFirstAccess(builder.isLoadOnFirstAccess());
        start();
    }

    @Override
    protected void doLoad() {
        final SetBuilder<T> builder = new SetBuilder<T>();
        loader.load(builder);
        delegate = builder.build();
    }

    public boolean add(final T e) {
        validateOnAccess();
        return delegate.add(e);
    }

    public boolean addAll(final Collection<? extends T> c) {
        throw new IllegalAccessError("Collection is immutable");
    }

    public void clear() {
        throw new IllegalAccessError("Collection is immutable");
    }

    public boolean contains(final Object o) {
        validateOnAccess();
        return delegate.contains(o);
    }

    public boolean containsAll(final Collection<?> c) {
        validateOnAccess();
        return delegate.containsAll(c);
    }

    public boolean isEmpty() {
        validateOnAccess();
        return delegate.isEmpty();
    }

    public Iterator<T> iterator() {
        validateOnAccess();
        return delegate.iterator();
    }

    public boolean remove(final Object o) {
        throw new IllegalAccessError("Collection is immutable");
    }

    public boolean removeAll(final Collection<?> c) {
        throw new IllegalAccessError("Collection is immutable");
    }

    public boolean retainAll(final Collection<?> c) {
        throw new IllegalAccessError("Collection is immutable");
    }

    public int size() {
        validateOnAccess();
        return delegate.size();
    }

    public Object[] toArray() {
        validateOnAccess();
        return delegate.toArray();
    }

    public <T> T[] toArray(final T[] a) {
        validateOnAccess();
        return delegate.toArray(a);
    }
}
