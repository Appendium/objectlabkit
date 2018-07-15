package net.objectlab.kit.util;

import static net.objectlab.kit.util.IntegerUtil.assign;
import static net.objectlab.kit.util.IntegerUtil.isNotSameValue;
import static net.objectlab.kit.util.IntegerUtil.isNotZero;
import static net.objectlab.kit.util.IntegerUtil.isNotZeroOrNegative;
import static net.objectlab.kit.util.IntegerUtil.isNullOrZero;
import static net.objectlab.kit.util.IntegerUtil.isSameValue;
import static net.objectlab.kit.util.IntegerUtil.isZero;
import static net.objectlab.kit.util.IntegerUtil.safeAdd;
import static net.objectlab.kit.util.IntegerUtil.safeCompare;
import static net.objectlab.kit.util.IntegerUtil.safeSignum;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class IntegerUtilTest {

    @Test
    public void testIsNotZero() {
        assertThat(isNotZero(null)).isFalse();
        assertThat(isNotZero(0)).isFalse();
        assertThat(isNotZero(1)).isTrue();
    }

    @Test
    public void testIsZero() {
        assertThat(isZero(0)).isTrue();
        assertThat(isZero(null)).isFalse();
        assertThat(isZero(1)).isFalse();
    }

    @Test
    public void testIsNullOrZero() {
        assertThat(isNullOrZero(0)).isTrue();
        assertThat(isNullOrZero(null)).isTrue();
        assertThat(isNullOrZero(1)).isFalse();
    }

    @Test
    public void testIsSameValue() {
        assertThat(isSameValue(null, null)).isTrue();
        assertThat(isSameValue(null, 0)).isFalse();
        assertThat(isSameValue(0, 0)).isTrue();
        assertThat(isSameValue(1, 2)).isFalse();
        assertThat(isSameValue(1, null)).isFalse();
        assertThat(isSameValue(null, 10)).isFalse();
    }

    @Test
    public void testIsNotSameValue() {
        assertThat(isNotSameValue(null, null)).isFalse();
        assertThat(isNotSameValue(null, 0)).isTrue();
        assertThat(isNotSameValue(0, 0)).isFalse();
        assertThat(isNotSameValue(1, 2)).isTrue();
        assertThat(isNotSameValue(1, null)).isTrue();
        assertThat(isNotSameValue(null, 10)).isTrue();
    }

    @Test
    public void testSafeAdd() {
        assertThat(safeAdd(null, null)).isEqualTo(null);
        assertThat(safeAdd(0, null)).isEqualTo(0);
        assertThat(safeAdd(0, 1)).isEqualTo(1);
        assertThat(safeAdd(1, null)).isEqualTo(1);
        assertThat(safeAdd(1, 2)).isEqualTo(3);
        assertThat(safeAdd(null, 2)).isEqualTo(2);
    }

    @Test
    public void testSafeSignum() {
        assertThat(safeSignum(null)).isEqualTo(0);
        assertThat(safeSignum(0)).isEqualTo(0);
        assertThat(safeSignum(100)).isEqualTo(1);
        assertThat(safeSignum(-100)).isEqualTo(-1);
    }

    @Test
    public void testSafeCompare() {
        assertThat(safeCompare(null, null)).isEqualTo(0);
        assertThat(safeCompare(0, null)).isEqualTo(1);
        assertThat(safeCompare(null, 0)).isEqualTo(-1);
        assertThat(safeCompare(0, 0)).isEqualTo(0);
        assertThat(safeCompare(10, 10)).isEqualTo(0);
        assertThat(safeCompare(10, 11)).isEqualTo(-1);
        assertThat(safeCompare(12, 11)).isEqualTo(1);
    }

    @Test
    public void testAssign() {
        assertThat(assign(null, null)).isNull();
        assertThat(assign(null, 0)).isEqualTo(0);
        assertThat(assign(1, 0)).isEqualTo(1);
    }

    @Test
    public void testIsNotZeroOrNegative() {
        assertThat(isNotZeroOrNegative(null)).isFalse();
        assertThat(isNotZeroOrNegative(0)).isFalse();
        assertThat(isNotZeroOrNegative(1)).isTrue();
        assertThat(isNotZeroOrNegative(-11)).isFalse();
    }

}
