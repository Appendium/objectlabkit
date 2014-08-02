package net.objectlab.kit.datecalc.jdk;

import java.util.Date;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.TenorCode;

public class CurrencyDateDateCalculator extends DateDateCalculator {
    private final DateDateCurrencyDateCalculator delegate;

    public CurrencyDateDateCalculator(final String ccy1, final String ccy2) {
        setName(ccy1 + "." + ccy2);
        delegate = new DateDateCurrencyDateCalculator(ccy1, ccy2, this);
    }

    public CurrencyDateDateCalculator setHolidayCalendars(final HolidayCalendar<Date> ccy1HolidayCalendar,
            final HolidayCalendar<Date> ccy2HolidayCalendar, final HolidayCalendar<Date> usdHolidayCalendar) {
        delegate.setHolidayCalendars(ccy1HolidayCalendar, ccy2HolidayCalendar, usdHolidayCalendar);
        return this;
    }

    @Override
    protected void moveToSpotDate(int spotLag) {
        delegate.moveToSpotDate(spotLag);
    }

    @Override
    public DateCalculator<Date> setHolidayCalendar(HolidayCalendar<Date> calendar) {
        throw new UnsupportedOperationException("Sorry do not set a single calendar for a Currency calculator, use setHolidayCalendars...");
    }

    @Override
    public Date setCurrentBusinessDate(final Date date) {
        super.setCurrentBusinessDate(date);
        if (delegate != null) {
            delegate.applyCurrencyPairCalendars();
        }
        return getCurrentBusinessDate();
    }

    @Override
    protected DateCalculator<Date> applyTenor(TenorCode tenorCode, int unit) {
        final DateCalculator<Date> d = super.applyTenor(tenorCode, unit);
        delegate.applyAllCcyCalendars();
        return d;
    }
}
