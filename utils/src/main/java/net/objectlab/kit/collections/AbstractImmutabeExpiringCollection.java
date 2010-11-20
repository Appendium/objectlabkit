/**
 * 
 */
package net.objectlab.kit.collections;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author xhensevalb
 */
public abstract class AbstractImmutabeExpiringCollection {
    private long expiryTimeoutMilliseconds;
    private boolean reloadOnExpiry = true;
    private boolean reloadWhenExpired = true;
    private boolean loadOnFirstAccess = true;
    private long lastLoadingTime;
    private String id;
    private Timer timer;

    protected void setId(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    protected void setLastLoadingTime(final long lastLoadingTime) {
        this.lastLoadingTime = lastLoadingTime;
    }

    public void setExpiryTimeoutMilliseconds(final long milliseconds) {
        this.expiryTimeoutMilliseconds = milliseconds;
    }

    public void setReloadOnExpiry(final boolean reloadOnExpiry) {
        this.reloadOnExpiry = reloadOnExpiry;
    }

    public void setLoadOnFirstAccess(final boolean loadOnFirstAccess) {
        this.loadOnFirstAccess = loadOnFirstAccess;
    }

    public void setReloadWhenExpired(final boolean reloadWhenExpired) {
        this.reloadWhenExpired = reloadWhenExpired;
    }

    protected boolean hasExpired() {
        return System.currentTimeMillis() - lastLoadingTime > expiryTimeoutMilliseconds;
    }

    public void start() {
        if (reloadOnExpiry) {
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
            if (reloadWhenExpired || loadOnFirstAccess && lastLoadingTime == 0) {
                load();
            } else {
                doClear();
            }
        }
    }

    private void load() {
        doLoad();
        lastLoadingTime = System.currentTimeMillis();
    }

    protected abstract void doLoad();

    protected abstract void doClear();

    protected long getExpiryTimeoutMilliseconds() {
        return expiryTimeoutMilliseconds;
    }

    protected boolean isReloadOnExpiry() {
        return reloadOnExpiry;
    }

    protected boolean isLoadOnFirstAccess() {
        return loadOnFirstAccess;
    }

    protected long getLastLoadingTime() {
        return lastLoadingTime;
    }

}
