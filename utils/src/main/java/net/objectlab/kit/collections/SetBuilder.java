/**
 *
 */
package net.objectlab.kit.collections;

import java.util.Collection;

/**
 * @author Benoit Xhenseval
 *
 */
public interface SetBuilder<T> {
    String getId();

    void add(final T t);

    void addAll(final Collection<T> t);
}
