package net.objectlab.kit.collections;

/**
 * The builder in charge of providing the parameters for the Set.
 * 
 * @author xhensevalb
 *
 * @param <T>
 */
public class ReadOnlyExpiringHashSetBuilder<T> extends ReadOnlyExpiringCollectionBuilder {
    private final SetLoader<T> loader;

    public ReadOnlyExpiringHashSetBuilder(final SetLoader<T> loader) {
        super();
        this.loader = loader;
    }

    final SetLoader<T> getLoader() {
        return loader;
    }
}
