/**
 *
 */
package net.objectlab.kit.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Benoit Xhenseval
 *
 */
public class DefaultSetBuilder<T> implements SetBuilder<T> {
    private final Set<T> set = new HashSet<>();
    private final String id;

    public DefaultSetBuilder(final String id) {
        super();
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void add(final T t) {
        set.add(t);
    }

    @Override
    public void addAll(final Collection<T> t) {
        set.addAll(t);
    }

    Set<T> build() {
        return new HashSet<>(set);
    }
}
