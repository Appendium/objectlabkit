package net.objectlab.kit.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Simple counter of any type with total and percentage.  It is thread-safe.
 * @author xhensevalb
 *
 * @param <T> will be used as the key.
 */
public class Counter<T> {
    private Map<T, AtomicInteger> map = new ConcurrentHashMap<>();
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

    @Getter
    @AllArgsConstructor
    public static class Stats<T> {
        private T key;
        private int count;
        private BigDecimal percentage;
    }

    public List<Stats> getOrderedDecStats() {
        return map.entrySet().stream().map(e -> new Stats(e.getKey(), e.getValue().get(), getPercentage(e.getKey())))
                .sorted((o1, o2) -> Integer.compare(o2.getCount(), o1.getCount())).collect(Collectors.toList());
    }
}
