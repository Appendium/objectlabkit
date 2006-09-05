package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractExcelDateUtilTest;

import org.joda.time.LocalDate;

public class LocalDateExcelDateUtilTest extends AbstractExcelDateUtilTest<LocalDate> {

    @Override
    protected LocalDate createDate(final String str) {
        return new LocalDate(str);
    }

    @Override
    protected LocalDate createDateFromExcel(final double excelDate) {
        return JodaExcelDateUtil.getLocalDate(excelDate, false);
    }
}
