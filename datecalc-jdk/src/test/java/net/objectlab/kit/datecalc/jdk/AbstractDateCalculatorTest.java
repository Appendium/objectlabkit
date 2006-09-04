package net.objectlab.kit.datecalc.jdk;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import net.objectlab.kit.datecalc.common.Utils;

public abstract class AbstractDateCalculatorTest extends TestCase {

    public AbstractDateCalculatorTest() {
        super();
    }

    protected Date createDate(final String str) {
        return Utils.createDate(str);
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