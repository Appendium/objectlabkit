package net.objectlab.kit.datecalc.joda;

import java.util.ArrayList;
import java.util.List;

import net.objectlab.kit.datecalc.common.AbstractIMMDateCalculator;
import net.objectlab.kit.datecalc.common.IMMPeriod;

import org.joda.time.LocalDate;
import org.joda.time.YearMonthDay;

public class YearMonthDayIMMDateCalculator extends AbstractIMMDateCalculator<YearMonthDay> {

    private static final LocalDateIMMDateCalculator DELEGATE = new LocalDateIMMDateCalculator();

    public boolean isIMMDate(YearMonthDay date) {
        return DELEGATE.isIMMDate(date.toLocalDate());
    }

    @Override
    protected YearMonthDay getNextIMMDate(boolean requestNextIMM, YearMonthDay theStartDate, IMMPeriod period) {
        return new YearMonthDay(DELEGATE.getNextIMMDate(requestNextIMM, theStartDate.toLocalDate(), period));
    }

    public List<YearMonthDay> getIMMDates(YearMonthDay start, YearMonthDay end, IMMPeriod period) {
        return buildList(DELEGATE.getIMMDates(start.toLocalDate(), end.toLocalDate(), period));
    }

    private List<YearMonthDay> buildList(List<LocalDate> dates) {
        List<YearMonthDay> imms = new ArrayList<YearMonthDay>();
        for (LocalDate date : dates) {
            imms.add(new YearMonthDay(date));
        }
        return imms;
    }
}
