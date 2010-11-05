package net.objectlab.kit.collections;

public class ImmutableExpiringHashMapBuilder<K, V> extends ImmutableExpiringCollectionBuilder {
    private final MapLoader<K, V> loader;

    public ImmutableExpiringHashMapBuilder(final MapLoader<K, V> loader) {
        super();
        this.loader = loader;
    }

    final MapLoader<K, V> getLoader() {
        return loader;
    }
}
