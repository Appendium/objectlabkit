package net.objectlab.kit.util.excel;


import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author Marcin Jekot
 * @since 2012/08/17
 */
public class ExcelTest {

    private Excel xls;
    private Excel xlsx;

    @Before
    public void setUp() {
        xls = new Excel(getClass().getResourceAsStream("Test Workbook.xls"));
        xlsx = new Excel(getClass().getResourceAsStream("Test Workbook.xlsx"));
    }

    @Test
    public void shouldReadSingleCell() {
        // When
        String result = xlsx.readValueAt("'Test Sheet'!C3", String.class);

        // Then
        assertEquals("TestA", result);
    }

    @Test
    public void shouldReadColumn() {
        // When
        List<String> result = xlsx.readColumn("'Test Sheet'!C3", String.class);

        // Then
        assertEquals(9, result.size());
        assertEquals("TestA", result.get(0));
        assertEquals("TestI", result.get(8));
    }

    @Test
    public void shouldReadTwoLinesAndStop() {
        // When
        Object[][] result = xlsx.readBlock("'Test Sheet'!C3:F4", String.class, Integer.class);

        // Then
        assertEquals("Number of rows read should be 2", 2, result.length);
        assertEquals("Number of columns read should be 4", 4, result[0].length);
        assertEquals("Number of columns read should be 4", 4, result[1].length);
    }

    @Test
    public void shouldReadWholeBlockGivenTopLine() {
        // When
        Object[][] result = xlsx.readBlock("'Test Sheet'!C3:F3", String.class, Integer.class);

        // Then
        assertEquals("Number of rows read should be 9", 9, result.length);
        assertEquals("Number of columns read should be 4", 4, result[0].length);
        assertEquals("Number of columns read should be 4", 4, result[8].length);
    }

    @Test
    public void shouldReadARangeOfCellsInXls() {
        shouldReadARangeOfCells(xls);
    }

    @Test
    public void shouldReadARangeOfCellsInXlx() {
        shouldReadARangeOfCells(xlsx);
    }

    private void shouldReadARangeOfCells(Excel xl) {
        // When
        Object[][] result = xl.readBlock("'Test Sheet'!C3:F12", String.class, Integer.class, Double.class, BigDecimal.class);

        // Then
        final int numberOfRows = 9;
        assertEquals(numberOfRows, result.length);

        for (int i = 1; i <= numberOfRows; i++) {

            Object[] data = result[i - 1];
            assertEquals("Test" + Character.toString((char) ('A' + i - 1)), data[0]);
            assertEquals(i, data[1]);

            final int decimal = i % 10;
            assertEquals(i + decimal / (double) 10, data[2]);
            assertEquals(new BigDecimal("" + i + "." + decimal + decimal), data[3]);

            assertEquals(4, data.length);
        }
    }
}
