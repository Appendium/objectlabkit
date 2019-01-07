package net.objectlab.kit.util.excel.gist;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import net.objectlab.kit.util.excel.ExcelStyle;
import net.objectlab.kit.util.excel.ExcelWorkbook;

public class ExcelInMemoryExample {
    public static final ExcelStyle HEADER_STYLE = ExcelStyle.builder().header().build();
    public static final ExcelStyle NUMERIC_STYLE = ExcelStyle.builder().numericFormat().build();
    public static final ExcelStyle BOLD_NUMERIC_STYLE = ExcelStyle.builder().numericFormat().bold().build();
    public static final ExcelStyle PERCENT_STYLE = ExcelStyle.builder().percentFormat().bold().build();

    public static void main(String[] args) throws IOException {
        ExcelWorkbook.newInMemoryWorkbook().newSheet("Countries").newRow().style(HEADER_STYLE) // HEADER style for entire row
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

                .row().sheet().skipRow().newRow().skipCol().newCell("Nice").style(ExcelStyle.builder().center()//
                        .backgroundColour(IndexedColors.ROSE)//
                        .backgroundFillPatternType(FillPatternType.BIG_SPOTS).build())//
                .newRow(7).newCell().newCell("Test").style(ExcelStyle.builder().underline().right().build())//
                .newRow(8).newCell().newCell("STRIKE").style(ExcelStyle.builder().strikeout().right().build())//
                .save("test.xlsx"); // Save it
    }
}
