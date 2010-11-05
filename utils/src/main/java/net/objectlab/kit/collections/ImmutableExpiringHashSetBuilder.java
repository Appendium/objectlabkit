package net.objectlab.kit.collections;

public class ImmutableExpiringHashSetBuilder<T> extends ImmutableExpiringCollectionBuilder {
    private final SetLoader<T> loader;

    public ImmutableExpiringHashSetBuilder(final SetLoader<T> loader) {
        super();
        this.loader = loader;
    }

    final SetLoader<T> getLoader() {
        return loader;
    }
}
