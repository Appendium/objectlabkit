package net.objectlab.kit.util;

import java.math.BigDecimal;

/**
 * @author Benoit
 *
 */
public final class Average {
    private Sum sum = new Sum();
    private int count = 0;

    public Average() {
    }

    public Average(final BigDecimal start) {
        sum = new Sum(start);
    }

    public Average(final int scale) {
        BigDecimal bd = new BigDecimal(0);
        sum = new Sum(bd.setScale(scale));
    }

    public void add(BigDecimal val) {
        sum.add(val);
        count++;
    }

    public BigDecimal getTotal() {
        return sum.getTotal();
    }

    public int getDataPoints() {
        return count;
    }

    public BigDecimal getAverage() {
        return BigDecimalUtil.divide(getTotal(), new BigDecimal(count), BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public String toString() {
        return StringUtil.concatWithSpaces("Total:", getTotal(), "Points", getDataPoints(), "Avg:", getAverage());
    }
}
