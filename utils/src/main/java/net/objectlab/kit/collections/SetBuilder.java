/**
 * 
 */
package net.objectlab.kit.collections;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xhensevalb
 *
 */
public class SetBuilder<T> {
    private final Set<T> set = new HashSet<T>();

    public void add(final T t) {
        set.add(t);
    }

    public Set<T> build() {
        return new HashSet<T>(set);
    }
}
