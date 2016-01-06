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
public class AverageTest {
    private Average average;

    @Before
    public void setUp() {
        average = new Average();
    }

    @Test
    public void testAverageNoPoint() {
        assertThat(average.getDataPoints()).isEqualTo(0);
        assertThat(average.getAverage()).isNull();
        assertThat(average.getMaximum().isPresent()).isFalse();
        assertThat(average.getMinimum().isPresent()).isFalse();
    }

    @Test
    public void testAverageOnePointZero() {
        average.add(BigDecimal.ZERO);
        assertThat(average.getDataPoints()).isEqualTo(1);
        assertThat(average.getAverage()).isEqualByComparingTo("0");
        assertThat(average.getTotal()).isEqualByComparingTo("0");
        assertThat(average.getMaximum().get()).isEqualByComparingTo("0");
        assertThat(average.getMinimum().get()).isEqualByComparingTo("0");
    }

    @Test
    public void testAverageOnePointTen() {
        average.add(BigDecimal.TEN);
        assertThat(average.getDataPoints()).isEqualTo(1);
        assertThat(average.getAverage()).isEqualByComparingTo("10");
        assertThat(average.getTotal()).isEqualByComparingTo("10");
        assertThat(average.getMaximum().get()).isEqualByComparingTo("10");
        assertThat(average.getMinimum().get()).isEqualByComparingTo("10");
    }

    @Test
    public void testAverageTwoPointsTen() {
        average.add(BigDecimal.ZERO);
        average.add(BigDecimal.TEN);
        assertThat(average.getDataPoints()).isEqualTo(2);
        assertThat(average.getAverage()).isEqualByComparingTo("5");
        assertThat(average.getTotal()).isEqualByComparingTo("10");
        assertThat(average.getMaximum().get()).isEqualByComparingTo("10");
        assertThat(average.getMinimum().get()).isEqualByComparingTo("0");
    }

    @Test
    public void testAverageTwoPointsWithNegative() {
        average.add(BigDecimalUtil.bd("-1"));
        average.add(BigDecimal.TEN);
        assertThat(average.getDataPoints()).isEqualTo(2);
        assertThat(average.getAverage()).isEqualByComparingTo("4.5");
        assertThat(average.getTotal()).isEqualByComparingTo("9");
        assertThat(average.getMaximum().get()).isEqualByComparingTo("10");
        assertThat(average.getMinimum().get()).isEqualByComparingTo("-1");
    }

    @Test
    public void testAverageFourPointMixed() {
        average.add(BigDecimalUtil.bd("-1"));
        average.add(BigDecimalUtil.bd("4"));
        average.add(BigDecimal.TEN);
        average.add(BigDecimal.TEN);
        assertThat(average.getDataPoints()).isEqualTo(4);
        assertThat(average.getAverage()).isEqualByComparingTo("5.75");
        assertThat(average.getTotal()).isEqualByComparingTo("23");
        assertThat(average.getMaximum().get()).isEqualByComparingTo("10");
        assertThat(average.getMinimum().get()).isEqualByComparingTo("-1");
    }

}
