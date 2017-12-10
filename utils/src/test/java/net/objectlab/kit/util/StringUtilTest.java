package net.objectlab.kit.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

public class StringUtilTest {

    @Test
    public void testCompareTo() {
        assertThat(StringUtil.compareTo(null, null)).isEqualTo(0);
        assertThat(StringUtil.compareTo("A", null)).isEqualTo(1);
        assertThat(StringUtil.compareTo(null, "B")).isEqualTo(-1);
        assertThat(StringUtil.compareTo("A", "B")).isEqualTo(-1);
        assertThat(StringUtil.compareTo("XA", "B")).isEqualTo(22);
        assertThat(StringUtil.compareTo("XA", "XA")).isEqualTo(0);
    }

    @Ignore
    public void testRemoveTrailingChar() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testDefaultFormatDatetime() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testDefaultFileFormatTimestamp() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testReplaceTokenStringStringObject() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testReplace() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testReplaceTokenStringStringString() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testReplaceCRToken() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testWrapText() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testTrimAndUpperCase() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testToLowerCaseString() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testToLowerCaseStringArray() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testToUpperCase() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testProcessCaseTreatment() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testToStringObject() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testToStringOrEmpty() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testSingleQuote() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testIsNotBlank() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testNoneBlank() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testAllEquals() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testEqualsAnyIgnoreCaseStringStringArray() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testEqualsAnyIgnoreCaseStringString() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testConcat() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testConcatWithSpaces() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testEmptyIfNull() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testIsWildcardOrNull() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testReturnIfNotNull() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testFirstCharToUpper() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testTrim() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testPrepareForNumericParsing() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testNullIfEmpty() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testAsList() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testBoxify() {
        fail("Not yet implemented");
    }

}
