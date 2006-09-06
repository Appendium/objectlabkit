package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractExcelDateUtilTest;

import org.joda.time.YearMonthDay;

public class YearMonthDayExcelDateUtilTest extends AbstractExcelDateUtilTest<YearMonthDay> {

    @Override
    protected YearMonthDay createDate(final String str) {
        return new YearMonthDay(str);
    }

    @Override
    protected YearMonthDay createDateFromExcel(final double excelDate, final boolean use1904Windowing) {
        return JodaExcelDateUtil.getYearMonthDay(excelDate, use1904Windowing);
    }
}
