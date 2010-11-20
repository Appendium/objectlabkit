/**
 * 
 */
package net.objectlab.kit.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xhensevalb
 *
 */
public class DefaultSetBuilder<T> implements SetBuilder<T> {
    private final Set<T> set = new HashSet<T>();
    private final String id;

    public DefaultSetBuilder(final String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void add(final T t) {
        set.add(t);
    }

    public void addAll(final Collection<T> t) {
        set.addAll(t);
    }

    Set<T> build() {
        return new HashSet<T>(set);
    }
}
