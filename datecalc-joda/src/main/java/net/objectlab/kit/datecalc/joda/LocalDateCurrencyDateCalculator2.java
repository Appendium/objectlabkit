package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractCurrencyDateCalculatorOldFashion;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.ccy.CurrencyCalculatorConfig;

import org.joda.time.LocalDate;

@Deprecated
public class LocalDateCurrencyDateCalculator2 extends AbstractCurrencyDateCalculatorOldFashion<LocalDate> {
    public LocalDateCurrencyDateCalculator2(final String ccy1, final String ccy2, final DateCalculator<LocalDate> calculator,
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
