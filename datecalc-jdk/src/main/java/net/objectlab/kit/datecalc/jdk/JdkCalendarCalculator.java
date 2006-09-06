package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.Tenor;

public interface JdkCalendarCalculator extends DateCalculator<Calendar> {

    JdkCalendarCalculator moveByDays(final int days);

    JdkCalendarCalculator moveByBusinessDays(final int businessDays);

    JdkCalendarCalculator combine(DateCalculator<Calendar> calculator);

    JdkCalendarCalculator moveByTenor(final Tenor tenor);
}