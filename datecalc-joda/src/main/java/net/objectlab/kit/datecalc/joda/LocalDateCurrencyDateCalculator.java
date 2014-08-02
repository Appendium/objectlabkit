package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractCurrencyDateCalculator;
import net.objectlab.kit.datecalc.common.DateCalculator;

import org.joda.time.LocalDate;

public class LocalDateCurrencyDateCalculator extends AbstractCurrencyDateCalculator<LocalDate> {
    public LocalDateCurrencyDateCalculator(String ccy1, String ccy2, DateCalculator<LocalDate> calculator) {
        super(ccy1, ccy2, calculator);
    }

    @Override
    protected void moveToNextWeekday() {
        getCalculator().setCurrentBusinessDate(getCalculator().getCurrentBusinessDate().plusDays(1));
        while (getCalculator().isWeekend(getCalculator().getCurrentBusinessDate())) {
            getCalculator().setCurrentBusinessDate(getCalculator().getCurrentBusinessDate().plusDays(1));
        }
    }
}
