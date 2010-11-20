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

    void setReloadOnExpiry(boolean loadOnExpiry);

    void setReloadWhenExpired(boolean loadWhenExpired);

    void start();
}
