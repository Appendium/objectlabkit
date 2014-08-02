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
        getCalculator().moveByDays(1);
        while (getCalculator().isWeekend(getCalculator().getCurrentBusinessDate())) {
            getCalculator().moveByDays(1);
        }
    }
}
