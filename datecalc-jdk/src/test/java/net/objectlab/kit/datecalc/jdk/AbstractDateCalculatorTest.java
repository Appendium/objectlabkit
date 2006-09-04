package net.objectlab.kit.datecalc.jdk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;

public abstract class AbstractDateCalculatorTest extends TestCase {

    public AbstractDateCalculatorTest() {
        super();
    }

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    // TODO remove this and use utils one instead
    protected Date createDate(final String str) {
        try {
            return SDF.parse(str);
        } catch (final ParseException e) {
            Assert.fail(e.toString());
        }
        return null;
    }

    protected Set<Date> createUKHolidays() {
        final Set<Date> uk = new HashSet<Date>();
        uk.add(createDate("2006-01-01"));
        uk.add(createDate("2006-08-28"));
        uk.add(createDate("2006-12-25"));
        uk.add(createDate("2006-12-26"));
        return uk;
    }

    protected Set<Date> createUSHolidays() {
        final Set<Date> us = new HashSet<Date>();
        us.add(createDate("2006-07-04"));
        us.add(createDate("2006-11-28"));
        us.add(createDate("2006-12-25"));
        return us;
    }
}