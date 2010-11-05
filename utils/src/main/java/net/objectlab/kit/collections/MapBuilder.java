/**
 * 
 */
package net.objectlab.kit.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.objectlab.kit.util.Pair;

/**
 * Inspired by the Google Collection builder.
 * 
 * @author Benoit Xhenseval
 *
 */
public class MapBuilder<K, V> {
    final List<Pair<K, V>> entries = new ArrayList<Pair<K, V>>();

    public MapBuilder() {
    }

    /**
     * Associates {@code key} with {@code value} in the built map. 
     */
    public MapBuilder<K, V> put(final K key, final V value) {
        entries.add(new Pair<K, V>(key, value));
        return this;
    }

    /**
     * Associates all of the given map's keys and values in the built map.
     *
     * @throws NullPointerException if any key or value in {@code map} is null
     */
    public MapBuilder<K, V> putAll(final Map<? extends K, ? extends V> map) {
        for (final Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * Returns a newly-created immutable map.
     *
     * @throws IllegalArgumentException if duplicate keys were added
     */
    public Map<K, V> build() {
        return fromEntryList(entries);
    }

    private static <K, V> Map<K, V> fromEntryList(final List<Pair<K, V>> entries) {
        final int size = entries.size();
        switch (size) {
        case 0:
            return new HashMap<K, V>();
        default:
            final Map<K, V> m = new HashMap<K, V>();
            for (final Pair<K, V> entry : entries) {
                m.put(entry.getElement1(), entry.getElement2());
            }
            return m;
        }
    }
}
