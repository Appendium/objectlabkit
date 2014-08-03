package net.objectlab.kit.datecalc.jdk8;

import java.time.LocalDate;

import net.objectlab.kit.datecalc.common.AbstractCurrencyDateCalculator;
import net.objectlab.kit.datecalc.common.CurrencyCalculatorConfig;
import net.objectlab.kit.datecalc.common.DateCalculator;

public class LocalDateCurrencyDateCalculator extends AbstractCurrencyDateCalculator<LocalDate> {
    public LocalDateCurrencyDateCalculator(final String ccy1, final String ccy2, final DateCalculator<LocalDate> calculator,
            final CurrencyCalculatorConfig config) {
        super(ccy1, ccy2, calculator, config);
    }

    @Override
    protected void moveToNextWeekday() {
        getCalculator().forceCurrentDateNoAdjustment(getCalculator().getCurrentBusinessDate().plusDays(1));
        while (getCalculator().isWeekend(getCalculator().getCurrentBusinessDate())) {
            getCalculator().forceCurrentDateNoAdjustment(getCalculator().getCurrentBusinessDate().plusDays(1));
        }
    }
}
