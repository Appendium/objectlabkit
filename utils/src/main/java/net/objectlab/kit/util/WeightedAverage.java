package net.objectlab.kit.util;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Benoit
 *
 */
public class WeightedAverage implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Sum total = new Sum();
    private final Sum totalExpanded = new Sum();
    private int count = 0;

    public BigDecimal getTotal() {
        return total.getTotal();
    }

    public void add(final BigDecimal value, final BigDecimal weightAsAValue) {
        if (BigDecimalUtil.isNotZero(value)) {
            count++;
            total.add(weightAsAValue);
            totalExpanded.add(BigDecimalUtil.multiply(value, weightAsAValue));
        }
    }

    public BigDecimal getWeightedAverage() {
        return BigDecimalUtil.divide(totalExpanded.getTotal(), total.getTotal(), BigDecimal.ROUND_HALF_UP);
    }

    public int getCount() {
        return count;
    }
}
