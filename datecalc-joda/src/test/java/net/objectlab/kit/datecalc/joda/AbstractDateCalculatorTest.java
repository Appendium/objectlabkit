package net.objectlab.kit.datecalc.joda;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.joda.time.LocalDate;

public abstract class AbstractDateCalculatorTest extends TestCase {

    public AbstractDateCalculatorTest() {
        super();
    }

    protected Set<LocalDate> createUKHolidays() {
        final Set<LocalDate> uk = new HashSet<LocalDate>();
        uk.add(new LocalDate("2006-01-01"));
        uk.add(new LocalDate("2006-08-28"));
        uk.add(new LocalDate("2006-12-25"));
        uk.add(new LocalDate("2006-12-26"));
        return uk;
    }

    protected Set<LocalDate> createUSHolidays() {
        final Set<LocalDate> us = new HashSet<LocalDate>();
        us.add(new LocalDate("2006-07-04"));
        us.add(new LocalDate("2006-11-28"));
        us.add(new LocalDate("2006-12-25"));
        return us;
    }
}