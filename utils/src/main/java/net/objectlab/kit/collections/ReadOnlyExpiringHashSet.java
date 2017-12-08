/**
 *
 */
package net.objectlab.kit.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Benoit Xhenseval
 *
 */
public class ReadOnlyExpiringHashSet<T> extends AbstractReadOnlyExpiringCollection implements ReadOnlyExpiringSet<T> {
    private static final String COLLECTION_IS_IMMUTABLE = "Collection is immutable";
    private final SetLoader<T> loader;
    private Set<T> delegate = new HashSet<>();

    public ReadOnlyExpiringHashSet(final ReadOnlyExpiringHashSetBuilder<T> builder) {
        this.loader = builder.getLoader();
        setId(builder.getId());
        setExpiryTimeoutMilliseconds(builder.getExpiryTimeoutMilliseconds());
        setReloadOnExpiry(builder.isReloadOnExpiry());
        setLoadOnFirstAccess(builder.isLoadOnFirstAccess());
        setReloadWhenExpired(builder.isReloadWhenExpired());
        setTimeProvider(builder.getTimeProvider());
        start();
    }

    @Override
    protected void doLoad() {
        final DefaultSetBuilder<T> builder = new DefaultSetBuilder<>(getId());
        loader.load(builder);
        delegate = builder.build();
    }

    @Override
    public boolean add(final T e) {
        validateOnAccess();
        return delegate.add(e);
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        throw new UnsupportedOperationException(COLLECTION_IS_IMMUTABLE);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(COLLECTION_IS_IMMUTABLE);
    }

    @Override
    public boolean contains(final Object o) {
        validateOnAccess();
        return delegate.contains(o);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        validateOnAccess();
        return delegate.containsAll(c);
    }

    @Override
    public boolean isEmpty() {
        validateOnAccess();
        return delegate.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        validateOnAccess();
        return delegate.iterator();
    }

    @Override
    public boolean remove(final Object o) {
        throw new UnsupportedOperationException(COLLECTION_IS_IMMUTABLE);
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        throw new UnsupportedOperationException(COLLECTION_IS_IMMUTABLE);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        throw new UnsupportedOperationException(COLLECTION_IS_IMMUTABLE);
    }

    @Override
    public int size() {
        validateOnAccess();
        return delegate.size();
    }

    @Override
    public Object[] toArray() {
        validateOnAccess();
        return delegate.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        validateOnAccess();
        return delegate.toArray(a);
    }

    @Override
    protected void doClear() {
        delegate.clear();
    }

    @Override
    public void reload() {
        doLoad();
    }
}
