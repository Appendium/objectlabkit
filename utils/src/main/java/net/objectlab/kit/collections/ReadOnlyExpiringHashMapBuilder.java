package net.objectlab.kit.collections;

public class ReadOnlyExpiringHashMapBuilder<K, V> extends ReadOnlyExpiringCollectionBuilder {
    private final MapLoader<K, V> loader;

    public ReadOnlyExpiringHashMapBuilder(final MapLoader<K, V> loader) {
        super();
        this.loader = loader;
    }

    final MapLoader<K, V> getLoader() {
        return loader;
    }
}
