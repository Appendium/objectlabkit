package net.objectlab.kit.datecalc.jdk;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.objectlab.kit.datecalc.common.AbstractIMMDateCalculator;
import net.objectlab.kit.datecalc.common.IMMPeriod;
import net.objectlab.kit.datecalc.common.Utils;

public class JdkDateIMMDateCalculator extends AbstractIMMDateCalculator<Date> {

    private static final JdkCalendarIMMDateCalculator DELEGATE = new JdkCalendarIMMDateCalculator();

    @Override
    protected Date getNextIMMDate(boolean requestNextIMM, Date theStartDate, IMMPeriod period) {
        return DELEGATE.getNextIMMDate(requestNextIMM, Utils.getCal(theStartDate), period).getTime();
    }

    public List<Date> getIMMDates(Date start, Date end, IMMPeriod period) {
        return buildList(DELEGATE.getIMMDates(Utils.getCal(start), Utils.getCal(end), period));
    }

    public boolean isIMMDate(Date date) {
        return DELEGATE.isIMMDate(Utils.getCal(date));
    }

    private List<Date> buildList(List<Calendar> dates) {
        List<Date> imms = new ArrayList<Date>();
        for (Calendar date : dates) {
            imms.add(date.getTime());
        }
        return imms;
    }
}
