package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.TenorCode;

public class CurrencyCalendarDateCalculator extends CalendarDateCalculator {
    private final CalendarDateCurrencyDateCalculator delegate;

    public CurrencyCalendarDateCalculator(final String ccy1, final String ccy2) {
        setName(ccy1 + "." + ccy2);
        delegate = new CalendarDateCurrencyDateCalculator(ccy1, ccy2, this);
    }

    public CurrencyCalendarDateCalculator setHolidayCalendars(final HolidayCalendar<Calendar> ccy1HolidayCalendar,
            final HolidayCalendar<Calendar> ccy2HolidayCalendar, final HolidayCalendar<Calendar> usdHolidayCalendar) {
        delegate.setHolidayCalendars(ccy1HolidayCalendar, ccy2HolidayCalendar, usdHolidayCalendar);
        return this;
    }

    @Override
    protected void moveToSpotDate(int spotLag) {
        delegate.moveToSpotDate(spotLag);
    }

    @Override
    public DateCalculator<Calendar> setHolidayCalendar(HolidayCalendar<Calendar> calendar) {
        throw new UnsupportedOperationException("Sorry do not set a single calendar for a Currency calculator, use setHolidayCalendars...");
    }

    @Override
    public Calendar setCurrentBusinessDate(final Calendar date) {
        super.setCurrentBusinessDate(date);
        delegate.applyCurrencyPairCalendars();
        return getCurrentBusinessDate();
    }

    @Override
    protected DateCalculator<Calendar> applyTenor(TenorCode tenorCode, int unit) {
        final DateCalculator<Calendar> d = super.applyTenor(tenorCode, unit);
        delegate.applyAllCcyCalendars();
        return d;
    }
}
