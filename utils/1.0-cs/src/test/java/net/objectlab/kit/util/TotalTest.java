package net.objectlab.kit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class TotalTest {
    @Test
    public void testSum() {
        final Total sum = new Total();
        assertEquals("scale", 0, sum.getTotal().scale());
        BigDecimalAssert.assertSameValue("ctor", BigDecimal.ZERO, sum.getTotal());
    }

    @Test
    public void testSumInt() {
        final Total sum = new Total(BigDecimal.ZERO, 2);
        assertEquals("scale", 2, sum.getTotal().scale());
        BigDecimalAssert.assertSameValue("ctor", BigDecimal.ZERO, sum.getTotal());
    }

    //    @Test
    public void testSumBigDecimal() {
        final Total sum = new Total(BigDecimal.TEN);
        assertEquals("scale", 2, sum.getTotal().scale());
        BigDecimalAssert.assertSameValue("ctor", BigDecimal.TEN, sum.getTotal());

        final Total sum2 = new Total(BigDecimal.TEN.setScale(4));
        assertEquals("scale", 4, sum2.getTotal().scale());
        BigDecimalAssert.assertSameValue("ctor", BigDecimal.TEN, sum2.getTotal());
    }

    @Test
    public void testSumBigDecimalInt() {
        final Total sum = new Total(BigDecimal.TEN, 4);
        assertEquals("scale", 4, sum.getTotal().scale());
        BigDecimalAssert.assertSameValue("ctor", BigDecimal.TEN, sum.getTotal());
    }

    @Test
    public void testgetTotal() {
        final Total sum = new Total(BigDecimal.TEN, 4);
        BigDecimalAssert.assertSameValue("ctor", BigDecimal.TEN, sum.getTotal());
    }

    @Test
    public void testAdd() {
        final Total sum = new Total();
        sum.add((BigDecimal[]) null);
        BigDecimalAssert.assertSameValue("add null", BigDecimal.ZERO, sum.getTotal());
        sum.add(BigDecimal.ZERO);
        BigDecimalAssert.assertSameValue("add 0", BigDecimal.ZERO, sum.getTotal());
        sum.add(BigDecimal.ONE).add(BigDecimal.ONE);
        BigDecimalAssert.assertSameValue("add 1 + 1", new BigDecimal("2"), sum.getTotal());
        sum.add(BigDecimal.ONE.negate());
        BigDecimalAssert.assertSameValue("add -1", new BigDecimal("1"), sum.getTotal());
        sum.add(BigDecimal.TEN.negate());
        BigDecimalAssert.assertSameValue("add -10", new BigDecimal("-9"), sum.getTotal());
        sum.add(BigDecimal.ONE, BigDecimal.ONE, null, BigDecimal.TEN);
        BigDecimalAssert.assertSameValue("add 1 1 null 10", new BigDecimal("3"), sum.getTotal());
    }

    @Test
    public void testSubtract() {
        final Total sum = new Total();
        sum.subtract((BigDecimal[]) null);
        BigDecimalAssert.assertSameValue("subtract null", BigDecimal.ZERO, sum.getTotal());
        sum.subtract(BigDecimal.ZERO);
        BigDecimalAssert.assertSameValue("subtract 0", BigDecimal.ZERO, sum.getTotal());
        sum.subtract(BigDecimal.ONE).subtract(BigDecimal.ONE);
        BigDecimalAssert.assertSameValue("subtract 1 + 1", new BigDecimal("-2"), sum.getTotal());
        sum.subtract(BigDecimal.ONE.negate());
        BigDecimalAssert.assertSameValue("subtract -1", new BigDecimal("-1"), sum.getTotal());
        sum.subtract(BigDecimal.TEN.negate());
        BigDecimalAssert.assertSameValue("subtract -10", new BigDecimal("9"), sum.getTotal());
        sum.subtract(BigDecimal.ONE, BigDecimal.ONE, null, BigDecimal.TEN);
        BigDecimalAssert.assertSameValue("subtract 1 1 null 10", new BigDecimal("-3"), sum.getTotal());
    }

    @Test
    public void testToString() {
        final Total sum = new Total(BigDecimal.TEN, 4);
        assertEquals("4 dp", "10.0000", sum.toString());
        final Total sum2 = new Total();
        assertEquals("0 dp", "0", sum2.toString());
    }

    @Test
    public void testIsZero() {
        final Total sum = new Total(BigDecimal.TEN, 4);
        assertFalse("10", sum.isZero());

        final Total sum2 = new Total();
        assertTrue("0", sum2.isZero());

        final Total sum3 = new Total(BigDecimal.ZERO, 0);
        assertTrue("0", sum3.isZero());

        final Total sum4 = new Total(new BigDecimal("0.0"));
        assertTrue("0", sum4.isZero());

        final Total sum5 = new Total(new BigDecimal("0.0"), 0);
        assertTrue("0", sum5.isZero());
    }
}