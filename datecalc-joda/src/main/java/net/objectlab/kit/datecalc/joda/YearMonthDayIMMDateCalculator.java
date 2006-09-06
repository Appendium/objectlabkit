package net.objectlab.kit.datecalc.joda;

import java.util.ArrayList;
import java.util.List;

import net.objectlab.kit.datecalc.common.AbstractIMMDateCalculator;
import net.objectlab.kit.datecalc.common.IMMPeriod;

import org.joda.time.LocalDate;
import org.joda.time.YearMonthDay;

public class YearMonthDayIMMDateCalculator extends AbstractIMMDateCalculator<YearMonthDay> {

    private static final LocalDateIMMDateCalculator DELEGATE = new LocalDateIMMDateCalculator();

    public boolean isIMMDate(final YearMonthDay date) {
        return DELEGATE.isIMMDate(date.toLocalDate());
    }

    @Override
    protected YearMonthDay getNextIMMDate(final boolean requestNextIMM, final YearMonthDay theStartDate, final IMMPeriod period) {
        return new YearMonthDay(DELEGATE.getNextIMMDate(requestNextIMM, theStartDate.toLocalDate(), period));
    }

    public List<YearMonthDay> getIMMDates(final YearMonthDay start, final YearMonthDay end, final IMMPeriod period) {
        return buildList(DELEGATE.getIMMDates(start.toLocalDate(), end.toLocalDate(), period));
    }

    private List<YearMonthDay> buildList(final List<LocalDate> dates) {
        final List<YearMonthDay> imms = new ArrayList<YearMonthDay>();
        for (final LocalDate date : dates) {
            imms.add(new YearMonthDay(date));
        }
        return imms;
    }
}
