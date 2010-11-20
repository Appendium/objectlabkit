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
public class ImmutableExpiringCollectionBuilder {
    private long expiryTimeoutMilliseconds = -1;
    private boolean reloadOnExpiry = true;
    private boolean reloadWhenExpired = true;
    private boolean loadOnFirstAccess = true;
    private String id;

    public ImmutableExpiringCollectionBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public ImmutableExpiringCollectionBuilder expiryTimeout(final PeriodBuilder expiryTimeout) {
        assert expiryTimeout != null;
        if (expiryTimeout != null) {
            this.expiryTimeoutMilliseconds = expiryTimeout.calculateMilliseconds();
        }
        return this;
    }

    public ImmutableExpiringCollectionBuilder expiryTimeoutMilliseconds(final long expiryTimeoutMilliseconds) {
        this.expiryTimeoutMilliseconds = expiryTimeoutMilliseconds;
        return this;
    }

    public ImmutableExpiringCollectionBuilder reloadWhenExpired(final boolean reloadWhenExpired) {
        this.reloadWhenExpired = reloadWhenExpired;
        return this;
    }

    public ImmutableExpiringCollectionBuilder reloadOnExpiry(final boolean loadOnExpiry) {
        this.reloadOnExpiry = loadOnExpiry;
        return this;
    }

    public ImmutableExpiringCollectionBuilder loadOnFirstAccess(final boolean loadOnFirstAccess) {
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
}
