package net.objectlab.kit.datecalc.common;

import junit.framework.TestCase;

public abstract class AbstractExcelDateUtilTest<E> extends TestCase {
    protected abstract E createDate(final String str);

    protected abstract E createDateFromExcel(double excelDate);

    public void testExcelDate() {
        checkDate(createDate("1899-12-31"), 0);
        checkDate(createDate("1900-01-01"), 1.0);
        checkDate(createDate("1900-03-01"), 61.0);
        checkDate(createDate("1968-06-11"), 25000.0);
        checkDate(createDate("1978-05-31"), 28641.00);
        checkDate(createDate("1999-12-31"), 36525.00);
        checkDate(createDate("2000-01-01"), 36526.00);
        checkDate(createDate("2000-01-01"), 36526.00);
        checkDate(createDate("2000-02-28"), 36584.00);
        checkDate(createDate("2000-02-29"), 36585.00);
        checkDate(createDate("2000-03-01"), 36586.00);
        checkDate(null, -1.0);
    }

    private void checkDate(final E date, final double excelDate) {
        assertEquals("excel:" + excelDate, date, createDateFromExcel(excelDate));
    }
}
