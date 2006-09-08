package net.objectlab.kit.datecalc.common;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;

public abstract class AbstractDateTestCase<E> extends TestCase {
    protected abstract E newDate(final String date);

    protected abstract KitCalculatorsFactory<E> getDateCalculatorFactory();

    protected void checkDate(final String string, final DateCalculator<E> calendar, final String string2) {
        Assert.assertEquals(string, newDate(string2), calendar.getCurrentBusinessDate());
    }

    protected Set<E> newHolidaysSet() {
        final Set<E> holidays = new HashSet<E>();
        holidays.add(newDate("2006-08-28"));
        holidays.add(newDate("2006-12-25"));
        holidays.add(newDate("2006-12-26"));
        return holidays;
    }

    protected Set<E> createUKHolidays() {
        final Set<E> uk = new HashSet<E>();
        uk.add(newDate("2006-01-01"));
        uk.add(newDate("2006-08-28"));
        uk.add(newDate("2006-12-25"));
        uk.add(newDate("2006-12-26"));
        return uk;
    }

    protected Set<E> createUSHolidays() {
        final Set<E> us = new HashSet<E>();
        us.add(newDate("2006-07-04"));
        us.add(newDate("2006-11-28"));
        us.add(newDate("2006-12-25"));
        return us;
    }

    protected void registerHolidays(final String name, final Set<E> holidays) {
        getDateCalculatorFactory().registerHolidays(name, holidays);
    }

    protected WorkingWeek getWorkingWeek(final WorkingWeek ww) {
        return ww;
    }

    protected DateCalculator<E> newDateCalculator(final String name, final String type) {
        return getDateCalculatorFactory().getDateCalculator(name, type);
    }

    /**
     * Based on UK Holidays for Aug 2006.
     * @param startDate
     * @param tenor
     * @param daysToSpot
     * @param expectedDate
     * @param holidayHandlerType
     */
    protected void checkMoveByTenor(final String startDate, final Tenor tenor, final int daysToSpot, final String expectedDate,
            final String holidayHandlerType) {        
        final DateCalculator<E> cal = newDateCalculator("bla", holidayHandlerType);
        cal.setNonWorkingDays(createUKHolidays());
        cal.setStartDate(newDate(startDate));
        checkDate("Move start:" + startDate + " tenor:" + tenor + " daysToSpot:" + daysToSpot,
                cal.moveByTenor(tenor, daysToSpot), expectedDate);
    }

}
