package net.objectlab.kit.collections;

import net.objectlab.kit.util.PeriodBuilder;

/**
 * Builder for the immutable classes.
 * Default: 
 * - no time out
 * - reload on expiry: true
 * - load on first access only (ie not at construction time)
 * 
 * @author xhensevalb
 *
 */
public class ReadOnlyExpiringCollectionBuilder {
    private long expiryTimeoutMilliseconds = -1;
    private boolean reloadOnExpiry = true;
    private boolean reloadWhenExpired = true;
    private boolean loadOnFirstAccess = true;
    private String id;
    private TimeProvider timeProvider;

    public ReadOnlyExpiringCollectionBuilder timeProvider(final TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
        return this;
    }

    public ReadOnlyExpiringCollectionBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public ReadOnlyExpiringCollectionBuilder expiryTimeout(final PeriodBuilder expiryTimeout) {
        assert expiryTimeout != null;
        if (expiryTimeout != null) {
            this.expiryTimeoutMilliseconds = expiryTimeout.calculateMilliseconds();
        }
        return this;
    }

    public ReadOnlyExpiringCollectionBuilder expiryTimeoutMilliseconds(final long expiryTimeoutMilliseconds) {
        this.expiryTimeoutMilliseconds = expiryTimeoutMilliseconds;
        return this;
    }

    public ReadOnlyExpiringCollectionBuilder reloadWhenExpired(final boolean reloadWhenExpired) {
        this.reloadWhenExpired = reloadWhenExpired;
        return this;
    }

    public ReadOnlyExpiringCollectionBuilder reloadOnExpiry(final boolean loadOnExpiry) {
        this.reloadOnExpiry = loadOnExpiry;
        return this;
    }

    public ReadOnlyExpiringCollectionBuilder loadOnFirstAccess(final boolean loadOnFirstAccess) {
        this.loadOnFirstAccess = loadOnFirstAccess;
        return this;
    }

    final long getExpiryTimeoutMilliseconds() {
        return expiryTimeoutMilliseconds;
    }

    final boolean isReloadOnExpiry() {
        return reloadOnExpiry;
    }

    final boolean isReloadWhenExpired() {
        return reloadWhenExpired;
    }

    final boolean isLoadOnFirstAccess() {
        return loadOnFirstAccess;
    }

    final String getId() {
        return id;
    }

    final TimeProvider getTimeProvider() {
        return timeProvider;
    }
}
