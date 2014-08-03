package net.objectlab.kit.datecalc.jdk8;

import java.time.LocalDate;

import net.objectlab.kit.datecalc.common.AbstractCurrencyDateCalculator;
import net.objectlab.kit.datecalc.common.DateCalculator;

public class LocalDateCurrencyDateCalculator extends AbstractCurrencyDateCalculator<LocalDate> {
    public LocalDateCurrencyDateCalculator(String ccy1, String ccy2, DateCalculator<LocalDate> calculator) {
        super(ccy1, ccy2, calculator);
    }

    @Override
    protected void moveToNextWeekday() {
        getCalculator().forceCurrentDateNoAdjustment(getCalculator().getCurrentBusinessDate().plusDays(1));
        while (getCalculator().isWeekend(getCalculator().getCurrentBusinessDate())) {
            getCalculator().forceCurrentDateNoAdjustment(getCalculator().getCurrentBusinessDate().plusDays(1));
        }
    }
}
