package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static Calendar getCal(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    
}
