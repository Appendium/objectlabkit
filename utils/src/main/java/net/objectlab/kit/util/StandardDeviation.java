package net.objectlab.kit.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StandardDeviation {
    private final Average average = new Average();
    private List<BigDecimal> dataPoints;

    public Optional<BigDecimal> getMaximum() {
        return average.getMaximum();
    }

    public BigDecimal getTotal() {
        return average.getTotal();
    }

    public Optional<BigDecimal> getMinimum() {
        return average.getMinimum();
    }

    public void add(final BigDecimal... values) {
        if (values != null) {
            average.add(values);
            if (dataPoints == null) {
                dataPoints = new ArrayList<>(values.length);
            }
            for (final BigDecimal val : values) {
                dataPoints.add(val);
            }
        }
    }

    public BigDecimal getAverage() {
        return average.getAverage();
    }

    public int getDataPoints() {
        return average.getDataPoints();
    }

    public BigDecimal getStandardDeviation() {
        if (dataPoints == null) {
            return null;
        }
        final BigDecimal average = getAverage();
        final BigDecimal sumSquaredDifferences = dataPoints.stream().map(dp -> BigDecimalUtil.subtract(dp, average).pow(2))
                .reduce(BigDecimal.ZERO, (a, b) -> b != null ? a.add(b) : a);
        return BigDecimalUtil.setScale(
                BigDecimal.valueOf(Math.sqrt(BigDecimalUtil.divide(8, sumSquaredDifferences, BigDecimal.valueOf(getDataPoints()),
                        BigDecimal.ROUND_HALF_UP).doubleValue())), 8);
        //
        // return BigDecimalUtil.setScale(BigDecimalUtil.bigSqrt(BigDecimalUtil.divide(8, sumSquaredDifferences, BigDecimal.valueOf(getDataPoints()),
        // BigDecimal.ROUND_HALF_UP)), 8);
    }

}
