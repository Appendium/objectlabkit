package net.objectlab.kit.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

public class ObjectUtilTest {

    @Test
    public void testEqualsValueValueValueIntegerInteger() {
        assertThat(ObjectUtil.equalsValue((Integer) null, null)).isTrue();
        assertThat(ObjectUtil.equalsValue(1, null)).isFalse();
        assertThat(ObjectUtil.equalsValue((Integer) null, 1)).isFalse();
        assertThat(ObjectUtil.equalsValue(1, 2)).isFalse();
        assertThat(ObjectUtil.equalsValue(10, 10)).isTrue();
    }

    @Test
    public void testEqualsValueValueValueBigDecimalBigDecimal() {
        assertThat(ObjectUtil.equalsValue((BigDecimal) null, null)).isTrue();
        assertThat(ObjectUtil.equalsValue(BigDecimal.ONE, null)).isFalse();
        assertThat(ObjectUtil.equalsValue((BigDecimal) null, BigDecimal.TEN)).isFalse();
        assertThat(ObjectUtil.equalsValue(BigDecimal.ONE, BigDecimal.TEN)).isFalse();
        assertThat(ObjectUtil.equalsValue(BigDecimal.TEN, new BigDecimal("10.00000"))).isTrue();
    }

    @Test
    public void testEqualsValueValueValueObjectObject() {
        assertThat(ObjectUtil.equalsValue((Object) null, null)).isTrue();
        assertThat(ObjectUtil.equalsValue("ABC", null)).isFalse();
        assertThat(ObjectUtil.equalsValue((Object) null, "ABC")).isFalse();
        assertThat(ObjectUtil.equalsValue("ABC", "")).isFalse();
        assertThat(ObjectUtil.equalsValue("ABC", "ABC")).isTrue();
    }

    @Test
    public void testEqualsValueValueValueAny() {
        assertThat(ObjectUtil.equalsAny((Object) null)).isFalse();
        assertThat(ObjectUtil.equalsAny("ABC")).isFalse();
        assertThat(ObjectUtil.equalsAny((Object) null, null)).isTrue();
        assertThat(ObjectUtil.equalsAny((Object) null, "ABC")).isFalse();
        assertThat(ObjectUtil.equalsAny((Object) null, "ABS", null)).isTrue();
        assertThat(ObjectUtil.equalsAny("ABC", "ABS", null)).isFalse();
        assertThat(ObjectUtil.equalsAny("ABC", "ABS", "ABC", null)).isTrue();
    }

    @Test
    public void testEqualsValueValueValueAll() {
        assertThat(ObjectUtil.equalsAll((Object) null)).isTrue();
        assertThat(ObjectUtil.equalsAll((Object) null, null)).isTrue();
        assertThat(ObjectUtil.equalsAll((Object) null, null, null)).isTrue();
        assertThat(ObjectUtil.equalsAll((Object) null, null, null, 1)).isFalse();
        assertThat(ObjectUtil.equalsAll(1, null, null)).isFalse();
        assertThat(ObjectUtil.equalsAll(1, null, 1)).isFalse();
        assertThat(ObjectUtil.equalsAll(1, 1, 1)).isTrue();
        assertThat(ObjectUtil.equalsAll(1, 1, 2)).isFalse();
    }

    @Test
    public void testNotEqualsAny() {
        assertThat(ObjectUtil.notEqualsAny((Object) null)).isTrue();
        assertThat(ObjectUtil.notEqualsAny("ABC")).isTrue();
        assertThat(ObjectUtil.notEqualsAny((Object) null, null)).isFalse();
        assertThat(ObjectUtil.notEqualsAny((Object) null, "ABC")).isTrue();
        assertThat(ObjectUtil.notEqualsAny((Object) null, "ABS", null)).isFalse();
        assertThat(ObjectUtil.notEqualsAny("ABC", "ABS", null)).isTrue();
        assertThat(ObjectUtil.notEqualsAny("ABC", "ABS", "ABC", null)).isFalse();
    }

    @Test
    public void testAnyNull() {
        assertThat(ObjectUtil.anyNull()).isTrue();
        assertThat(ObjectUtil.anyNull(null)).isTrue();
        assertThat(ObjectUtil.anyNull(null, null)).isTrue();
        assertThat(ObjectUtil.anyNull(1, 1, 2)).isFalse();
        assertThat(ObjectUtil.anyNull(1, 1, null)).isTrue();
        assertThat(ObjectUtil.anyNull(null, 1, 2)).isTrue();
    }

    @Test
    public void testAllNull() {
        assertThat(ObjectUtil.allNull()).isTrue();
        assertThat(ObjectUtil.allNull(null)).isTrue();
        assertThat(ObjectUtil.allNull(null, null, null)).isTrue();
        assertThat(ObjectUtil.allNull(null, 1, null)).isFalse();
        assertThat(ObjectUtil.allNull(1, 1, null)).isFalse();
        assertThat(ObjectUtil.allNull(1, 1, 1)).isFalse();
    }

    @Test
    public void testAtLeastOneNotNull() {
        assertThat(ObjectUtil.atLeastOneNotNull()).isFalse();
        assertThat(ObjectUtil.atLeastOneNotNull(null)).isFalse();
        assertThat(ObjectUtil.atLeastOneNotNull(null, null, 1)).isTrue();
        assertThat(ObjectUtil.atLeastOneNotNull(1, 2, 3, 4, null)).isTrue();
        assertThat(ObjectUtil.atLeastOneNotNull(1, 2, 3, 4)).isTrue();
    }

    @Test
    public void testNoneNull() {
        assertThat(ObjectUtil.noneNull()).isFalse();
        assertThat(ObjectUtil.noneNull(null)).isFalse();
        assertThat(ObjectUtil.noneNull(1)).isTrue();
        assertThat(ObjectUtil.noneNull(1, 2)).isTrue();
        assertThat(ObjectUtil.noneNull(1, 2, null)).isFalse();
        assertThat(ObjectUtil.noneNull(null, 1, 2)).isFalse();
    }

}
