package net.objectlab.kit.util.excel;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

public class ExcelWorkbookTest {
    public static final ExcelStyle HEADER_STYLE = ExcelStyle.builder().header().build();
    public static final ExcelStyle NUMERIC_STYLE = ExcelStyle.builder().numericFormat().build();
    public static final ExcelStyle BOLD_NUMERIC_STYLE = ExcelStyle.builder().numericFormat().bold().build();
    public static final ExcelStyle PERCENT_STYLE = ExcelStyle.builder().percentFormat().bold().build();

    /**
     * 
     * 
    <table summary="Quick Example to generate this"> 
    <tr><th>Country</th><th>Population 2018</th><th>Percentage</th></tr>
    <tr><td>Belgium</td><td>11,000,000</td><td><b>15.28%</b></td></tr>
    <tr><td>UK</td><td>62,000,000</td><td><b>86.11%</b></td></tr>
    <tr><td><b><i>Total</i></b></td><td><b>72,000,000</b></td><td> </td></tr>
    </table>
     */
    @Test
    public void testSimple() throws IOException {
        ExcelWorkbook.newBook().newSheet("Countries").newRow().style(HEADER_STYLE) // HEADER style for entire row
                .newCell("Country").newCell("Population 2018").newCell("Percentage") //
                .newRow() // FIRST ROW
                .newCell("Belgium").newCell(11_000_000L).style(NUMERIC_STYLE)//
                .newCell(BigDecimal.valueOf(11_000_000d / 72_000_000d)).style(PERCENT_STYLE) //

                .newRow() // SECOND ROW
                .newCell("UK").newCell(62_000_000L).style(NUMERIC_STYLE) //
                .newCell(BigDecimal.valueOf(62_000_000d / 72_000_000d)).style(PERCENT_STYLE) //

                .newRow() // TOTAL
                .newCell("Total").style(ExcelStyle.builder().bold().italic().build()).newCell(72_000_000L).style(BOLD_NUMERIC_STYLE) //
                .autoSizeColumn(0, 1) //
                .save("test.xlsx"); // Save it

    }
}
