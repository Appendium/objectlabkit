/**
 *
 */
package net.objectlab.kit.collections;

/**
 * @author Benoit Xhenseval
 *
 */
@FunctionalInterface
public interface TimeProvider {
    long getCurrentTimeMillis();
}
