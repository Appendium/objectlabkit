package net.objectlab.kit.datecalc.common;

import junit.framework.TestCase;

public abstract class AbstractExcelDateUtilTest<E> extends TestCase {
    protected abstract E createDate(final String str);

    protected abstract E createDateFromExcel(double excelDate, boolean use1904windowing);

    public void testExcelDate() {
        checkDate(createDate("1899-12-31"), 0, false);
        checkDate(createDate("1900-01-01"), 1.0, false);
        checkDate(createDate("1900-03-01"), 61.0, false);
        checkDate(createDate("1968-06-11"), 25000.0, false);
        checkDate(createDate("1978-05-31"), 28641.00, false);
        checkDate(createDate("1999-12-31"), 36525.00, false);
        checkDate(createDate("2000-01-01"), 36526.00, false);
        checkDate(createDate("2000-01-01"), 36526.00, false);
        checkDate(createDate("2000-02-28"), 36584.00, false);
        checkDate(createDate("2000-02-29"), 36585.00, false);
        checkDate(createDate("2000-03-01"), 36586.00, false);
        checkDate(null, -1.0, false);
    }

    public void testExcelDateUsing1904Windowing() {
        checkDate(createDate("1904-01-01"), 0.0, true);
        checkDate(createDate("1904-01-02"), 1.0, true);
        checkDate(createDate("1904-03-02"), 61.0, true);
        checkDate(null, -1.0, false);
    }

    private void checkDate(final E date, final double excelDate, final boolean use1904windowing) {
        assertEquals("excel:" + excelDate, date, createDateFromExcel(excelDate, use1904windowing));
    }
}
