package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;
import java.util.Date;

import net.objectlab.kit.datecalc.common.HolidayHandlerType;

public class ForwardHandler implements HolidayHandler {

    public Date moveCurrentDate(DateCalculator calendar) {
        
        Calendar cal = Utils.getCal(calendar.getCurrentDate());

        do {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        } while (calendar.isCurrentDateNonWorking());
        
        return cal.getTime();
    }

    public String getType() {
        return HolidayHandlerType.FORWARD;
    }

}
