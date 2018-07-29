/**
 *
 */
package net.objectlab.kit.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * @author Benoit
 *
 */
public class BigDecimalUtilTest {

    @Test
    public void testIfNotNull() {
        assertThat(BigDecimalUtil.ifNotNull(null, t -> fail("Should not be called"))).isFalse();
        assertThat(BigDecimalUtil.ifNotNull(BigDecimal.TEN, t -> assertThat(t).isEqualByComparingTo(BigDecimal.TEN))).isTrue();
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#divide(java.math.BigDecimal, java.math.BigDecimal, int, int)}.
     */
    @Test
    public void testDivideBigDecimalBigDecimalIntInt() {
        assertEquals("3 null", null, BigDecimalUtil.divide(null, null, BigDecimal.ROUND_HALF_UP));
        assertEquals("2a null", null, BigDecimalUtil.divide(BigDecimal.ONE, null, BigDecimal.ROUND_HALF_UP));
        assertEquals("2c null", null, BigDecimalUtil.divide(null, BigDecimal.ONE, BigDecimal.ROUND_HALF_UP));
        assertEquals("2d null", null, BigDecimalUtil.divide(null, BigDecimal.ONE, BigDecimal.ROUND_HALF_UP));
        assertEquals("ONE 2", BigDecimal.ONE, BigDecimalUtil.divide(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ROUND_HALF_UP));
        assertEquals("ONE 3", BigDecimal.ONE, BigDecimalUtil.divide(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#setScale(java.math.BigDecimal, int)}.
     */
    @Test
    public void testSetScaleBigDecimalInt() {
        assertEquals("null", null, BigDecimalUtil.setScale(null, 3));
        assertEquals("1.000", new BigDecimal("1.000"), BigDecimalUtil.setScale(BigDecimal.ONE, 3));
        assertEquals("1.000 same", new BigDecimal("1.000"), BigDecimalUtil.setScale(new BigDecimal("1.000"), 3));
        assertEquals("1.00 to 3dp", new BigDecimal("1.000"), BigDecimalUtil.setScale(new BigDecimal("1.00"), 3));
        assertEquals("1.000 to -1dp", new BigDecimal("0E+1"), BigDecimalUtil.setScale(new BigDecimal("1.000"), -1));
        assertEquals("1.236 to 2", new BigDecimal("1.24"), BigDecimalUtil.setScale(new BigDecimal("1.236"), 2));
        assertEquals("1.235 to 2", new BigDecimal("1.24"), BigDecimalUtil.setScale(new BigDecimal("1.235"), 2));
        assertEquals("1.234 to 2", new BigDecimal("1.23"), BigDecimalUtil.setScale(new BigDecimal("1.23"), 2));
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#inverse(java.math.BigDecimal, int)}.
     */
    @Test
    public void testInverseBigDecimalInt() {
        assertThat(BigDecimalUtil.inverse(null, 0)).isNull();
        assertThat(BigDecimalUtil.inverse(BigDecimal.ONE, 0)).isEqualByComparingTo(BigDecimal.ONE);
        assertThat(BigDecimalUtil.inverse(BigDecimal.TEN, 0)).isEqualByComparingTo(new BigDecimal("0"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.TEN, 2)).isEqualByComparingTo(new BigDecimal("0.1"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.valueOf(3), 4)).isEqualByComparingTo(new BigDecimal("0.3333"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.valueOf(3), 5)).isEqualByComparingTo(new BigDecimal("0.33333"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.valueOf(16), 5)).isEqualByComparingTo(new BigDecimal("0.06250"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.valueOf(16), 3)).isEqualByComparingTo(new BigDecimal("0.0630"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.valueOf(16), 1)).isEqualByComparingTo(new BigDecimal("0.1"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.ZERO, 10)).isNull();
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#inverse(java.math.BigDecimal, int, int)}.
     */
    @Test
    public void testInverseBigDecimalIntInt() {
        assertThat(BigDecimalUtil.inverse(null, 0, 0)).isNull();
        assertThat(BigDecimalUtil.inverse(BigDecimal.ONE, 0, BigDecimal.ROUND_DOWN)).isEqualByComparingTo(BigDecimal.ONE);
        assertThat(BigDecimalUtil.inverse(BigDecimal.TEN, 0, BigDecimal.ROUND_DOWN)).isEqualByComparingTo(new BigDecimal("0"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.TEN, 2, BigDecimal.ROUND_DOWN)).isEqualByComparingTo(new BigDecimal("0.1"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.valueOf(3), 4, BigDecimal.ROUND_DOWN)).isEqualByComparingTo(new BigDecimal("0.3333"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.valueOf(3), 5, BigDecimal.ROUND_DOWN)).isEqualByComparingTo(new BigDecimal("0.33333"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.valueOf(16), 5, BigDecimal.ROUND_DOWN)).isEqualByComparingTo(new BigDecimal("0.06250"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.valueOf(16), 3, BigDecimal.ROUND_DOWN)).isEqualByComparingTo(new BigDecimal("0.0620"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.valueOf(16), 1, BigDecimal.ROUND_DOWN)).isEqualByComparingTo(new BigDecimal("0"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.ZERO, 10, BigDecimal.ROUND_DOWN)).isNull();
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#inverse(java.math.BigDecimal)}.
     */
    @Test
    public void testInverseBigDecimal() {
        assertThat(BigDecimalUtil.inverse(null)).isNull();
        assertThat(BigDecimalUtil.inverse(BigDecimal.ONE)).isEqualByComparingTo(BigDecimal.ONE);
        assertThat(BigDecimalUtil.inverse(BigDecimal.TEN)).isEqualByComparingTo(new BigDecimal("0.1"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.valueOf(3))).isEqualByComparingTo(new BigDecimal("0.33333333333333333333"));
        assertThat(BigDecimalUtil.inverse(BigDecimal.ZERO)).isNull();
    }

    @Test
    public void testBd() {
        assertThat(BigDecimalUtil.bd(null)).isNull();
        assertThat(BigDecimalUtil.bd("")).isNull();
        assertThat(BigDecimalUtil.bd("0")).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(BigDecimalUtil.bd("10.12345678")).isEqualByComparingTo(new BigDecimal("10.12345678"));
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isNotZero(java.math.BigDecimal)}.
     */
    @Test
    public void testIsNotZero() {
        assertThat(BigDecimalUtil.isNotZero(null)).isFalse();
        assertThat(BigDecimalUtil.isNotZero(BigDecimal.ZERO)).isFalse();
        assertThat(BigDecimalUtil.isNotZero(BigDecimal.TEN)).isTrue();
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isZero(java.math.BigDecimal)}.
     */
    @Test
    public void testIsZero() {
        assertThat(BigDecimalUtil.isZero(null)).isFalse();
        assertThat(BigDecimalUtil.isZero(BigDecimal.ZERO)).isTrue();
        assertThat(BigDecimalUtil.isZero(BigDecimal.TEN)).isFalse();
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isNegative(java.math.BigDecimal)}.
     */
    @Test
    public void testIsNegative() {
        assertThat(BigDecimalUtil.isNegative(null)).isFalse();
        assertThat(BigDecimalUtil.isNegative(BigDecimal.ZERO)).isFalse();
        assertThat(BigDecimalUtil.isNegative(BigDecimal.TEN)).isFalse();
        assertThat(BigDecimalUtil.isNegative(new BigDecimal("-0.0001"))).isTrue();
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isStrictlyPositive(java.math.BigDecimal)}.
     */
    @Test
    public void testIsStrictlyPositive() {
        assertThat(BigDecimalUtil.isStrictlyPositive(null)).isFalse();
        assertThat(BigDecimalUtil.isStrictlyPositive(BigDecimal.ZERO)).isFalse();
        assertThat(BigDecimalUtil.isStrictlyPositive(BigDecimal.TEN)).isTrue();
        assertThat(BigDecimalUtil.isStrictlyPositive(new BigDecimal("-0.0001"))).isFalse();
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isNullOrZero(java.math.BigDecimal)}.
     */
    @Test
    public void testIsNullOrZero() {
        assertThat(BigDecimalUtil.isNullOrZero(null)).isTrue();
        assertThat(BigDecimalUtil.isNullOrZero(BigDecimal.ZERO)).isTrue();
        assertThat(BigDecimalUtil.isNullOrZero(BigDecimal.TEN)).isFalse();
        assertThat(BigDecimalUtil.isNullOrZero(new BigDecimal("-0.0001"))).isFalse();
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isSameValue(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testIsSameValue() {
        assertThat(BigDecimalUtil.isSameValue(null, null)).isTrue();
        assertThat(BigDecimalUtil.isSameValue(null, BigDecimal.ZERO)).isFalse();
        assertThat(BigDecimalUtil.isSameValue(BigDecimal.ZERO, null)).isFalse();
        assertThat(BigDecimalUtil.isSameValue(BigDecimal.ZERO, new BigDecimal("0.00000"))).isTrue();
        assertThat(BigDecimalUtil.isSameValue(BigDecimal.ZERO, new BigDecimal("0.000001"))).isFalse();
        assertThat(BigDecimalUtil.isSameValue(new BigDecimal("-11.234"), new BigDecimal("-11.234"))).isTrue();
        assertThat(BigDecimalUtil.isSameValue(new BigDecimal("-11.234"), new BigDecimal("11.234"))).isFalse();
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isSameAbsValue(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testIsSameAbsValue() {
        assertThat(BigDecimalUtil.isSameAbsValue(null, null)).isTrue();
        assertThat(BigDecimalUtil.isSameAbsValue(null, BigDecimal.ZERO)).isFalse();
        assertThat(BigDecimalUtil.isSameAbsValue(BigDecimal.ZERO, null)).isFalse();
        assertThat(BigDecimalUtil.isSameAbsValue(BigDecimal.ZERO, new BigDecimal("0.00000"))).isTrue();
        assertThat(BigDecimalUtil.isSameAbsValue(BigDecimal.ZERO, new BigDecimal("0.000001"))).isFalse();
        assertThat(BigDecimalUtil.isSameAbsValue(new BigDecimal("-11.234"), new BigDecimal("-11.234"))).isTrue();
        assertThat(BigDecimalUtil.isSameAbsValue(new BigDecimal("-11.234"), new BigDecimal("11.234"))).isTrue();
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isSameValueTreatNullAsZero(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testIsSameValueTreatNullAsZero() {
        assertThat(BigDecimalUtil.isSameValueTreatNullAsZero(null, null)).isTrue();
        assertThat(BigDecimalUtil.isSameValueTreatNullAsZero(null, BigDecimal.ZERO)).isTrue();
        assertThat(BigDecimalUtil.isSameValueTreatNullAsZero(null, BigDecimal.TEN)).isFalse();
        assertThat(BigDecimalUtil.isSameValueTreatNullAsZero(BigDecimal.ZERO, null)).isTrue();
        assertThat(BigDecimalUtil.isSameValueTreatNullAsZero(BigDecimal.TEN, null)).isFalse();
        assertThat(BigDecimalUtil.isSameValueTreatNullAsZero(BigDecimal.ZERO, BigDecimal.ZERO)).isTrue();
        assertThat(BigDecimalUtil.isSameValueTreatNullAsZero(BigDecimal.TEN, BigDecimal.ZERO)).isFalse();
        assertThat(BigDecimalUtil.isSameValueTreatNullAsZero(BigDecimal.ZERO, BigDecimal.TEN)).isFalse();
        assertThat(BigDecimalUtil.isSameValueTreatNullAsZero(BigDecimal.TEN, BigDecimal.TEN)).isTrue();
        assertThat(BigDecimalUtil.isSameValueTreatNullAsZero(BigDecimal.ZERO, new BigDecimal("0.00000"))).isTrue();
        assertThat(BigDecimalUtil.isSameValueTreatNullAsZero(BigDecimal.ZERO, new BigDecimal("0.000001"))).isFalse();
        assertThat(BigDecimalUtil.isSameValueTreatNullAsZero(new BigDecimal("-11.234"), new BigDecimal("-11.234"))).isTrue();
        assertThat(BigDecimalUtil.isSameValueTreatNullAsZero(new BigDecimal("-11.234"), new BigDecimal("11.234"))).isFalse();
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#add(java.math.BigDecimal, java.math.BigDecimal[])}.
     */
    @Test
    public void testAddBigDecimalBigDecimalArray() {
        assertEquals("null", BigDecimal.ZERO, BigDecimalUtil.add(null));
        assertEquals("null+null", BigDecimal.ZERO, BigDecimalUtil.add(null, (BigDecimal) null));
        assertEquals("null+null+0", BigDecimal.ZERO, BigDecimalUtil.add(null, null, BigDecimal.ZERO));
        assertEquals("null+1", BigDecimal.ONE, BigDecimalUtil.add(null, BigDecimal.ONE));
        assertEquals("1+null", BigDecimal.ONE, BigDecimalUtil.add(BigDecimal.ONE, (BigDecimal) null));
        assertEquals("1+null+0", BigDecimal.ONE, BigDecimalUtil.add(BigDecimal.ONE, null, BigDecimal.ZERO));
        assertEquals("1+null+1", new BigDecimal(2), BigDecimalUtil.add(BigDecimal.ONE, null, BigDecimal.ONE));
        assertEquals("1+0+1", new BigDecimal(2), BigDecimalUtil.add(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE));
        assertEquals("1+1+1", new BigDecimal(3), BigDecimalUtil.add(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE));
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#subtract(java.math.BigDecimal, java.math.BigDecimal[])}.
     */
    @Test
    public void testSubtractBigDecimalBigDecimalArray() {
        assertEquals("2 null", BigDecimal.ZERO, BigDecimalUtil.subtract(null, (BigDecimal[]) null));
        assertEquals("3 null", BigDecimal.ZERO, BigDecimalUtil.subtract(null, null, null));
        assertEquals("0 -null -null", BigDecimal.ZERO, BigDecimalUtil.subtract(BigDecimal.ZERO, null, null));
        assertEquals("null 0 -null", BigDecimal.ZERO, BigDecimalUtil.subtract(null, BigDecimal.ZERO, null));
        assertEquals("1 -null", BigDecimal.ONE, BigDecimalUtil.subtract(BigDecimal.ONE, (BigDecimal) null));
        assertEquals("1 -null -0", BigDecimal.ONE, BigDecimalUtil.subtract(BigDecimal.ONE, null, BigDecimal.ZERO));
        assertEquals("1 -0 -null", BigDecimal.ONE, BigDecimalUtil.subtract(BigDecimal.ONE, BigDecimal.ZERO, null));
        assertEquals("1 -0 -1", BigDecimal.ZERO, BigDecimalUtil.subtract(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE));
        assertEquals("1 -null -1", BigDecimal.ZERO, BigDecimalUtil.subtract(BigDecimal.ONE, null, BigDecimal.ONE));
        assertEquals("null -null -1", BigDecimal.ONE.negate(), BigDecimalUtil.subtract(null, null, BigDecimal.ONE));
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#divide(java.math.BigDecimal, java.math.BigDecimal, int)}.
     */
    @Test
    public void testDivideBigDecimalBigDecimalInt() {
        assertThat(BigDecimalUtil.divide(null, null, BigDecimal.ROUND_HALF_UP)).isNull();
        assertThat(BigDecimalUtil.divide(BigDecimal.ONE, null, BigDecimal.ROUND_HALF_UP)).isNull();
        assertThat(BigDecimalUtil.divide(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ROUND_HALF_UP)).isEqualByComparingTo(BigDecimal.ONE);
        assertThat(BigDecimalUtil.divide(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ROUND_HALF_UP)).isNull();
        assertThat(BigDecimalUtil.divide(BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ROUND_HALF_UP)).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(BigDecimalUtil.divide(new BigDecimal("1.0"), BigDecimal.TEN, BigDecimal.ROUND_HALF_UP))
                .isEqualByComparingTo(new BigDecimal("0.1"));
        assertThat(BigDecimalUtil.divide(new BigDecimal("1.00000"), new BigDecimal("46"), BigDecimal.ROUND_HALF_UP))
                .isEqualByComparingTo(new BigDecimal("0.02174"));
        assertThat(BigDecimalUtil.divide(new BigDecimal("1.000"), new BigDecimal("46"), BigDecimal.ROUND_DOWN))
                .isEqualByComparingTo(new BigDecimal("0.021"));
        assertThat(BigDecimalUtil.divide(new BigDecimal("1.000"), new BigDecimal("46"), BigDecimal.ROUND_HALF_UP))
                .isEqualByComparingTo(new BigDecimal("0.022"));
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#divide(int, java.math.BigDecimal, java.math.BigDecimal, int)}.
     */
    @Test
    public void testDivideIntBigDecimalBigDecimalInt() {
        assertThat(BigDecimalUtil.divide(0, null, null, BigDecimal.ROUND_HALF_UP)).isNull();
        assertThat(BigDecimalUtil.divide(0, BigDecimal.ONE, null, BigDecimal.ROUND_HALF_UP)).isNull();
        assertThat(BigDecimalUtil.divide(0, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ROUND_HALF_UP)).isEqualByComparingTo(BigDecimal.ONE);
        assertThat(BigDecimalUtil.divide(0, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ROUND_HALF_UP)).isNull();
        assertThat(BigDecimalUtil.divide(0, BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ROUND_HALF_UP)).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(BigDecimalUtil.divide(0, new BigDecimal("1.0"), BigDecimal.TEN, BigDecimal.ROUND_HALF_UP))
                .isEqualByComparingTo(new BigDecimal("0"));
        assertThat(BigDecimalUtil.divide(5, new BigDecimal("1.0"), BigDecimal.TEN, BigDecimal.ROUND_HALF_UP))
                .isEqualByComparingTo(new BigDecimal("0.1"));
        assertThat(BigDecimalUtil.divide(8, new BigDecimal("1.00000"), new BigDecimal("46"), BigDecimal.ROUND_HALF_UP))
                .isEqualByComparingTo(new BigDecimal("0.02173913"));
        assertThat(BigDecimalUtil.divide(3, new BigDecimal("1.00000000"), new BigDecimal("46"), BigDecimal.ROUND_DOWN))
                .isEqualByComparingTo(new BigDecimal("0.021"));
        assertThat(BigDecimalUtil.divide(3, new BigDecimal("1"), new BigDecimal("46"), BigDecimal.ROUND_HALF_UP))
                .isEqualByComparingTo(new BigDecimal("0.022"));
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#calculateWeight(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testCalculateWeight() {
        assertThat(BigDecimalUtil.calculateWeight(null, null)).isNull();
        assertThat(BigDecimalUtil.calculateWeight(null, BigDecimal.TEN)).isNull();
        assertThat(BigDecimalUtil.calculateWeight(BigDecimal.TEN, BigDecimal.TEN)).isEqualByComparingTo(BigDecimal.ONE);
        assertThat(BigDecimalUtil.calculateWeight(new BigDecimal("2345"), new BigDecimal("123456789")))
                .isEqualByComparingTo(new BigDecimal("0.000018995"));
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#multiply(java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testMultiplyBigDecimalBigDecimal() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#multiply(java.math.BigDecimal, java.math.BigDecimal[])}.
     */

    public void testMultiplyBigDecimalBigDecimalArray() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#abs(java.math.BigDecimal)}.
     */

    public void testAbs() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#negate(java.math.BigDecimal)}.
     */

    public void testNegate() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#negateIfTrue(boolean, java.math.BigDecimal)}.
     */

    public void testNegateIfTrue() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isNotSameAbsValue(java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testIsNotSameAbsValue() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isNotSameValue(java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testIsNotSameValue() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#compareTo(java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testCompareTo() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#absCompareTo(java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testAbsCompareTo() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#absDiff(java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testAbsDiff() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#movePoint(java.math.BigDecimal, int)}.
     */

    public void testMovePoint() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#roundTo(java.math.BigDecimal, int, int)}.
     */

    public void testRoundTo() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#setScale(java.math.BigDecimal, java.lang.Integer)}.
     */

    public void testSetScaleBigDecimalInteger() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#setScale(java.math.BigDecimal, java.lang.Integer, int)}.
     */

    public void testSetScaleBigDecimalIntegerInt() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#signum(java.math.BigDecimal)}.
     */

    public void testSignum() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isSameSignum(java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testIsSameSignum() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#hasSignedFlippedAndNotZero(java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testHasSignedFlippedAndNotZero() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#hasSignedChanged(java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testHasSignedChanged() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isOutsideRange(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testIsOutsideRange() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isInsideInclusiveRange(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testIsInsideInclusiveRange() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#assignNonNull(java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testAssignNonNull() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#addWeightedConstituent(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testAddWeightedConstituent() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#allNullOrZero(java.math.BigDecimal[])}.
     */

    public void testAllNullOrZero() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#format(java.math.BigDecimal)}.
     */

    public void testFormat() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#percentFormat(java.math.BigDecimal)}.
     */

    public void testPercentFormat() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#movedInsideThresholdPercentage(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testMovedInsideThresholdPercentage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#movedStrictlyOutsideThresholdPercentage(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testMovedStrictlyOutsideThresholdPercentage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#roundUp(java.math.BigDecimal, int)}.
     */

    public void testRoundUp() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#roundDown(java.math.BigDecimal, int)}.
     */

    public void testRoundDown() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#roundUpForIncrement(java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testRoundUpForIncrement() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#roundDownForIncrement(java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testRoundDownForIncrement() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#ensureMin(java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testEnsureMin() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#forceNegative(java.math.BigDecimal)}.
     */

    public void testForceNegative() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#forceNegativeIfTrue(boolean, java.math.BigDecimal)}.
     */

    public void testForceNegativeIfTrue() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#min(java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testMin() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#max(java.math.BigDecimal[])}.
     */

    public void testMax() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#longForFraction(java.math.BigDecimal)}.
     */

    public void testLongForFraction() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isDiffMoreThanAbsThreshold(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)}.
     */

    public void testIsDiffMoreThanAbsThreshold() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#doubleValue(java.math.BigDecimal)}.
     */

    public void testDoubleValue() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isZeroOrLess(java.math.BigDecimal)}.
     */

    public void testIsZeroOrLess() {
        fail("Not yet implemented");
    }

}
