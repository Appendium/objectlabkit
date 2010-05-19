/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 * 
 * Based in London, we are world leaders in the design and development 
 * of bespoke applications for the securities financing markets.
 * 
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 *
 * $Id: AbstractDateCalculator.java 309 2010-03-23 21:01:49Z marchy $
 * 
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.objectlab.kit.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.StandardToStringStyle;

/**
 * @author Benoit Xhenseval
 *
 */
public final class StringUtil {
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
    private static final SimpleDateFormat FILE_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyyMMdd-HHmmss");
    private static final String NEWLINE_TOKEN = "%CR%";
    private static final CharSequence TOKEN = "%";

    public static final String NEW_LINE = System.getProperty("line.separator");
    public static final String SINGLE_QUOTE = "'";
    public static final String SLASH = "/";
    public static final String NULL_COL = "null";
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String WILDCARD = "*";
    public static final StandardToStringStyle STYLE = new StandardToStringStyle();
    public static final String OPEN_PARENTHESES = "(";
    public static final String CLOSE_PARENTHESES = ")";
    private static final Pattern PATTERN_FOR_NUM_PARSING_PREP = Pattern.compile("\"|\\s|,");

    static {
        STYLE.setFieldSeparator(NEW_LINE);
        STYLE.setUseShortClassName(true);
    }

    private StringUtil() {
    }

    public static String removeTrailingChar(final String original, final char charToRemove) {
        final StringBuilder builder = new StringBuilder(original);
        while (builder.length() > 0 && builder.charAt(builder.length() - 1) == charToRemove) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    public static String defaultFormatDatetime(final Date date) {
        if (date != null) {
            return DATETIME_FORMAT.format(date);
        }
        return null;
    }

    public static String defaultFileFormatTimestamp(final Date date) {
        if (date != null) {
            return FILE_TIMESTAMP_FORMAT.format(date);
        }
        return null;
    }

    public static String replaceToken(final String original, final String token, final Object replacement) {
        if (replacement == null) {
            return StringUtil.replaceToken(original, token, null);
        }

        return StringUtil.replaceToken(original, token, replacement.toString());
    }

    public static String replace(final String originalPattern, final String newPattern, final String originalString) {
        if (originalPattern == null || originalPattern.length() == 0 || originalString == null) {
            return originalString;
        }

        final StringBuffer newString = new StringBuffer(originalString.length());
        int index = 0;
        final int originalLength = originalPattern.length();
        int previousIndex = 0;

        while ((index = originalString.indexOf(originalPattern, index)) != -1) {
            newString.append(originalString.substring(previousIndex, index)).append(newPattern);
            index += originalLength;
            previousIndex = index;
        }

        if (previousIndex == 0) {
            newString.append(originalString);
        } else if (previousIndex != originalString.length()) {
            newString.append(originalString.substring(previousIndex));
        }

        return newString.toString();
    }

    /**
     * Replaces the token, surrounded by % within a string with new value.
     * Also replaces any %CR% tokens with the newline character.
     *
     * @return the <code>original</code> string with the <code>%token%</code>
     *         (if it exists) replaced with the <code>replacement</code>
     *         string. If <code>original</code> or <code>%token%</code> are
     *         null, then returns the <code>original</code> string.
     */
    public static String replaceToken(final String original, final String token, final String replacement) {
        final StringBuffer tok = new StringBuffer(TOKEN);
        tok.append(token).append(TOKEN);

        final String toReplace = replaceCRToken(original);

        return StringUtil.replace(tok.toString(), replacement, toReplace);
    }

    /**
     * Replaces any %CR% tokens with the newline character.
     *
     * @return the <code>original</code> string with the '%CR%' tokens
     *         (if it exists) replaced with the new line character.
     */
    public static String replaceCRToken(final String original) {
        String toReplace = original;

        if (original != null && original.indexOf(NEWLINE_TOKEN) >= 0) {
            toReplace = StringUtil.replace(NEWLINE_TOKEN, NEW_LINE, original);
        }

        return toReplace;
    }

    /**
     * Takes a block of text which might have long lines in it and wraps
     * the long lines based on the supplied wrapColumn parameter. It was
     * initially implemented for use by VelocityEmail. If there are tabs
     * in inString, you are going to get results that are a bit strange,
     * since tabs are a single character but are displayed as 4 or 8
     * spaces. Remove the tabs.
     *
     * @param inString   Text which is in need of word-wrapping.
     * @param newline    The characters that define a newline.
     * @param wrapColumn The column to wrap the words at.
     * @return           The text with all the long lines word-wrapped.
     */
    public static String wrapText(final String inString, final String newline, final int wrapColumn) {
        if (inString == null) {
            return null;
        }
        final StringTokenizer lineTokenizer = new StringTokenizer(inString, newline, true);
        final StringBuffer stringBuffer = new StringBuffer();

        while (lineTokenizer.hasMoreTokens()) {
            try {
                String nextLine = lineTokenizer.nextToken();

                if (nextLine.length() > wrapColumn) {
                    // This line is long enough to be wrapped.
                    nextLine = wrapLine(nextLine, newline, wrapColumn);
                }

                stringBuffer.append(nextLine);
            } catch (final NoSuchElementException nsee) {
                // thrown by nextToken(), but I don't know why it would
                break;
            }
        }

        return stringBuffer.toString();
    }

    /**
     * Wraps a single line of text. Called by wrapText(). I can't
     * think of any good reason for exposing this to the public,
     * since wrapText should always be used AFAIK.
     *
     * @param line       A line which is in need of word-wrapping.
     * @param newline    The characters that define a newline.
     * @param wrapColumn The column to wrap the words at.
     * @return           A line with newlines inserted.
     */

    private static String wrapLine(String line, final String newline, final int wrapColumn) {
        final StringBuffer wrappedLine = new StringBuffer();

        while (line.length() > wrapColumn) {
            int spaceToWrapAt = line.lastIndexOf(' ', wrapColumn);

            if (spaceToWrapAt >= 0) {
                wrappedLine.append(line.substring(0, spaceToWrapAt));
                wrappedLine.append(newline);
                line = line.substring(spaceToWrapAt + 1);
            } else {
                // This must be a really long word or URL. Pass it
                // through unchanged even though it's longer than the
                // wrapColumn would allow. This behavior could be
                // dependent on a parameter for those situations when
                // someone wants long words broken at line length.
                spaceToWrapAt = line.indexOf(' ', wrapColumn);

                if (spaceToWrapAt >= 0) {
                    wrappedLine.append(line.substring(0, spaceToWrapAt));
                    wrappedLine.append(newline);
                    line = line.substring(spaceToWrapAt + 1);
                } else {
                    wrappedLine.append(line);
                    line = "";
                }
            }
        }

        // Whatever is left in line is short enough to just pass through,
        // just like a small small kidney stone
        wrappedLine.append(line);

        return wrappedLine.toString();
    }

    public static String trimAndUpperCase(final String str) {
        if (str != null) {
            return str.trim().toUpperCase();
        }
        return null;
    }

    public static String toLowerCase(final String str) {
        return str != null ? str.toLowerCase() : null;
    }

    public static String toLowerCase(final String... str) {
        final StringBuilder b = new StringBuilder();
        if (str == null) {
            return null;
        }
        for (final String s : str) {
            b.append(s.toLowerCase());
        }
        return b.toString();
    }

    public static String toUpperCase(final String str) {
        return str != null ? str.toUpperCase() : null;
    }

    public static String processCaseTreatment(final String str, final CaseTreatment caseTreatment) {
        if (caseTreatment == CaseTreatment.UPPER_CASE) {
            return toUpperCase(str);
        }
        if (caseTreatment == CaseTreatment.LOWER_CASE) {
            return toLowerCase(str);
        }
        return str;
    }

    /**
     * @return object.toString if object != null; otherwise returns null;
     */
    public static String toString(final Object object) {
        return object != null ? object.toString() : null;
    }

    /**
     * @return object.toString if object != null; otherwise returns "";
     */
    public static String toStringOrEmpty(final Object object) {
        return object != null ? object.toString() : EMPTY;
    }

    /**
     * Add single quotes ' around the text.
     * @param text
     * @return 'text'
     */
    public static String singleQuote(final String text) {
        final StringBuilder b = new StringBuilder((text != null ? text.length() : 0) + 2);
        b.append(SINGLE_QUOTE);
        if (text != null) {
            b.append(text);
        }
        b.append(SINGLE_QUOTE);
        return b.toString();
    }

    public static boolean isNotBlank(final Object text) {
        if (text instanceof String) {
            return StringUtils.isNotBlank((String) text);
        }
        return false;
    }

    public static boolean noneBlank(final String... text) {
        for (final String txt : text) {
            if (StringUtils.isBlank(txt)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return true if all strings are the same.
     */
    public static boolean allEquals(final String value, final String... strings) {
        for (final String s : strings) {
            if (s == null && value != null || s != null && value != null && !s.equals(value)) {
                return false;
            }
        }
        return true;

    }

    /**
     * @param val string to look for
     * @param strings possibilities
     * @return true if any of strs is equals to val (ignoring case).
     */
    public static boolean equalsAnyIgnoreCase(final String val, final String... strings) {
        if (strings != null) {
            for (final String o2 : strings) {
                if (val == null) {
                    if (o2 == null) {
                        return true;
                    }
                    continue;
                } else if (o2 == null) {
                    continue;
                }
                // o1 != null
                if (val.equalsIgnoreCase(o2)) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * @param val string to look for
     * @param toCheck string to check
     * @return true if val is equals to '*' or toCheck.
     */
    public static boolean equalsAnyIgnoreCase(final String val, final String toCheck) {
        if (val == null && toCheck == null) {
            return true;
        }
        if (val != null && WILDCARD.equals(val)) {
            return true;
        }
        if (val != null && toCheck != null) {
            return val.equalsIgnoreCase(toCheck);
        }
        return false;
    }

    /**
     * Concatenate the string value of the objects (toString()). If osString is null, return empty ""
     * but if one of the value is null, then use 'null' string.
     */
    public static String concat(final Object... osToString) {
        final StringBuilder b = new StringBuilder();
        for (final Object o : osToString) {
            b.append(o != null ? o.toString() : "null");
        }
        return b.toString();
    }

    /**
     * Concatenate the string value of the objects (toString()) and
     * insert a space between each value. If osString is null, return empty ""
     * but if one of the value is null, then use 'null' string.
     */
    public static String concatWithSpaces(final Object... osToString) {
        final StringBuilder b = new StringBuilder();
        boolean notFirst = false;
        for (final Object o : osToString) {
            if (notFirst) {
                b.append(" ");
            } else {
                notFirst = true;
            }
            b.append(o != null ? o.toString() : "null");
        }
        return b.toString();
    }

    /**
     * Returns "" if obj is null, obj.toString() otherwise.
     * @param obj
     */
    public static String emptyIfNull(final Object obj) {
        return obj != null ? obj.toString() : EMPTY;
    }

    public static boolean isWildcardOrNull(final String txt) {
        return txt == null || WILDCARD.equals(txt);
    }

    public static String returnIfNotNull(final String txt, final String defaultTxt) {
        return txt != null ? txt : defaultTxt;
    }

    public static String firstCharToUpper(final String txt) {
        String val = null;
        if (StringUtils.isNotBlank(txt)) {
            final StringBuilder b = new StringBuilder(txt.length());
            b.append(toUpperCase(String.valueOf(txt.charAt(0))));
            if (txt.length() > 1) {
                b.append(txt.substring(1));
            }
            val = b.toString();
        }
        return val;
    }

    /**
     * Handles null.
     */
    public static String trim(final String str) {
        if (str != null) {
            return str.trim();
        }
        return null;
    }

    /**
     * Remove " and spaces from the input string.
     * @param inputStr
     * @return
     */
    public static String prepareForNumericParsing(final String inputStr) {
        final Matcher matcher = PATTERN_FOR_NUM_PARSING_PREP.matcher(inputStr);
        return matcher.replaceAll("");
    }

    /**
     * return null if txt is null or empty
     * @param txt
     */
    public static String nullIfEmpty(final String txt) {
        return StringUtils.isNotBlank(txt) ? txt : null;
    }

    /**
     * Handle null.
     */
    public static int compareTo(final String s1, final String s2) {
        int ret = -2;
        if (s1 == null && s2 == null) {
            ret = 0;
        } else if (s1 != null && s2 == null) {
            ret = 1;
        } else if (s1 == null && s2 != null) {
            ret = -1;
        }
        return ret == -2 ? s1 != null ? s1.compareTo(s2) : -1 : ret;
    }

    public static String asList(final String[] values, final String separator) {
        final StringBuilder b = new StringBuilder();
        if (values != null) {
            for (final String s : values) {
                if (b.length() > 0) {
                    b.append(separator);
                }
                b.append(s);
            }
        }
        return b.toString();
    }

    /**
     * Returns a String which is surrounded by a box made of boxing char.
     * @param boxing boxing character, eg '+'
     * @param text
     * @return
     */
    public static String boxify(final char boxing, final String text) {
        if (boxing != 0 && StringUtils.isNotBlank(text)) {
            final StringBuilder b = new StringBuilder();
            b.append(NEW_LINE);
            final String line = StringUtils.repeat(String.valueOf(boxing), text.length() + 4);
            b.append(line).append(NEW_LINE);
            b.append(boxing).append(SPACE).append(text).append(SPACE).append(boxing).append(NEW_LINE);
            b.append(line).append(NEW_LINE);
            return b.toString();
        }
        return EMPTY;
    }

    /**
    *
    * @param strings
    * @return true if any are empty
    */
   public static boolean anyEmpty(final String... strings) {
       if (strings != null) {
           for (final String object : strings) {
               if (StringUtils.isEmpty(object)) {
                   return true;
               }
           }
           return strings.length == 0 ? true : false;
       }
       return true;
   }

  
   /**
    * Does equalsIgnoreCase call but if the value is '*', immediately returns true.
    */
   public static boolean equalsIgnoreCaseOrValueIsWildcard(final String value, final String toCheck) {
       if (value == null && toCheck == null) {
           return true;
       }
       if (value != null && StringUtil.WILDCARD.equals(value)) {
           return true;
       }
       if (value != null && toCheck != null) {
           return value.equalsIgnoreCase(toCheck);
       }
       return false;
   }
}
