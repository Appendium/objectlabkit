package net.objectlab.kit.datecalc.common;

import java.util.Date;

public class DateExcelDateUtilTest extends AbstractExcelDateUtilTest<Date> {

    @Override
    protected Date createDate(final String str) {
        return Utils.createDate(str);
    }

    @Override
    protected Date createDateFromExcel(final double excelDate, final boolean use1904Windowing) {
        return ExcelDateUtil.getJavaDateOnly(excelDate, use1904Windowing);
    }
}
