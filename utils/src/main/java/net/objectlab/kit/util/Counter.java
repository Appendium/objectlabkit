package net.objectlab.kit.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simple counter of any type with total and percentage.  It is thread-safe.
 * @author xhensevalb
 *
 * @param <T> will be used as the key.
 */
public class Counter<T> {
    private Map<T, AtomicInteger> map = new HashMap<>();
    private AtomicInteger total = new AtomicInteger();

    public Counter<T> add(T t) {
        map.computeIfAbsent(t, k -> new AtomicInteger()).incrementAndGet();
        total.incrementAndGet();
        return this;
    }

    public Counter<T> add(T t, int increment) {
        map.computeIfAbsent(t, k -> new AtomicInteger()).addAndGet(increment);
        total.addAndGet(increment);
        return this;
    }

    public int getCount(T t) {
        final AtomicInteger ai = map.get(t);
        return ai != null ? ai.get() : 0;
    }

    public int getTotal() {
        return total.get();
    }

    public BigDecimal getPercentage(T t) {
        int to = getTotal();
        return to != 0 ? BigDecimalUtil.divide(8, BigDecimal.valueOf(getCount(t)), BigDecimal.valueOf(getTotal()), BigDecimal.ROUND_HALF_UP)
                : BigDecimal.ZERO;
    }
}
