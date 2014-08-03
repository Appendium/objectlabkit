package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractCurrencyDateCalculator;
import net.objectlab.kit.datecalc.common.CurrencyCalculatorConfig;
import net.objectlab.kit.datecalc.common.DateCalculator;

import org.joda.time.YearMonthDay;

public class YearMonthDayCurrencyDateCalculator extends AbstractCurrencyDateCalculator<YearMonthDay> {
    public YearMonthDayCurrencyDateCalculator(final String ccy1, final String ccy2, final DateCalculator<YearMonthDay> calculator,
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
