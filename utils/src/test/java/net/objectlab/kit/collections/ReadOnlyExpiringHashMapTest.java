/**
 *
 */
package net.objectlab.kit.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Benoit Xhenseval
 *
 */
public class ReadOnlyExpiringHashMapTest implements MapLoader<String, Integer>, TimeProvider {

    private int reloadCount;
    private long time;

    @Before
    public void reset() {
        reloadCount = 0;
        time = System.currentTimeMillis();
    }

    @Test
    public void basicConstructorNoReload() {
        final ReadOnlyExpiringHashMapBuilder<String, Integer> builder = new ReadOnlyExpiringHashMapBuilder<>(this);
        builder.expiryTimeoutMilliseconds(1000);
        builder.loadOnFirstAccess(true);
        builder.reloadOnExpiry(false);
        builder.reloadWhenExpired(false);
        builder.timeProvider(this);
        builder.id("Greetings");

        final ReadOnlyExpiringMap<String, Integer> ims = new ReadOnlyExpiringHashMap<>(builder);

        assertEquals("Should not call load until called", 0, reloadCount);

        assertFalse(ims.isEmpty());
        assertEquals(2, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.containsKey("Hi"));
        assertTrue("Correct key", ims.containsKey("Hello"));
        assertNull("diff key", ims.get("Hi"));
        assertEquals(Integer.valueOf(2), ims.get("Yo"));

        time += 101; // simulate 101 ms

        // second call
        assertFalse(ims.isEmpty());
        assertEquals(2, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.containsKey("Hi"));
        assertTrue("Correct key", ims.containsKey("Hello"));
        assertNull("diff key", ims.get("Hi"));
        assertEquals(Integer.valueOf(2), ims.get("Yo"));

        time += 901; // simulate 901 ms

        // should be gone
        assertTrue(ims.isEmpty());
        assertEquals(0, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.containsKey("Hi"));
        assertFalse("Correct key", ims.containsKey("Hello"));
        assertNull("diff key", ims.get("Hi"));
        assertNull(ims.get("Yo"));
    }

    @Test
    public void basicConstructorWithReloadWhenCalled() {
        final ReadOnlyExpiringHashMapBuilder<String, Integer> builder = new ReadOnlyExpiringHashMapBuilder<>(this);
        builder.expiryTimeoutMilliseconds(1000);
        builder.loadOnFirstAccess(true);
        builder.reloadOnExpiry(false);
        builder.reloadWhenExpired(true);
        builder.timeProvider(this);
        builder.id("Greetings");

        final ReadOnlyExpiringMap<String, Integer> ims = new ReadOnlyExpiringHashMap<>(builder);

        assertEquals("Should not call load until called", 0, reloadCount);

        assertFalse(ims.isEmpty());
        assertEquals(2, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.containsKey("Hi"));
        assertTrue("Correct key", ims.containsKey("Hello"));
        assertNull("diff key", ims.get("Hi"));
        assertEquals(Integer.valueOf(2), ims.get("Yo"));

        time += 101; // simulate 101 ms

        // second call
        assertFalse(ims.isEmpty());
        assertEquals(2, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.containsKey("Hi"));
        assertTrue("Correct key", ims.containsKey("Hello"));
        assertNull("diff key", ims.get("Hi"));
        assertEquals(Integer.valueOf(2), ims.get("Yo"));

        time += 901; // simulate 901 ms

        assertEquals("2) Should not call load until called", 1, reloadCount);

        // should be gone
        assertFalse(ims.isEmpty());
        assertEquals(2, ims.size());
        assertEquals(2, reloadCount);
        assertFalse("diff key", ims.containsKey("Hi"));
        assertTrue("Correct key", ims.containsKey("Hello"));
        assertNull("diff key", ims.get("Hi"));
        assertEquals(Integer.valueOf(2), ims.get("Yo"));
    }

    @Test
    public void basicConstructorWithImmediateLoadAndWhenExpired() {
        final ReadOnlyExpiringHashMapBuilder<String, Integer> builder = new ReadOnlyExpiringHashMapBuilder<>(this);
        builder.expiryTimeoutMilliseconds(1000);
        builder.loadOnFirstAccess(false);
        builder.reloadOnExpiry(false);
        builder.reloadWhenExpired(true);
        builder.timeProvider(this);
        builder.id("Greetings");

        final ReadOnlyExpiringMap<String, Integer> ims = new ReadOnlyExpiringHashMap<>(builder);

        assertEquals("Should not call load until called", 1, reloadCount);

        assertFalse(ims.isEmpty());
        assertEquals(2, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.containsKey("Hi"));
        assertTrue("Correct key", ims.containsKey("Hello"));
        assertNull("diff key", ims.get("Hi"));
        assertEquals(Integer.valueOf(2), ims.get("Yo"));

        time += 101; // simulate 101 ms

        // second call
        assertFalse(ims.isEmpty());
        assertEquals(2, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.containsKey("Hi"));
        assertTrue("Correct key", ims.containsKey("Hello"));
        assertNull("diff key", ims.get("Hi"));
        assertEquals(Integer.valueOf(2), ims.get("Yo"));

        time += 901; // simulate 901 ms

        assertEquals("2) Should not call load until called", 1, reloadCount);

        // should be gone
        assertFalse(ims.isEmpty());
        assertEquals(2, ims.size());
        assertEquals(2, reloadCount);
        assertFalse("diff key", ims.containsKey("Hi"));
        assertTrue("Correct key", ims.containsKey("Hello"));
        assertNull("diff key", ims.get("Hi"));
        assertEquals(Integer.valueOf(2), ims.get("Yo"));
    }

    @Test
    public void basicConstructorWithReloadOnExpiry() {
        final ReadOnlyExpiringHashMapBuilder<String, Integer> builder = new ReadOnlyExpiringHashMapBuilder<>(this);
        builder.expiryTimeoutMilliseconds(1000);
        builder.loadOnFirstAccess(false);
        builder.reloadOnExpiry(true);
        builder.reloadWhenExpired(false);
        builder.timeProvider(this);
        builder.id("Greetings");

        final ReadOnlyExpiringMap<String, Integer> ims = new ReadOnlyExpiringHashMap<>(builder);

        assertEquals("Should not call load until called", 1, reloadCount);

        assertFalse(ims.isEmpty());
        assertEquals(2, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.containsKey("Hi"));
        assertTrue("Correct key", ims.containsKey("Hello"));
        assertNull("diff key", ims.get("Hi"));
        assertEquals(Integer.valueOf(2), ims.get("Yo"));

        time += 101; // simulate 101 ms

        // second call
        assertFalse(ims.isEmpty());
        assertEquals(2, ims.size());
        assertEquals(1, reloadCount);
        assertFalse("diff key", ims.containsKey("Hi"));
        assertTrue("Correct key", ims.containsKey("Hello"));
        assertNull("diff key", ims.get("Hi"));
        assertEquals(Integer.valueOf(2), ims.get("Yo"));

        time += 901; // simulate 901 ms

        try {
            Thread.sleep(2001);
        } catch (final InterruptedException e) {
        }

        assertEquals("2) Should not call load until called", 2, reloadCount);

        // should be gone
        assertFalse(ims.isEmpty());
        assertEquals(2, ims.size());
        assertEquals(2, reloadCount);
        assertFalse("diff key", ims.containsKey("Hi"));
        assertTrue("Correct key", ims.containsKey("Hello"));
        assertNull("diff key", ims.get("Hi"));
        assertEquals(Integer.valueOf(2), ims.get("Yo"));
    }

    @Override
    public void load(final MapBuilder<String, Integer> builder) {
        assertEquals("Greetings", builder.getId());
        builder.put("Hello", 1);
        builder.put("Yo", 2);
        reloadCount++;
    }

    @Override
    public long getCurrentTimeMillis() {
        return time;
    }

}
