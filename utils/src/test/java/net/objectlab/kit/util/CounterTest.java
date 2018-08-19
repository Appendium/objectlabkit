package net.objectlab.kit.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

public class CounterTest {

    @Test
    public void testEmpty() {
        Counter<String> c = new Counter<>();
        assertThat(c.getTotal()).isEqualTo(0);
        assertThat(c.getCount("ABC")).isEqualTo(0);
        assertThat(c.getPercentage("ABC")).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    public void testOne() {
        Counter<String> c = new Counter<>();
        c.add("ABC");
        assertThat(c.getTotal()).isEqualTo(1);
        assertThat(c.getCount("ABC")).isEqualTo(1);
        assertThat(c.getPercentage("ABC")).isEqualByComparingTo(BigDecimal.ONE);
    }

    @Test
    public void testTwoSame() {
        Counter<String> c = new Counter<>();
        c.add("ABC").add("ABC");
        assertThat(c.getTotal()).isEqualTo(2);
        assertThat(c.getCount("ABC")).isEqualTo(2);
        assertThat(c.getPercentage("ABC")).isEqualByComparingTo(BigDecimal.ONE);
    }

    @Test
    public void testTwoDiff() {
        Counter<String> c = new Counter<>();
        c.add("ABC").add("XYZ");
        assertThat(c.getTotal()).isEqualTo(2);
        assertThat(c.getCount("ABC")).isEqualTo(1);
        assertThat(c.getCount("XYZ")).isEqualTo(1);
        assertThat(c.getPercentage("ABC")).isEqualByComparingTo(new BigDecimal("0.5"));
        assertThat(c.getPercentage("XYZ")).isEqualByComparingTo(new BigDecimal("0.5"));
    }

    @Test
    public void testTwoDiffAdd() {
        Counter<String> c = new Counter<>();
        c.add("ABC", 2).add("ABC", 1).add("XYZ", 1);
        assertThat(c.getTotal()).isEqualTo(4);
        assertThat(c.getCount("ABC")).isEqualTo(3);
        assertThat(c.getCount("XYZ")).isEqualTo(1);
        assertThat(c.getPercentage("ABC")).isEqualByComparingTo(new BigDecimal("0.75"));
        assertThat(c.getPercentage("XYZ")).isEqualByComparingTo(new BigDecimal("0.25"));
    }
}
