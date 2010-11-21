/**
 * 
 */
package net.objectlab.kit.collections;

/**
 * @author xhensevalb
 *
 */
public interface ReadOnlyExpiringCollection {
    //    void setExpiryTimeoutMilliseconds(long milliseconds);

    //    void setLoadOnFirstAccess(boolean loadOnFirstAccess);

    //    void setReloadOnExpiry(boolean loadOnExpiry);

    //    void setReloadWhenExpired(boolean loadWhenExpired);

    //    void start();
    void stop();

    void reload();
}
