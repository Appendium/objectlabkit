package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractExcelDateUtilTest;
import net.objectlab.kit.datecalc.common.Utils;

import org.joda.time.DateTime;

public class DateTimeExcelDateUtilTest extends AbstractExcelDateUtilTest<DateTime> {

    @Override
    protected DateTime createDate(final String str) {
        return new DateTime(Utils.createDate(str));
    }

    @Override
    protected DateTime createDateFromExcel(final double excelDate, final boolean use1904Windowing) {
        return JodaExcelDateUtil.getDateTime(excelDate, use1904Windowing);
    }
}
