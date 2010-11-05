package net.objectlab.kit.collections;

public class ImmutableExpiringCollectionBuilder {
    private long expiryTimeoutMilliseconds;
    private boolean loadOnExpiry = true;
    private boolean loadOnFirstAccess = true;

    public ImmutableExpiringCollectionBuilder expiryTimeoutMilliseconds(final long expiryTimeourMilliseconds) {
        this.expiryTimeoutMilliseconds = expiryTimeourMilliseconds;
        return this;
    }

    public ImmutableExpiringCollectionBuilder loadOnExpiry(final boolean loadOnExpiry) {
        this.loadOnExpiry = loadOnExpiry;
        return this;
    }

    public ImmutableExpiringCollectionBuilder loadOnFirstAccess(final boolean loadOnFirstAccess) {
        this.loadOnFirstAccess = loadOnFirstAccess;
        return this;
    }

    final long getExpiryTimeoutMilliseconds() {
        return expiryTimeoutMilliseconds;
    }

    final boolean isLoadOnExpiry() {
        return loadOnExpiry;
    }

    final boolean isLoadOnFirstAccess() {
        return loadOnFirstAccess;
    }

}
