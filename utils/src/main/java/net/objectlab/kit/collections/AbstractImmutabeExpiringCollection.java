/**
 * 
 */
package net.objectlab.kit.collections;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author xhensevalb
 *
 */
public abstract class AbstractImmutabeExpiringCollection {
    private long expiryTimeoutMilliseconds;
    private boolean loadOnExpiry = true;
    private boolean loadOnFirstAccess = true;
    private long lastLoadingTime;
    private Timer timer;

    protected void setLastLoadingTime(final long lastLoadingTime) {
        this.lastLoadingTime = lastLoadingTime;
    }

    public void setExpiryTimeoutMilliseconds(final long milliseconds) {
        this.expiryTimeoutMilliseconds = milliseconds;
    }

    public void setLoadOnExpiry(final boolean loadOnExpiry) {
        this.loadOnExpiry = loadOnExpiry;
    }

    public void setLoadOnFirstAccess(final boolean loadOnFirstAccess) {
        this.loadOnFirstAccess = loadOnFirstAccess;
    }

    protected boolean hasExpired() {
        return System.currentTimeMillis() - lastLoadingTime > expiryTimeoutMilliseconds;
    }

    public void start() {
        if (loadOnExpiry) {
            // start timer
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    load();
                }
            }, 0, expiryTimeoutMilliseconds);
        }
        if (!loadOnFirstAccess) {
            load();
        }
    }

    protected void validateOnAccess() {
        if (hasExpired()) {
            load();
        }
    }

    private void load() {
        doLoad();
        lastLoadingTime = System.currentTimeMillis();
    }

    protected abstract void doLoad();

    protected long getExpiryTimeoutMilliseconds() {
        return expiryTimeoutMilliseconds;
    }

    protected boolean isLoadOnExpiry() {
        return loadOnExpiry;
    }

    protected boolean isLoadOnFirstAccess() {
        return loadOnFirstAccess;
    }

    protected long getLastLoadingTime() {
        return lastLoadingTime;
    }

}
