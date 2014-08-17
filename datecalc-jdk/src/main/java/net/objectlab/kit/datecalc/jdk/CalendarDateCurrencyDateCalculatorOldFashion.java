package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.AbstractCurrencyDateCalculatorOldFashion;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.ccy.CurrencyCalculatorConfig;

@Deprecated
public class CalendarDateCurrencyDateCalculatorOldFashion extends AbstractCurrencyDateCalculatorOldFashion<Calendar> {
    public CalendarDateCurrencyDateCalculatorOldFashion(final String ccy1, final String ccy2, final DateCalculator<Calendar> calculator,
            final CurrencyCalculatorConfig config) {
        super(ccy1, ccy2, calculator, config);
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
