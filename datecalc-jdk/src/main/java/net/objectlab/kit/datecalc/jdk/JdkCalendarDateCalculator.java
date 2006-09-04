package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.Tenor;

public interface JdkCalendarDateCalculator extends DateCalculator<Calendar> {

    JdkCalendarDateCalculator moveByDays(final int days);

    JdkCalendarDateCalculator moveByBusinessDays(final int businessDays);

    JdkCalendarDateCalculator combine(DateCalculator<Calendar> calendar);

    JdkCalendarDateCalculator moveByTenor(final Tenor tenor);

}
