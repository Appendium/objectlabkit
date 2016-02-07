/**
 *
 */
package net.objectlab.kit.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * @author Benoit
 *
 */
public class FrequencyBucketDistributionTest {
    @Test
    public void testNullBucket() {
        FrequencyBucketDistribution bucket = new FrequencyBucketDistribution(null);
        assertThat(bucket.getDistribution()).isEmpty();
    }

    @Test
    public void testEmptyBucket() {
        FrequencyBucketDistribution bucket = new FrequencyBucketDistribution(Collections.emptyList());
        assertThat(bucket.getDistribution()).hasSize(1);
        assertThat(bucket.getDistribution().get(0).isUpperLimit()).isTrue();
    }

    @Test
    public void testOneBucketNoPoint() {
        List<BigDecimal> buckets = new ArrayList<>();
        buckets.add(BigDecimal.ONE);

        FrequencyBucketDistribution bucket = new FrequencyBucketDistribution(buckets);
        assertThat(bucket.getDistribution()).hasSize(2);
        assertThat(bucket.getDistribution().get(0).getBucket()).isEqualByComparingTo("1");
    }

    @Test
    public void testOneBucket() {
        List<BigDecimal> buckets = new ArrayList<>();
        buckets.add(BigDecimal.ONE);

        FrequencyBucketDistribution bucket = new FrequencyBucketDistribution(buckets);

        bucket.addPoint(BigDecimal.ONE);
        bucket.addPoint(BigDecimal.ONE);

        assertThat(bucket.getDistribution()).hasSize(2);
        assertThat(bucket.getDistribution().get(0).getBucket()).isEqualByComparingTo("1");
        assertThat(bucket.getDistribution().get(0).getCount()).isEqualTo(2);
        assertThat(bucket.getDistribution().get(1).getBucket()).isNull();
        assertThat(bucket.getDistribution().get(1).getCount()).isEqualTo(0);
    }

    @Test
    public void testMultiBucket() {
        List<BigDecimal> buckets = new ArrayList<>();
        buckets.add(BigDecimal.ONE);
        buckets.add(BigDecimal.TEN);
        buckets.add(BigDecimal.ZERO);

        FrequencyBucketDistribution bucket = new FrequencyBucketDistribution(buckets);

        bucket.addPoint(BigDecimal.ONE);
        bucket.addPoint(BigDecimal.ONE);
        bucket.addPoint(BigDecimalUtil.bd("-1"));
        bucket.addPoint(BigDecimalUtil.bd("11"));
        bucket.addPoint(BigDecimalUtil.bd("101"));
        bucket.addPoint(BigDecimalUtil.bd("11"));

        assertThat(bucket.getDistribution()).hasSize(4);
        assertThat(bucket.getDistribution().get(0).getBucket()).isEqualByComparingTo("0");
        assertThat(bucket.getDistribution().get(0).getCount()).isEqualTo(1);
        assertThat(bucket.getDistribution().get(1).getBucket()).isEqualByComparingTo("1");
        assertThat(bucket.getDistribution().get(1).getCount()).isEqualTo(2);
        assertThat(bucket.getDistribution().get(2).getBucket()).isEqualByComparingTo("10");
        assertThat(bucket.getDistribution().get(2).getCount()).isEqualTo(0);
        assertThat(bucket.getDistribution().get(3).getBucket()).isNull();
        assertThat(bucket.getDistribution().get(3).getCount()).isEqualTo(3);
    }
}
