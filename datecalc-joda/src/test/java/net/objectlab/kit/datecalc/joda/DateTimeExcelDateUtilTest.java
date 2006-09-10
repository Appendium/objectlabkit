package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractExcelDateUtilTest;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class DateTimeExcelDateUtilTest extends AbstractExcelDateUtilTest<DateTime> {

    @Override
    protected DateTime createDate(final String str) {
        return new DateTime(str);
    }

    @Override
    protected DateTime createDateFromExcel(final double excelDate, final boolean use1904Windowing) {
        return JodaExcelDateUtil.getDateTime(excelDate, use1904Windowing);
    }

    public void testPlusMonths() {
        final LocalDate d = new LocalDate("2008-01-31");
        final LocalDate d2 = d.plusMonths(1);
        System.out.println("======================> " + d2);
    }
}
