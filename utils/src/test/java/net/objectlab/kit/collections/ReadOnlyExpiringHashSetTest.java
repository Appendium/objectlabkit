/**
 * 
 */
package net.objectlab.kit.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author xhensevalb
 *
 */
public class ReadOnlyExpiringHashSetTest implements SetLoader<String>, TimeProvider {

    private int reloadCount;
    private long time;

    @Before
    public void reset() {
        reloadCount = 0;
        time = System.currentTimeMillis();
    }

    @Test
    public void basicConstructorNoReload() {
        final ReadOnlyExpiringHashSetBuilder<String> builder = new ReadOnlyExpiringHashSetBuilder<String>(this);
        builder.expiryTimeoutMilliseconds(1000);
        builder.loadOnFirstAccess(true);
        builder.reloadOnExpiry(false);
        builder.reloadWhenExpired(false);
        builder.timeProvider(this);
        builder.id("Greetings");

        final ReadOnlyExpiringSet<String> ims = new ReadOnlyExpiringHashSet<String>(builder);
        assertEquals("Should not call load until called", 0, reloadCount);

        assertFalse(ims.isEmpty());
        assertEquals(1, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.contains("Hi"));
        assertTrue("Correct key", ims.contains("Hello"));

        time += 100; // simulate 100 ms 

        // second call
        assertFalse(ims.isEmpty());
        assertEquals(1, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.contains("Hi"));
        assertTrue("Correct key", ims.contains("Hello"));

        time += 901;

        // should be gone
        assertTrue(ims.isEmpty());
        assertEquals(0, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.contains("Hi"));
        assertFalse("Correct key", ims.contains("Hello"));
    }

    @Test
    public void basicConstructorWithReloadWhenCalled() {
        final ReadOnlyExpiringHashSetBuilder<String> builder = new ReadOnlyExpiringHashSetBuilder<String>(this);
        builder.expiryTimeoutMilliseconds(1000);
        builder.loadOnFirstAccess(true);
        builder.reloadOnExpiry(false);
        builder.reloadWhenExpired(true);
        builder.timeProvider(this);
        builder.id("Greetings");

        final ReadOnlyExpiringSet<String> ims = new ReadOnlyExpiringHashSet<String>(builder);

        assertEquals("Should not call load until called", 0, reloadCount);

        assertFalse(ims.isEmpty());
        assertEquals(1, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.contains("Hi"));
        assertTrue("Correct key", ims.contains("Hello"));
        time += 100; // simulate 100 ms 

        // second call
        assertFalse(ims.isEmpty());
        assertEquals(1, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.contains("Hi"));
        assertTrue("Correct key", ims.contains("Hello"));
        time += 901; // simulate 901 ms 

        assertEquals("Should NOT have reloaded until called!", 1, reloadCount);

        // should be gone
        assertFalse(ims.isEmpty());
        assertEquals("Now it should have called the reload", 2, reloadCount);
        assertEquals(1, ims.size());
        assertFalse("diff key", ims.contains("Hi"));
        assertTrue("Correct key", ims.contains("Hello"));
    }

    @Test
    public void basicConstructorWithImmediateLoadAndWhenExpired() {
        final ReadOnlyExpiringHashSetBuilder<String> builder = new ReadOnlyExpiringHashSetBuilder<String>(this);
        builder.expiryTimeoutMilliseconds(1000);
        builder.loadOnFirstAccess(false);
        builder.reloadOnExpiry(false);
        builder.reloadWhenExpired(true);
        builder.timeProvider(this);
        builder.id("Greetings");

        final ReadOnlyExpiringSet<String> ims = new ReadOnlyExpiringHashSet<String>(builder);

        assertEquals("Should call load immediately", 1, reloadCount);

        assertFalse(ims.isEmpty());
        assertEquals(1, reloadCount);
        assertEquals(1, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.contains("Hi"));
        assertTrue("Correct key", ims.contains("Hello"));
        time += 100; // simulate 100 ms 

        // second call
        assertFalse(ims.isEmpty());
        assertEquals(1, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.contains("Hi"));
        assertTrue("Correct key", ims.contains("Hello"));
        time += 901; // simulate 901 ms 

        assertEquals("Should NOT have reloaded until called!", 1, reloadCount);

        // should be gone
        assertFalse(ims.isEmpty());
        assertEquals("Now it should have called the reload", 2, reloadCount);
        assertEquals(1, ims.size());
        assertFalse("diff key", ims.contains("Hi"));
        assertTrue("Correct key", ims.contains("Hello"));
    }

    @Test
    public void basicConstructorWithReloadOnExpiry() {
        final ReadOnlyExpiringHashSetBuilder<String> builder = new ReadOnlyExpiringHashSetBuilder<String>(this);
        builder.expiryTimeoutMilliseconds(1000);
        builder.loadOnFirstAccess(false);
        builder.reloadOnExpiry(true);
        builder.reloadWhenExpired(false); // but does not matter
        builder.timeProvider(this);
        builder.id("Greetings");

        final ReadOnlyExpiringSet<String> ims = new ReadOnlyExpiringHashSet<String>(builder);

        assertEquals("Should have called load immediately", 1, reloadCount);

        assertFalse(ims.isEmpty());
        assertEquals(1, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.contains("Hi"));
        assertTrue("Correct key", ims.contains("Hello"));

        time += 101;
        
        // second call
        assertFalse(ims.isEmpty());
        assertEquals(1, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.contains("Hi"));
        assertTrue("Correct key", ims.contains("Hello"));
        time += 901; // simulate 901 ms 
        
        try {
            Thread.sleep(1000); // ensure that the timer can catch up.
        } catch (InterruptedException e) {
        }

        assertEquals("Should have reloaded until called!", 2, reloadCount);

        // should be gone
        assertFalse(ims.isEmpty());
        assertEquals("Now it should have called the reload", 2, reloadCount);
        assertEquals(1, ims.size());
        assertFalse("diff key", ims.contains("Hi"));
        assertTrue("Correct key", ims.contains("Hello"));
    }

    public void load(final SetBuilder<String> builder) {
        assertEquals("Greetings", builder.getId());
        builder.add("Hello");
        reloadCount++;
    }

    public long getCurrentTimeMillis() {
        return time;
    }
}
