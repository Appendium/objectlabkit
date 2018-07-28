package net.objectlab.kit.util;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Very basic distribution count per bucket. It is not meant to handle extreme large dataset and the calculation
 * is quite basic and inefficient at the moment.
 * 
 * Given a list of bucket upper limits, the distribution will count the number of occurrences.
 * If a value is &lt;= bucket (ordered) then the count is incremented.
 * If the value is &gt; than the upper limit then the count is incremented for an upper limit bucket.
 */
public class FrequencyBucketDistribution {
    private final List<Bucket> orderedBuckets;

    public static class Bucket {
        private final BigDecimal bucketValue;
        private long count;

        public Bucket(final BigDecimal bucket) {
            this.bucketValue = bucket;
        }

        public void incrementCount() {
            count++;
        }

        public BigDecimal getBucket() {
            return bucketValue;
        }

        public long getCount() {
            return count;
        }

        public boolean isUpperLimit() {
            return bucketValue == null;
        }
    }

    /**
     * Anything &lt;= smallest bucket goes into the bucket. If the value to classify is &gt; last bucket, then it is put in the last bucket.
     * @param buckets
     */
    public FrequencyBucketDistribution(final List<BigDecimal> buckets) {
        if (buckets != null) {
            orderedBuckets = buckets.stream().map(Bucket::new).sorted((o1, o2) -> o1.bucketValue.compareTo(o2.bucketValue))
                    .collect(Collectors.toList());
            orderedBuckets.add(new Bucket(null)); // upper limit
        } else {
            orderedBuckets = Collections.emptyList();
        }
    }

    public void addPoint(final BigDecimal point) {
        for (final Bucket b : orderedBuckets) {
            if (!b.isUpperLimit() && BigDecimalUtil.compareTo(point, b.getBucket()) <= 0) {
                b.incrementCount();
                return;
            }
        }
        orderedBuckets.get(orderedBuckets.size() - 1).incrementCount();
    }

    public List<Bucket> getDistribution() {
        return orderedBuckets;
    }
}
