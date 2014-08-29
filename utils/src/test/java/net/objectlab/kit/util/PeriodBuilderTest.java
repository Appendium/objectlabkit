package net.objectlab.kit.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PeriodBuilderTest {
    @Test
    public void testIt() {
        assertThat(new PeriodBuilder().calculateMilliseconds()).isEqualTo(0L);
        assertThat(new PeriodBuilder().milliseconds(1).calculateMilliseconds()).isEqualTo(1L);
        assertThat(new PeriodBuilder().seconds(5).calculateMilliseconds()).isEqualTo(5_000L);
        assertThat(new PeriodBuilder().minutes(2).calculateMilliseconds()).isEqualTo(120_000L);
        assertThat(new PeriodBuilder().hours(1).calculateMilliseconds()).isEqualTo(3_600_000L);
        assertThat(new PeriodBuilder().days(3).calculateMilliseconds()).isEqualTo(259_200_000L);
        assertThat(new PeriodBuilder().weeks(1).calculateMilliseconds()).isEqualTo(604_800_000L);
        assertThat(new PeriodBuilder().weeks(1).days(3).hours(1).minutes(2).seconds(5).milliseconds(1).calculateMilliseconds()).isEqualTo(
                867_725_001L);
    }
}
