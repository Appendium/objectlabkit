package net.objectlab.kit.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class StringUtilTest {
    private TimeZone tzBefore;

    @Before
    public void setUp() {
        tzBefore = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @After
    public void tearDown() {
        TimeZone.setDefault(tzBefore);
    }

    @Test
    public void testCompareTo() {
        assertThat(StringUtil.compareTo(null, null)).isEqualTo(0);
        assertThat(StringUtil.compareTo("A", null)).isEqualTo(1);
        assertThat(StringUtil.compareTo(null, "B")).isEqualTo(-1);
        assertThat(StringUtil.compareTo("A", "B")).isEqualTo(-1);
        assertThat(StringUtil.compareTo("XA", "B")).isEqualTo(22);
        assertThat(StringUtil.compareTo("XA", "XA")).isEqualTo(0);
    }

    @Test
    public void testRemoveTrailingChar() {
        assertThat(StringUtil.removeTrailingChar(null, ' ')).isNull();
        assertThat(StringUtil.removeTrailingChar("", ' ')).isEmpty();
        assertThat(StringUtil.removeTrailingChar("Hello ", ' ')).isEqualToIgnoringCase("Hello");
        assertThat(StringUtil.removeTrailingChar("Hello     ", ' ')).isEqualToIgnoringCase("Hello");
        assertThat(StringUtil.removeTrailingChar(" Hello  ", ' ')).isEqualToIgnoringCase(" Hello");
        assertThat(StringUtil.removeTrailingChar(" Hellooo", 'o')).isEqualToIgnoringCase(" Hell");
        assertThat(StringUtil.removeTrailingChar(" Hollooo", 'o')).isEqualToIgnoringCase(" Holl");
    }

    @Test
    public void testReplaceCRToken() {
        assertThat(StringUtil.replaceCRToken(null)).isNull();
        assertThat(StringUtil.replaceCRToken("Hello")).isEqualTo("Hello");
        assertThat(StringUtil.replaceCRToken("Hello%CR%")).isEqualTo("Hello" + System.getProperty("line.separator"));
        assertThat(StringUtil.replaceCRToken("Hel%CR%lo")).isEqualTo("Hel" + System.getProperty("line.separator") + "lo");
    }

    @Test
    public void testToUpperCase() {
        assertThat(StringUtil.toUpperCase(null)).isNull();
        assertThat(StringUtil.toUpperCase("Hi")).isEqualTo("HI");
        assertThat(StringUtil.toUpperCase("HeLLo")).isEqualTo("HELLO");
    }

    @Test
    public void testIsNotBlank() {
        assertThat(StringUtil.isNotBlank(null)).isFalse();
        assertThat(StringUtil.isNotBlank("")).isFalse();
        assertThat(StringUtil.isNotBlank(" ")).isFalse();
        assertThat(StringUtil.isNotBlank(" X")).isTrue();
        assertThat(StringUtil.isNotBlank(new Object())).isFalse();
        assertThat(StringUtil.isNotBlank(BigDecimal.ONE)).isFalse();
    }

    @Test
    public void testDefaultFormatDatetime() {
        assertThat(StringUtil.defaultFormatDatetime(null)).isNull();
        assertThat(StringUtil.defaultFormatDatetime(new Date(1531603574189L))).isEqualToIgnoringCase("14-Jul-2018 21:26:14");
    }

    @Test
    public void testDefaultFileFormatTimestamp() {
        assertThat(StringUtil.defaultFileFormatTimestamp(null)).isNull();
        assertThat(StringUtil.defaultFileFormatTimestamp(new Date(1531603574189L))).isEqualToIgnoringCase("20180714-212614");
    }

    @Test
    public void testReplace() {
        assertThat(StringUtil.replace(null, null, null)).isNull();
        assertThat(StringUtil.replace("hi", null, null)).isNull();
        assertThat(StringUtil.replace("", null, null)).isNull();
        assertThat(StringUtil.replace("hi", "ho", null)).isNull();
        assertThat(StringUtil.replace("", "", null)).isNull();
        assertThat(StringUtil.replace("hi", "Ho", "")).isEmpty();
        assertThat(StringUtil.replace("", "", "")).isEmpty();
        assertThat(StringUtil.replace(null, null, "Hello")).isEqualTo("Hello");
        assertThat(StringUtil.replace("", null, "Hello")).isEqualTo("Hello");
        assertThat(StringUtil.replace(null, "", "Hello")).isEqualTo("Hello");
        assertThat(StringUtil.replace("", "", "Hello")).isEqualTo("Hello");
        assertThat(StringUtil.replace("H", "Ti", "Hello")).isEqualTo("Tiello");
        assertThat(StringUtil.replace("WW", "Ti", "Hello")).isEqualTo("Hello");
        assertThat(StringUtil.replace("l", "Ti", "Hello")).isEqualTo("HeTiTio");
        assertThat(StringUtil.replace("l", null, "Hello")).isEqualTo("Henullnullo");
        assertThat(StringUtil.replace(null, "@@", "Hello")).isEqualTo("Hello");
    }

    @Test
    public void testTrimAndUpperCase() {
        assertThat(StringUtil.trimAndUpperCase(null)).isNull();
        assertThat(StringUtil.trimAndUpperCase("")).isEmpty();
        assertThat(StringUtil.trimAndUpperCase("hi")).isEqualTo("HI");
        assertThat(StringUtil.trimAndUpperCase("  hi")).isEqualTo("HI");
        assertThat(StringUtil.trimAndUpperCase("  hi  ")).isEqualTo("HI");
        assertThat(StringUtil.trimAndUpperCase(" hi      ")).isEqualTo("HI");
    }

    @Test
    public void testToLowerCaseString() {
        assertThat(StringUtil.toLowerCase((String) null)).isNull();
        assertThat(StringUtil.toLowerCase("")).isEmpty();
        assertThat(StringUtil.toLowerCase("hi")).isEqualTo("hi");
        assertThat(StringUtil.toLowerCase("  hI")).isEqualTo("  hi");
        assertThat(StringUtil.toLowerCase("  Hi HO  ")).isEqualTo("  hi ho  ");
        assertThat(StringUtil.toLowerCase(" hi      ")).isEqualTo(" hi      ");
    }

    /*
    @Test
    public void testToLowerCaseStringArray() {
        assertThat(StringUtil.toLowerCase((String) null, null)).isNull();
        assertThat(StringUtil.toLowerCase("", "", "")).isEmpty();
        assertThat(StringUtil.toLowerCase("hi", "YO")).isEqualTo("hi");
        assertThat(StringUtil.toLowerCase("  hI", "hI")).isEqualTo("  hi");
    }
    */

    @Test
    public void testSingleQuote() {
        assertThat(StringUtil.singleQuote("")).isEqualTo("''");
        assertThat(StringUtil.singleQuote(null)).isEqualTo("''");
        assertThat(StringUtil.singleQuote("Hi World")).isEqualTo("'Hi World'");
        assertThat(StringUtil.singleQuote(" Hi World")).isEqualTo("' Hi World'");
    }

    @Test
    public void testNoneBlank() {
        assertThat(StringUtil.noneBlank("hi")).isTrue();
        assertThat(StringUtil.noneBlank("hi", "ho", "World")).isTrue();
        assertThat(StringUtil.noneBlank("hi", "ho", null, "World")).isFalse();
        assertThat(StringUtil.noneBlank("hi", "ho", "", "World")).isFalse();
        assertThat(StringUtil.noneBlank(null)).isFalse();
        assertThat(StringUtil.noneBlank("")).isFalse();
    }

    @Test
    public void testTrim() {
        assertThat(StringUtil.trim(null)).isNull();
        assertThat(StringUtil.trim("")).isEmpty();
        assertThat(StringUtil.trim("     ")).isEmpty();
        assertThat(StringUtil.trim("   hi  ")).isEqualTo("hi");
    }

    @Test
    public void testToStringObject() {
        assertThat(StringUtil.toString(null)).isNull();
        assertThat(StringUtil.toString("")).isEqualTo("");
        assertThat(StringUtil.toString("hi")).isEqualTo("hi");
        assertThat(StringUtil.toString(BigDecimal.TEN)).isEqualTo("10");
    }

    @Test
    public void testToStringOrEmpty() {
        assertThat(StringUtil.toStringOrEmpty(null)).isEqualTo("");
        assertThat(StringUtil.toStringOrEmpty("")).isEqualTo("");
        assertThat(StringUtil.toStringOrEmpty("hi")).isEqualTo("hi");
        assertThat(StringUtil.toStringOrEmpty(BigDecimal.TEN)).isEqualTo("10");
    }

    @Test
    public void testEmptyIfNull() {
        assertThat(StringUtil.emptyIfNull(null)).isEmpty();
        assertThat(StringUtil.emptyIfNull("")).isEmpty();
        assertThat(StringUtil.emptyIfNull("Hi")).isEqualTo("Hi");
    }

    @Test
    public void testConcat() {
        assertThat(StringUtil.concat(null)).isEmpty();
        assertThat(StringUtil.concat("")).isEmpty();
        assertThat(StringUtil.concat("h", "o", "World")).isEqualTo("hoWorld");
        assertThat(StringUtil.concat("h", null, "World")).isEqualTo("hnullWorld");
        assertThat(StringUtil.concat("h", null, "World", "")).isEqualTo("hnullWorld");
        assertThat(StringUtil.concat("h", null, "World", null)).isEqualTo("hnullWorldnull");
    }

    @Test
    public void testConcatWithSpaces() {
        assertThat(StringUtil.concatWithSpaces(null)).isEmpty();
        assertThat(StringUtil.concatWithSpaces("")).isEmpty();
        assertThat(StringUtil.concatWithSpaces("h", "o", "World")).isEqualTo("h o World");
        assertThat(StringUtil.concatWithSpaces("h", null, "World")).isEqualTo("h null World");
        assertThat(StringUtil.concatWithSpaces("h", null, "World", "")).isEqualTo("h null World ");
        assertThat(StringUtil.concatWithSpaces("h", null, "World", null)).isEqualTo("h null World null");
    }

    @Test
    public void testIsWildcardOrNull() {
        assertThat(StringUtil.isWildcardOrNull(null)).isTrue();
        assertThat(StringUtil.isWildcardOrNull("")).isFalse();
        assertThat(StringUtil.isWildcardOrNull("*")).isTrue();
        assertThat(StringUtil.isWildcardOrNull("**")).isFalse();
        assertThat(StringUtil.isWildcardOrNull(" ")).isFalse();
        assertThat(StringUtil.isWildcardOrNull(" Hello ")).isFalse();
    }

    @Test
    public void testBoxify() {
        assertThat(StringUtil.boxify('+', null)).isEmpty();
        assertThat(StringUtil.boxify('+', "")).isEmpty();
        assertThat(StringUtil.boxify('+', "X")).isEqualTo(System.lineSeparator() + "+++++" + System.lineSeparator() //
                + "+ X +" + System.lineSeparator() //
                + "+++++" + System.lineSeparator());
    }

    @Test
    public void testIfNotBlank() {
        assertThat(StringUtil.ifNotBlank(null, (t) -> fail("null should not have been called"))).isFalse();
        assertThat(StringUtil.ifNotBlank("", (t) -> fail("Empty should not have been called"))).isFalse();
        assertThat(StringUtil.ifNotBlank("Hello", new Consumer<String>() {
            @Override
            public void accept(String t) {
            }
        })).isTrue();
    }

    @Test
    public void testAllEquals() {
        assertThat(StringUtil.allEquals(null, null)).isTrue();
        assertThat(StringUtil.allEquals(null, null, null)).isTrue();
        assertThat(StringUtil.allEquals("", "", "")).isTrue();
        assertThat(StringUtil.allEquals("", "", " ")).isFalse();
        assertThat(StringUtil.allEquals(" ", " ", " ")).isTrue();
        assertThat(StringUtil.allEquals(" ", null)).isFalse();
        assertThat(StringUtil.allEquals(" ", null, null)).isFalse();
        assertThat(StringUtil.allEquals(" ", null, " ")).isFalse();
        assertThat(StringUtil.allEquals("X", null, "X")).isFalse();
        assertThat(StringUtil.allEquals("X", "X", "X")).isTrue();
        assertThat(StringUtil.allEquals(null, "X", "X")).isFalse();
    }

    @Test
    public void testEqualsAnyIgnoreCaseStringStringArray() {
        assertThat(StringUtil.equalsAnyIgnoreCase(null, null, null)).isTrue();
        assertThat(StringUtil.equalsAnyIgnoreCase(null, (String[]) null)).isTrue();
        assertThat(StringUtil.equalsAnyIgnoreCase("X", (String[]) null)).isFalse();
        assertThat(StringUtil.equalsAnyIgnoreCase(null, "", "")).isFalse();
        assertThat(StringUtil.equalsAnyIgnoreCase(null, "", null)).isTrue();
        assertThat(StringUtil.equalsAnyIgnoreCase("", "", null)).isTrue();
        assertThat(StringUtil.equalsAnyIgnoreCase("X", "", null)).isFalse();
        assertThat(StringUtil.equalsAnyIgnoreCase("X", "", null, "X")).isTrue();
        assertThat(StringUtil.equalsAnyIgnoreCase("X", "", null, "x")).isTrue();
        assertThat(StringUtil.equalsAnyIgnoreCase("X", "", "ABC", "X")).isTrue();
        assertThat(StringUtil.equalsAnyIgnoreCase("X", "", "DEX", "x")).isTrue();
    }

    @Test
    public void testEqualsAnyIgnoreCaseStringString() {
        assertThat(StringUtil.equalsAnyIgnoreCase((String) null, (String) null)).isTrue();
        assertThat(StringUtil.equalsAnyIgnoreCase((String) null, "Hi")).isFalse();
        assertThat(StringUtil.equalsAnyIgnoreCase("*", "Hi")).isTrue();
        assertThat(StringUtil.equalsAnyIgnoreCase("Hi", "*")).isFalse();
        assertThat(StringUtil.equalsAnyIgnoreCase("Hi", "NO")).isFalse();
        assertThat(StringUtil.equalsAnyIgnoreCase("Hi", "Hi")).isTrue();
        assertThat(StringUtil.equalsAnyIgnoreCase("Hi", "HI")).isTrue();
        assertThat(StringUtil.equalsAnyIgnoreCase(null, "HI")).isFalse();
        assertThat(StringUtil.equalsAnyIgnoreCase("Hi", (String) null)).isFalse();
    }

    @Ignore
    public void testReplaceTokenStringStringString() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testWrapText() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testReplaceTokenStringStringObject() {
        fail("Not yet implemented");
    }

    @Ignore
    public void testProcessCaseTreatment() {
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

}
