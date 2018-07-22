package net.objectlab.kit.util.excel;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

public class ExcelWorkbookTest {
    /**
     * 
     * 
    <table summary="Quick Example to generate this"> 
    <tr><th>Country</th><th>Population 2018</th><th>Percentage</th></tr>
    <tr><td>Belgium</td><td>11,000,000</td><td>15.28%</td></tr>
    <tr><td>UK</td><td>62,000,000</td><td>86.11%</td></tr>
    <tr><td><b>Total</b></td><td><b>72,000,000</b></td><td> </td></tr>
    </table>
     */
    @Test
    public void testSimple() throws IOException {
        ExcelWorkbook.newBook().newSheet("Countries").newRow() // HEADER
                .newCell("Country").header().newCell("Population 2018").header().newCell("Percentage").header() //
                .newRow() // FIRST ROW
                .newCell("Belgium").newCell(11_000_000L).numericFormat()//
                .newCell(BigDecimal.valueOf(11_000_000d / 72_000_000d)).percentFormat() //

                .newRow() // SECOND ROW
                .newCell("UK").newCell(62_000_000L).numericFormat() //
                .newCell(BigDecimal.valueOf(62_000_000d / 72_000_000d)).percentFormat() //

                .newRow() // TOTAL
                .newCell("Total").bold().newCell(72_000_000L).numericFormat().bold() //
                .autoSizeColumn(0, 1) //
                .save("test.xlsx"); // Save it

    }
}
