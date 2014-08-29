package net.objectlab.kit.fxcalc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CurrencyPairTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNull1() {
        new CurrencyPair("", "B");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull2() {
        new CurrencyPair(null, "B");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull3() {
        new CurrencyPair("A", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull4() {
        new CurrencyPair("A", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull5() {
        new CurrencyPair("", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull6() {
        new CurrencyPair(null, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull7() {
        new CurrencyPair("", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull8() {
        new CurrencyPair(null, null);
    }

    @Test
    public void testEquals() throws Exception {
        final CurrencyPair cp = new CurrencyPair("A", "B");
        assertThat(cp.getCcy1()).describedAs("ccy1").isEqualTo("A");
        assertThat(cp.getCcy2()).describedAs("ccy2").isEqualTo("B");

        final CurrencyPair cp2 = new CurrencyPair("A", "B");
        assertThat(cp.equals(cp2)).isTrue();

        final CurrencyPair cp3 = new CurrencyPair("X", "B");
        assertThat(cp.equals(cp3)).isFalse();
    }

    @Test
    public void testToString() throws Exception {
        final CurrencyPair cp = new CurrencyPair("A", "B");
        assertThat(cp.toString()).isEqualTo("A.B");
    }

    @Test
    public void testContainsCcy() throws Exception {
        final CurrencyPair cp = new CurrencyPair("A", "B");
        assertThat(cp.containsCcy("A")).isTrue();
        assertThat(cp.containsCcy("B")).isTrue();
        assertThat(cp.containsCcy("C")).isFalse();
    }

    @Test
    public void testFindCommonCcy() throws Exception {
        final CurrencyPair cp = new CurrencyPair("A", "B");
        final CurrencyPair cp2 = new CurrencyPair("X", "B");
        assertThat(cp.findCommonCcy(cp2).get()).isEqualTo("B");
        final CurrencyPair cp3 = new CurrencyPair("A", "X");
        assertThat(cp3.findCommonCcy(cp).get()).isEqualTo("A");
        final CurrencyPair cp4 = new CurrencyPair("Y", "X");
        assertThat(cp4.findCommonCcy(cp).isPresent()).isFalse();

        final CurrencyPair cpx = new CurrencyPair("GBP", "CHF");
        final CurrencyPair cpy = new CurrencyPair("EUR", "GBP");
        assertThat(cpx.findCommonCcy(cpy).get()).isEqualTo("GBP");

    }

    @Test
    public void testCreateInverse() throws Exception {
        final CurrencyPair cp = new CurrencyPair("A", "B").createInverse();
        assertThat(cp.getCcy1()).describedAs("ccy1").isEqualTo("B");
        assertThat(cp.getCcy2()).describedAs("ccy2").isEqualTo("A");
    }

}
