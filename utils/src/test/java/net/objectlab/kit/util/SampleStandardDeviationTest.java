/**
 *
 */
package net.objectlab.kit.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Benoit
 *
 */
public class SampleStandardDeviationTest {
    private SampleStandardDeviation stdDev;

    @Before
    public void setUp() {
        stdDev = new SampleStandardDeviation();
    }

    @Test
    public void testAverageNoPoint() {
        assertThat(stdDev.getDataPoints()).isEqualTo(0);
        assertThat(stdDev.getAverage()).isNull();
        assertThat(stdDev.getStandardDeviation()).isNull();
        assertThat(stdDev.getTotal()).isEqualByComparingTo("0");
        assertThat(stdDev.getMinimum().isPresent()).isFalse();
        assertThat(stdDev.getMaximum().isPresent()).isFalse();
    }

    @Test
    public void testAverageOnePointZero() {
        stdDev.add(BigDecimal.ZERO);
        assertThat(stdDev.getDataPoints()).isEqualTo(1);
        assertThat(stdDev.getAverage()).isEqualByComparingTo("0");
        assertThat(stdDev.getTotal()).isEqualByComparingTo("0");
        assertThat(stdDev.getMaximum().get()).isEqualByComparingTo("0");
        assertThat(stdDev.getMinimum().get()).isEqualByComparingTo("0");
        assertThat(stdDev.getStandardDeviation()).isNull();
        ;
    }

    @Test
    public void testAverageOnePointOne() {
        stdDev.add(BigDecimal.ONE);
        assertThat(stdDev.getDataPoints()).isEqualTo(1);
        assertThat(stdDev.getAverage()).isEqualByComparingTo("1");
        assertThat(stdDev.getTotal()).isEqualByComparingTo("1");
        assertThat(stdDev.getMaximum().get()).isEqualByComparingTo("1");
        assertThat(stdDev.getMinimum().get()).isEqualByComparingTo("1");
        assertThat(stdDev.getStandardDeviation()).isNull();
    }

    @Test
    public void testAverageOnePointTen() {
        stdDev.add(BigDecimal.TEN);
        assertThat(stdDev.getDataPoints()).isEqualTo(1);
        assertThat(stdDev.getAverage()).isEqualByComparingTo("10");
        assertThat(stdDev.getTotal()).isEqualByComparingTo("10");
        assertThat(stdDev.getMaximum().get()).isEqualByComparingTo("10");
        assertThat(stdDev.getMinimum().get()).isEqualByComparingTo("10");
        assertThat(stdDev.getStandardDeviation()).isNull();
    }

    @Test
    public void testAverageTwoPointsTen() {
        stdDev.add(BigDecimal.ZERO);
        stdDev.add(BigDecimal.TEN);
        assertThat(stdDev.getDataPoints()).isEqualTo(2);
        assertThat(stdDev.getAverage()).isEqualByComparingTo("5");
        assertThat(stdDev.getTotal()).isEqualByComparingTo("10");
        assertThat(stdDev.getMaximum().get()).isEqualByComparingTo("10");
        assertThat(stdDev.getMinimum().get()).isEqualByComparingTo("0");
        assertThat(stdDev.getStandardDeviation()).isEqualByComparingTo("7.07106781");
    }

    @Test
    public void testAverageTwoPointsWithNegative() {
        stdDev.add(BigDecimalUtil.bd("-1"));
        stdDev.add(BigDecimal.TEN);
        assertThat(stdDev.getDataPoints()).isEqualTo(2);
        assertThat(stdDev.getAverage()).isEqualByComparingTo("4.5");
        assertThat(stdDev.getTotal()).isEqualByComparingTo("9");
        assertThat(stdDev.getMaximum().get()).isEqualByComparingTo("10");
        assertThat(stdDev.getMinimum().get()).isEqualByComparingTo("-1");
        assertThat(stdDev.getStandardDeviation()).isEqualByComparingTo("7.77817459");
    }

    @Test
    public void testAverageFourPointMixed() {
        stdDev.add(BigDecimalUtil.bd("-1"));
        stdDev.add(BigDecimalUtil.bd("4"));
        stdDev.add(BigDecimal.TEN);
        stdDev.add(BigDecimal.TEN);
        assertThat(stdDev.getDataPoints()).isEqualTo(4);
        assertThat(stdDev.getAverage()).isEqualByComparingTo("5.75");
        assertThat(stdDev.getTotal()).isEqualByComparingTo("23");
        assertThat(stdDev.getMaximum().get()).isEqualByComparingTo("10");
        assertThat(stdDev.getMinimum().get()).isEqualByComparingTo("-1");
        assertThat(stdDev.getStandardDeviation()).isEqualByComparingTo("5.31507291");
    }

}
