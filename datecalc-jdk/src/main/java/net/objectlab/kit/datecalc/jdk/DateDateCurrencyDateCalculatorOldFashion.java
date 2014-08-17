package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;
import java.util.Date;

import net.objectlab.kit.datecalc.common.AbstractCurrencyDateCalculatorOldFashion;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.Utils;
import net.objectlab.kit.datecalc.common.ccy.CurrencyCalculatorConfig;

@Deprecated
public class DateDateCurrencyDateCalculatorOldFashion extends AbstractCurrencyDateCalculatorOldFashion<Date> {
    public DateDateCurrencyDateCalculatorOldFashion(final String ccy1, final String ccy2, final DateCalculator<Date> calculator,
            final CurrencyCalculatorConfig config) {
        super(ccy1, ccy2, calculator, config);
    }

    @Override
    protected void moveToNextWeekday() {
        final Calendar calendar = Utils.getCal(getCalculator().getCurrentBusinessDate());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        getCalculator().forceCurrentDateNoAdjustment(calendar.getTime());
        while (getCalculator().isWeekend(getCalculator().getCurrentBusinessDate())) {
            final Calendar calendar2 = Utils.getCal(getCalculator().getCurrentBusinessDate());
            calendar2.add(Calendar.DAY_OF_MONTH, 1);
            getCalculator().forceCurrentDateNoAdjustment(calendar2.getTime());
        }
    }
}
