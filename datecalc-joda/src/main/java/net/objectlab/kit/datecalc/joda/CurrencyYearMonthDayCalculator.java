package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.CurrencyCalculatorConfig;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.TenorCode;

import org.joda.time.YearMonthDay;

public class CurrencyYearMonthDayCalculator extends YearMonthDayDateCalculator {
    private final YearMonthDayCurrencyDateCalculator delegate;

    public CurrencyYearMonthDayCalculator(final String ccy1, final String ccy2, final CurrencyCalculatorConfig config) {
        setName(ccy1 + "." + ccy2);
        delegate = new YearMonthDayCurrencyDateCalculator(ccy1, ccy2, this, config);
    }

    public CurrencyYearMonthDayCalculator setHolidayCalendars(final HolidayCalendar<YearMonthDay> ccy1HolidayCalendar,
            final HolidayCalendar<YearMonthDay> ccy2HolidayCalendar, final HolidayCalendar<YearMonthDay> usdHolidayCalendar) {
        delegate.setHolidayCalendars(ccy1HolidayCalendar, ccy2HolidayCalendar, usdHolidayCalendar);
        return this;
    }

    @Override
    protected void moveToSpotDate(final int spotLag) {
        delegate.moveToSpotDate(spotLag);
    }

    @Override
    public DateCalculator<YearMonthDay> setHolidayCalendar(final HolidayCalendar<YearMonthDay> calendar) {
        throw new UnsupportedOperationException("Sorry do not set a single calendar for a Currency calculator, use setHolidayCalendars...");
    }

    @Override
    public YearMonthDay setCurrentBusinessDate(final YearMonthDay date) {
        super.setCurrentBusinessDate(date);
        delegate.applyCurrencyPairCalendars();
        return getCurrentBusinessDate();
    }

    @Override
    protected DateCalculator<YearMonthDay> applyTenor(final TenorCode tenorCode, final int unit) {
        final DateCalculator<YearMonthDay> d = super.applyTenor(tenorCode, unit);
        delegate.applyAllCcyCalendars();
        return d;
    }
}
