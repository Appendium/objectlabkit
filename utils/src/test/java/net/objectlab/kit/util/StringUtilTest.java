package net.objectlab.kit.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StringUtilTest {
    private TimeZone tzBefore;

    @Before
    public void setUp() {
        tzBefore = TimeZone.getDefault();
        System.err.println("setup " + tzBefore);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @After
    public void tearDown() {
        System.err.println("tearDown " + tzBefore);
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
        System.err.println("TEST " + TimeZone.getDefault());
        assertThat(StringUtil.defaultFormatDatetime(new Date(1531603574189L))).isEqualToIgnoringCase("14-Jul-2018 22:26:14");
    }

    @Test
    public void testDefaultFileFormatTimestamp() {
        assertThat(StringUtil.defaultFileFormatTimestamp(null)).isNull();
        assertThat(StringUtil.defaultFileFormatTimestamp(new Date(1531603574189L))).isEqualToIgnoringCase("20180714-222614");
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

        assertThat(StringUtil.boxify((char) 0, "Hi")).isEmpty();
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

    @Test
    public void testReplaceTokenStringStringString() {
        assertThat(StringUtil.replaceToken(null, null, null)).isNull();
        assertThat(StringUtil.replaceToken("", null, null)).isEmpty();
        assertThat(StringUtil.replaceToken("", "", null)).isEmpty();
        assertThat(StringUtil.replaceToken("", "", "")).isEmpty();
        assertThat(StringUtil.replaceToken("Hello", "YO", "!")).isEqualTo("Hello");
        assertThat(StringUtil.replaceToken("Hello %YO%", "YO", "you")).isEqualTo("Hello you");
        assertThat(StringUtil.replaceToken("Hello %YO%", "YI", "you")).isEqualTo("Hello %YO%");
        assertThat(StringUtil.replaceToken("Hello %YO%", "YO", "")).isEqualTo("Hello ");
        assertThat(StringUtil.replaceToken("Hello %YO%", "YO", null)).isEqualTo("Hello null");
    }

    @Test
    public void testReplaceTokenStringStringObject() {
        assertThat(StringUtil.replaceToken(null, null, null)).isNull();
        assertThat(StringUtil.replaceToken("", null, null)).isEmpty();
        assertThat(StringUtil.replaceToken("", "", null)).isEmpty();
        assertThat(StringUtil.replaceToken(null, "", "")).isNull();
        assertThat(StringUtil.replaceToken(null, "A", "BC")).isNull();
        assertThat(StringUtil.replaceToken("Hello %YO%", "YO", Long.valueOf(1234))).isEqualTo("Hello 1234");
        assertThat(StringUtil.replaceToken("Hello %YO%", "YO", (Object) null)).isEqualTo("Hello null");
        assertThat(StringUtil.replaceToken("Hello %YO%", "YU", (Object) null)).isEqualTo("Hello %YO%");
    }

    @Test
    public void testEqualsIgnoreCaseOrValueIsWildcard() {
        assertThat(StringUtil.equalsIgnoreCaseOrValueIsWildcard(null, null)).isTrue();
        assertThat(StringUtil.equalsIgnoreCaseOrValueIsWildcard("", "")).isTrue();
        assertThat(StringUtil.equalsIgnoreCaseOrValueIsWildcard("", "A")).isFalse();
        assertThat(StringUtil.equalsIgnoreCaseOrValueIsWildcard(null, "A")).isFalse();
        assertThat(StringUtil.equalsIgnoreCaseOrValueIsWildcard("", null)).isFalse();
        assertThat(StringUtil.equalsIgnoreCaseOrValueIsWildcard("A", null)).isFalse();
        assertThat(StringUtil.equalsIgnoreCaseOrValueIsWildcard("*", null)).isTrue();
        assertThat(StringUtil.equalsIgnoreCaseOrValueIsWildcard("*", "")).isTrue();
        assertThat(StringUtil.equalsIgnoreCaseOrValueIsWildcard("ABC", "*")).isFalse();
        assertThat(StringUtil.equalsIgnoreCaseOrValueIsWildcard("aBC", "ABC")).isTrue();
        assertThat(StringUtil.equalsIgnoreCaseOrValueIsWildcard("*", "ABC")).isTrue();
        assertThat(StringUtil.equalsIgnoreCaseOrValueIsWildcard("**", "ABC")).isFalse();
        assertThat(StringUtil.equalsIgnoreCaseOrValueIsWildcard("AB C", "ab c")).isTrue();
    }

    @Test
    public void testWrapText() {
        assertThat(StringUtil.wrapText(null, "#", 5)).isNull();
        assertThat(StringUtil.wrapText("", "#", 5)).isEmpty();
        assertThat(StringUtil.wrapText("1234", "#", 5)).isEqualTo("1234");
        assertThat(StringUtil.wrapText("12345 6789", "#", 5)).isEqualTo("12345#6789");
        assertThat(StringUtil.wrapText("123456 789", "#", 5)).isEqualTo("123456#789");
        assertThat(StringUtil.wrapText("12345 789101 1213", "#", 5)).isEqualTo("12345#789101#1213");
        assertThat(StringUtil.wrapText("12345 789101 12134", "#", 5)).isEqualTo("12345#789101#12134");
        assertThat(StringUtil.wrapText("12345 789101 12134 ", "#", 5)).isEqualTo("12345#789101#12134#");
        assertThat(StringUtil.wrapText(" 12345 789101 12134 ", "#", 5)).isEqualTo("#12345#789101#12134#");
        assertThat(StringUtil.wrapText(" 12345 789101  12134 ", "#", 5)).isEqualTo("#12345#789101##12134#");
        assertThat(StringUtil.wrapText("123345#6789", "#", 5)).isEqualTo("123345#6789");
    }

    @Test
    public void testProcessCaseTreatment() {
        assertThat(StringUtil.processCaseTreatment(null, CaseTreatment.LOWER_CASE)).isNull();
        assertThat(StringUtil.processCaseTreatment(null, CaseTreatment.UNCHANGED)).isNull();
        assertThat(StringUtil.processCaseTreatment(null, CaseTreatment.UPPER_CASE)).isNull();

        assertThat(StringUtil.processCaseTreatment(" Hello World ", CaseTreatment.LOWER_CASE)).isEqualTo(" hello world ");
        assertThat(StringUtil.processCaseTreatment(" Hello World ", CaseTreatment.UNCHANGED)).isEqualTo(" Hello World ");
        assertThat(StringUtil.processCaseTreatment(" Hello World ", CaseTreatment.UPPER_CASE)).isEqualTo(" HELLO WORLD ");

        assertThat(StringUtil.processCaseTreatment("", CaseTreatment.LOWER_CASE)).isEmpty();
        assertThat(StringUtil.processCaseTreatment("", CaseTreatment.UNCHANGED)).isEmpty();
        assertThat(StringUtil.processCaseTreatment("", CaseTreatment.UPPER_CASE)).isEmpty();
    }

    @Test
    public void testAnyEmpty() {
        assertThat(StringUtil.anyEmpty()).isTrue();
        assertThat(StringUtil.anyEmpty(null)).isTrue();
        assertThat(StringUtil.anyEmpty(null, null)).isTrue();
        assertThat(StringUtil.anyEmpty("", null)).isTrue();
        assertThat(StringUtil.anyEmpty("", "")).isTrue();
        assertThat(StringUtil.anyEmpty("X", "X")).isFalse();
        assertThat(StringUtil.anyEmpty("X", "X", null)).isTrue();
        assertThat(StringUtil.anyEmpty("X", "X", "")).isTrue();
    }

    @Test
    public void testReturnIfNotNull() {
        assertThat(StringUtil.returnIfNotNull(null, null)).isNull();
        assertThat(StringUtil.returnIfNotNull(null, "")).isEmpty();
        assertThat(StringUtil.returnIfNotNull(null, "ABC")).isEqualTo("ABC");
        assertThat(StringUtil.returnIfNotNull("", "ABC")).isEqualTo("");
        assertThat(StringUtil.returnIfNotNull("DEF", "ABC")).isEqualTo("DEF");
        assertThat(StringUtil.returnIfNotNull("DEF", null)).isEqualTo("DEF");
    }

    @Test
    public void testFirstCharToUpper() {
        assertThat(StringUtil.firstCharToUpper(null)).isNull();
        assertThat(StringUtil.firstCharToUpper("")).isEmpty();
        assertThat(StringUtil.firstCharToUpper("A")).isEqualTo("A");
        assertThat(StringUtil.firstCharToUpper("a")).isEqualTo("A");
        assertThat(StringUtil.firstCharToUpper("belgium")).isEqualTo("Belgium");
        assertThat(StringUtil.firstCharToUpper("belgium brussels")).isEqualTo("Belgium brussels");
        assertThat(StringUtil.firstCharToUpper(" belgium brussels")).isEqualTo(" belgium brussels");
    }

    @Test
    public void testPrepareForNumericParsing() {
        assertThat(StringUtil.prepareForNumericParsing(null)).isEmpty();
        assertThat(StringUtil.prepareForNumericParsing("")).isEmpty();
        assertThat(StringUtil.prepareForNumericParsing("1234")).isEqualTo("1234");
        assertThat(StringUtil.prepareForNumericParsing(" 1,234 ")).isEqualTo("1234");
        assertThat(StringUtil.prepareForNumericParsing(" 1,234.00 ")).isEqualTo("1234.00");
        assertThat(StringUtil.prepareForNumericParsing(" \"1,234.00\" ")).isEqualTo("1234.00");
    }

    @Test
    public void testNullIfEmpty() {
        assertThat(StringUtil.nullIfEmpty(null)).isNull();
        assertThat(StringUtil.nullIfEmpty("")).isNull();
        assertThat(StringUtil.nullIfEmpty("AB")).isEqualTo("AB");
    }

    @Test
    public void testAsList() {
        assertThat(StringUtil.asList(null, "||")).isEmpty();
        assertThat(StringUtil.asList(new String[] { "A", "B" }, "||")).isEqualTo("A||B");
        assertThat(StringUtil.asList(new String[] { "A", "B", "C" }, "||")).isEqualTo("A||B||C");
        assertThat(StringUtil.asList(new String[] { "A", "", "C" }, "||")).isEqualTo("A||||C");
        assertThat(StringUtil.asList(new String[] { "A", null, "C" }, "||")).isEqualTo("A||null||C");
    }

}
