/**
 * 
 */
package net.objectlab.kit.collections;

import java.util.Map;

/**
 * Inspired by the Google Collection builder.
 * 
 * @author Benoit Xhenseval
 *
 */
public interface MapBuilder<K, V> {
    String getId();

    /**
     * Associates {@code key} with {@code value} in the built map. 
     */
    MapBuilder<K, V> put(K key, V value);

    /**
     * Associates all of the given map's keys and values in the built map.
     *
     * @throws NullPointerException if any key or value in {@code map} is null
     */
    MapBuilder<K, V> putAll(Map<? extends K, ? extends V> map);
}