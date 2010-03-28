/**
 * 
 */
package net.objectlab.kit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * @author Benoit
 *
 */
public class BigDecimalUtilTest {

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
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#inverse(java.math.BigDecimal)}.
     */
    @Test
    public void testInverseBigDecimal() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isNotZero(java.math.BigDecimal)}.
     */
    @Test
    public void testIsNotZero() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isZero(java.math.BigDecimal)}.
     */
    @Test
    public void testIsZero() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isNegative(java.math.BigDecimal)}.
     */
    @Test
    public void testIsNegative() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isStrictlyPositive(java.math.BigDecimal)}.
     */
    @Test
    public void testIsStrictlyPositive() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isNullOrZero(java.math.BigDecimal)}.
     */
    @Test
    public void testIsNullOrZero() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isSameValue(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testIsSameValue() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isSameValueTreatNullAsZero(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testIsSameValueTreatNullAsZero() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#add(java.math.BigDecimal, java.math.BigDecimal[])}.
     */
    @Test
    public void testAddBigDecimalBigDecimalArray() {
        assertEquals("null", BigDecimal.ZERO, BigDecimalUtil.add(null));
        assertEquals("null+null", BigDecimal.ZERO, BigDecimalUtil.add(null, null));
        assertEquals("null+null+0", BigDecimal.ZERO, BigDecimalUtil.add(null, null, BigDecimal.ZERO));
        assertEquals("null+1", BigDecimal.ONE, BigDecimalUtil.add(null, BigDecimal.ONE));
        assertEquals("1+null", BigDecimal.ONE, BigDecimalUtil.add(BigDecimal.ONE, null));
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
        assertEquals("1 -null", BigDecimal.ONE, BigDecimalUtil.subtract(BigDecimal.ONE, null));
        assertEquals("1 -null -0", BigDecimal.ONE, BigDecimalUtil.subtract(BigDecimal.ONE, null, BigDecimal.ZERO));
        assertEquals("1 -0 -null", BigDecimal.ONE, BigDecimalUtil.subtract(BigDecimal.ONE, BigDecimal.ZERO, null));
        assertEquals("1 -0 -1", BigDecimal.ZERO, BigDecimalUtil.subtract(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE));
        assertEquals("1 -null -1", BigDecimal.ZERO, BigDecimalUtil.subtract(BigDecimal.ONE, null, BigDecimal.ONE));
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#divide(java.math.BigDecimal, java.math.BigDecimal, int)}.
     */
    @Test
    public void testDivideBigDecimalBigDecimalInt() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#calculateWeight(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testCalculateWeight() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#divide(int, java.math.BigDecimal, java.math.BigDecimal, int)}.
     */
    @Test
    public void testDivideIntBigDecimalBigDecimalInt() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#multiply(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testMultiplyBigDecimalBigDecimal() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#multiply(java.math.BigDecimal, java.math.BigDecimal[])}.
     */
    @Test
    public void testMultiplyBigDecimalBigDecimalArray() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#abs(java.math.BigDecimal)}.
     */
    @Test
    public void testAbs() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#negate(java.math.BigDecimal)}.
     */
    @Test
    public void testNegate() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#negateIfTrue(boolean, java.math.BigDecimal)}.
     */
    @Test
    public void testNegateIfTrue() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isNotSameAbsValue(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testIsNotSameAbsValue() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isNotSameValue(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testIsNotSameValue() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isSameAbsValue(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testIsSameAbsValue() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#compareTo(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testCompareTo() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#absCompareTo(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testAbsCompareTo() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#absDiff(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testAbsDiff() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#movePoint(java.math.BigDecimal, int)}.
     */
    @Test
    public void testMovePoint() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#roundTo(java.math.BigDecimal, int, int)}.
     */
    @Test
    public void testRoundTo() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#setScale(java.math.BigDecimal, java.lang.Integer)}.
     */
    @Test
    public void testSetScaleBigDecimalInteger() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#setScale(java.math.BigDecimal, java.lang.Integer, int)}.
     */
    @Test
    public void testSetScaleBigDecimalIntegerInt() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#signum(java.math.BigDecimal)}.
     */
    @Test
    public void testSignum() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isSameSignum(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testIsSameSignum() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#hasSignedFlippedAndNotZero(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testHasSignedFlippedAndNotZero() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#hasSignedChanged(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testHasSignedChanged() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isOutsideRange(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testIsOutsideRange() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isInsideInclusiveRange(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testIsInsideInclusiveRange() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#assignNonNull(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testAssignNonNull() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#addWeightedConstituent(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testAddWeightedConstituent() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#allNullOrZero(java.math.BigDecimal[])}.
     */
    @Test
    public void testAllNullOrZero() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#format(java.math.BigDecimal)}.
     */
    @Test
    public void testFormat() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#percentFormat(java.math.BigDecimal)}.
     */
    @Test
    public void testPercentFormat() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#movedInsideThresholdPercentage(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testMovedInsideThresholdPercentage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#movedStrictlyOutsideThresholdPercentage(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testMovedStrictlyOutsideThresholdPercentage() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#roundUp(java.math.BigDecimal, int)}.
     */
    @Test
    public void testRoundUp() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#roundDown(java.math.BigDecimal, int)}.
     */
    @Test
    public void testRoundDown() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#roundUpForIncrement(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testRoundUpForIncrement() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#roundDownForIncrement(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testRoundDownForIncrement() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#ensureMin(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testEnsureMin() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#forceNegative(java.math.BigDecimal)}.
     */
    @Test
    public void testForceNegative() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#forceNegativeIfTrue(boolean, java.math.BigDecimal)}.
     */
    @Test
    public void testForceNegativeIfTrue() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#min(java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testMin() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#max(java.math.BigDecimal[])}.
     */
    @Test
    public void testMax() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#longForFraction(java.math.BigDecimal)}.
     */
    @Test
    public void testLongForFraction() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isDiffMoreThanAbsThreshold(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)}.
     */
    @Test
    public void testIsDiffMoreThanAbsThreshold() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#doubleValue(java.math.BigDecimal)}.
     */
    @Test
    public void testDoubleValue() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.objectlab.kit.util.BigDecimalUtil#isZeroOrLess(java.math.BigDecimal)}.
     */
    @Test
    public void testIsZeroOrLess() {
        fail("Not yet implemented");
    }

}
