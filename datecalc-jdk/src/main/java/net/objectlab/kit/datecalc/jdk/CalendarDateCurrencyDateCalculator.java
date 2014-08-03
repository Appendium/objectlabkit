package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.AbstractCurrencyDateCalculator;
import net.objectlab.kit.datecalc.common.DateCalculator;

public class CalendarDateCurrencyDateCalculator extends AbstractCurrencyDateCalculator<Calendar> {
    public CalendarDateCurrencyDateCalculator(String ccy1, String ccy2, DateCalculator<Calendar> calculator) {
        super(ccy1, ccy2, calculator);
    }

    @Override
    protected void moveToNextWeekday() {
        final Calendar calendar = getCalculator().getCurrentBusinessDate();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        getCalculator().forceCurrentDateNoAdjustment(calendar);
        while (getCalculator().isWeekend(getCalculator().getCurrentBusinessDate())) {
            final Calendar calendar2 = getCalculator().getCurrentBusinessDate();
            calendar2.add(Calendar.DAY_OF_MONTH, 1);
            getCalculator().forceCurrentDateNoAdjustment(calendar2);
        }
    }
}
