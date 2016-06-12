/**
 *
 */
package net.objectlab.kit.collections;

/**
 * Whenever the set needs to be reloaded, it will call the loader.
 *
 */
@FunctionalInterface
public interface SetLoader<T> {
    void load(SetBuilder<T> builder);
}
