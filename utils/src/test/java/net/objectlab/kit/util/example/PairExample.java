package net.objectlab.kit.util.example;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import net.objectlab.kit.util.Pair;

public class PairExample {
    public static void main(final String[] args) {
        final Pair<String, BigDecimal> p1 = Pair.create("AMGN", new BigDecimal("114.13"));
        final Pair<String, BigDecimal> p2 = Pair.create("AAPL", new BigDecimal("614.88"));
        final Pair<String, BigDecimal> p3 = Pair.create("AAPL", new BigDecimal("614.88"));
        final Set<Pair<String, BigDecimal>> set = new HashSet<>();
        set.add(p1);
        set.add(p2);
        set.add(p3);
        System.out.println("Only 2 elements! (same pair twice):" + set.size());
    }
}
