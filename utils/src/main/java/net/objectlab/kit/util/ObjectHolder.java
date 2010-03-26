package net.objectlab.kit.util;

/**
 * @author Benoit Xhenseval
 *
 */
public class ObjectHolder<T> {
    private T value;

    public ObjectHolder() {
    }

    public ObjectHolder(final T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(final T value) {
        this.value = value;
    }
}
