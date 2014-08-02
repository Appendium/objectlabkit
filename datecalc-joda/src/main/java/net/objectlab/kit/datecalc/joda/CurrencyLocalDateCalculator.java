package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.TenorCode;

import org.joda.time.LocalDate;

public class CurrencyLocalDateCalculator extends LocalDateCalculator {
    private final LocalDateCurrencyDateCalculator delegate;

    public CurrencyLocalDateCalculator(final String ccy1, final String ccy2) {
        setName(ccy1 + "." + ccy2);
        delegate = new LocalDateCurrencyDateCalculator(ccy1, ccy2, this);
    }

    public CurrencyLocalDateCalculator setHolidayCalendars(final HolidayCalendar<LocalDate> ccy1HolidayCalendar,
            final HolidayCalendar<LocalDate> ccy2HolidayCalendar, final HolidayCalendar<LocalDate> usdHolidayCalendar) {
        delegate.setHolidayCalendars(ccy1HolidayCalendar, ccy2HolidayCalendar, usdHolidayCalendar);
        return this;
    }

    @Override
    protected void moveToSpotDate(int spotLag) {
        delegate.moveToSpotDate(spotLag);
    }

    @Override
    public DateCalculator<LocalDate> setHolidayCalendar(HolidayCalendar<LocalDate> calendar) {
        throw new UnsupportedOperationException("Sorry do not set a single calendar for a Currency calculator, use setHolidayCalendars...");
    }

    @Override
    public LocalDate setCurrentBusinessDate(final LocalDate date) {
        super.setCurrentBusinessDate(date);
        delegate.applyCurrencyPairCalendars();
        return getCurrentBusinessDate();
    }

    @Override
    protected DateCalculator<LocalDate> applyTenor(TenorCode tenorCode, int unit) {
        final DateCalculator<LocalDate> d = super.applyTenor(tenorCode, unit);
        delegate.applyAllCcyCalendars();
        return d;
    }
}
