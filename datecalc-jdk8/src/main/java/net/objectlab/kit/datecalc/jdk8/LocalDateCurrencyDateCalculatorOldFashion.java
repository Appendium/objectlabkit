package net.objectlab.kit.datecalc.jdk8;

import java.time.LocalDate;

import net.objectlab.kit.datecalc.common.AbstractCurrencyDateCalculatorOldFashion;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.ccy.CurrencyCalculatorConfig;

@Deprecated
public class LocalDateCurrencyDateCalculatorOldFashion extends AbstractCurrencyDateCalculatorOldFashion<LocalDate> {
    public LocalDateCurrencyDateCalculatorOldFashion(final String ccy1, final String ccy2, final DateCalculator<LocalDate> calculator,
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
