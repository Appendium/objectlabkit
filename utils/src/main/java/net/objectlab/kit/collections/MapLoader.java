/**
 * 
 */
package net.objectlab.kit.collections;

/**
 * Whenever the map needs to be reloaded, it will call the loader.
 * @author Benoit Xhenseval
 */
public interface MapLoader<K, V> {
    void load(MapBuilder<K, V> builder);
}
