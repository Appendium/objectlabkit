package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;
import java.util.Date;

import net.objectlab.kit.datecalc.common.AbstractCurrencyDateCalculator;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.Utils;

public class DateDateCurrencyDateCalculator extends AbstractCurrencyDateCalculator<Date> {
    public DateDateCurrencyDateCalculator(String ccy1, String ccy2, DateCalculator<Date> calculator) {
        super(ccy1, ccy2, calculator);
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
