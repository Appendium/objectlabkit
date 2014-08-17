package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.TenorCode;
import net.objectlab.kit.datecalc.common.ccy.CurrencyCalculatorConfig;

import org.joda.time.LocalDate;

public class CurrencyLocalDateCalculatorOldFashion extends LocalDateCalculator {
    private final LocalDateCurrencyDateCalculator2 delegate;

    public CurrencyLocalDateCalculatorOldFashion(final String ccy1, final String ccy2, final CurrencyCalculatorConfig config) {
        setName(ccy1 + "." + ccy2);
        delegate = new LocalDateCurrencyDateCalculator2(ccy1, ccy2, this, config);
    }

    public CurrencyLocalDateCalculatorOldFashion setHolidayCalendars(final HolidayCalendar<LocalDate> ccy1HolidayCalendar,
            final HolidayCalendar<LocalDate> ccy2HolidayCalendar, final HolidayCalendar<LocalDate> usdHolidayCalendar) {
        delegate.setHolidayCalendars(ccy1HolidayCalendar, ccy2HolidayCalendar, usdHolidayCalendar);
        return this;
    }

    @Override
    protected void moveToSpotDate(final int spotLag) {
        delegate.moveToSpotDate(spotLag);
    }

    @Override
    public DateCalculator<LocalDate> setHolidayCalendar(final HolidayCalendar<LocalDate> calendar) {
        throw new UnsupportedOperationException("Sorry do not set a single calendar for a Currency calculator, use setHolidayCalendars...");
    }

    @Override
    public LocalDate setCurrentBusinessDate(final LocalDate date) {
        super.setCurrentBusinessDate(date);
        delegate.applyCurrencyPairCalendars();
        return getCurrentBusinessDate();
    }

    @Override
    protected DateCalculator<LocalDate> applyTenor(final TenorCode tenorCode, final int unit) {
        final DateCalculator<LocalDate> d = super.applyTenor(tenorCode, unit);
        delegate.applyAllCcyCalendars();
        return d;
    }
}
