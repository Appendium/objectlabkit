/**
 * 
 */
package net.objectlab.kit.collections;

/**
 * @author xhensevalb
 *
 */
public interface ImmutableExpiringCollection {
    void setExpiryTimeoutMilliseconds(long milliseconds);

    void setLoadOnFirstAccess(boolean loadOnFirstAccess);

    void setLoadOnExpiry(boolean loadOnExpiry);

    void start();
}
