package net.objectlab.kit.datecalc.common;

import java.util.Calendar;

public class CalendarExcelDateUtilTest extends AbstractExcelDateUtilTest<Calendar> {

    @Override
    protected Calendar createDate(final String str) {
        return Utils.createCalendar(str);
    }

    @Override
    protected Calendar createDateFromExcel(final double excelDate, final boolean use1904Windowing) {
        return ExcelDateUtil.getJavaCalendar(excelDate, use1904Windowing);
    }
}
