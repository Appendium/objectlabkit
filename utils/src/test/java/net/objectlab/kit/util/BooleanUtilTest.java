package net.objectlab.kit.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class BooleanUtilTest {

    @Test
    public void testIsTrueOrNull() {
        assertTrue("null", BooleanUtil.isTrueOrNull(null));
        assertTrue("Boolean.TRUE", BooleanUtil.isTrueOrNull(Boolean.TRUE));
        assertTrue("true", BooleanUtil.isTrueOrNull(true));
        assertFalse("Boolean.FALSE", BooleanUtil.isTrueOrNull(Boolean.FALSE));
        assertFalse("false", BooleanUtil.isTrueOrNull(false));
    }

    @Test
    public void testIsFalseOrNull() {
        assertTrue("null", BooleanUtil.isFalseOrNull(null));
        assertFalse("Boolean.TRUE", BooleanUtil.isFalseOrNull(Boolean.TRUE));
        assertFalse("true", BooleanUtil.isFalseOrNull(true));
        assertTrue("Boolean.FALSE", BooleanUtil.isFalseOrNull(Boolean.FALSE));
        assertTrue("false", BooleanUtil.isFalseOrNull(false));
    }

    @Test
    public void testIsTrueBoolean() {
        assertFalse("null", BooleanUtil.isTrue((Boolean)null));
        assertTrue("Boolean.TRUE", BooleanUtil.isTrue(Boolean.TRUE));
        assertFalse("Boolean.FALSE", BooleanUtil.isTrue(Boolean.FALSE));
    }

    @Test
    public void testIsTrueString() {
        assertFalse("null", BooleanUtil.isTrue((String)null));
        assertFalse("empty", BooleanUtil.isTrue(""));
        assertFalse("sjs", BooleanUtil.isTrue("sjs"));
        assertFalse("space", BooleanUtil.isTrue(" "));
        assertTrue("y", BooleanUtil.isTrue("y"));
        assertTrue("yEs", BooleanUtil.isTrue("yEs"));
        assertTrue("Y", BooleanUtil.isTrue("Y"));
        assertTrue("YES", BooleanUtil.isTrue("YES"));
        assertTrue("YeS", BooleanUtil.isTrue("YeS"));
        assertTrue("t", BooleanUtil.isTrue("t"));
        assertFalse("tru", BooleanUtil.isTrue("tru"));
        assertTrue("true", BooleanUtil.isTrue("true"));
        assertTrue("True", BooleanUtil.isTrue("True"));
        assertTrue("t ", BooleanUtil.isTrue("t "));
    }

    @Test
    public void testIsFalse() {
        assertFalse("null", BooleanUtil.isFalse((Boolean)null));
        assertFalse("Boolean.TRUE", BooleanUtil.isFalse(Boolean.TRUE));
        assertTrue("Boolean.FALSE", BooleanUtil.isFalse(Boolean.FALSE));
    }
}
