package net.objectlab.kit.datecalc.joda;

import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.objectlab.kit.datecalc.common.DateCalculator;

public abstract class AbstractDateTestCase<E> extends TestCase {
    protected abstract E newDate(final String date);

    protected abstract DateCalculator<E> newDateCalculator(String name, String type);

    protected void checkDate(final String string, final DateCalculator<E> calendar, final String string2) {
        Assert.assertEquals(string, newDate(string2), calendar.getCurrentBusinessDate());
    }

    protected abstract Set<E> newHolidaysSet();

    protected abstract Set<E> createUKHolidays();

    protected abstract Set<E> createUSHolidays();

    protected abstract void registerHolidays(final String name, Set<E> holidays);
}
