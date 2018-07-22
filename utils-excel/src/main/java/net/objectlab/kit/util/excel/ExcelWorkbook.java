package net.objectlab.kit.util.excel;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Wrapper for the POI Workbook, allowing creation of simple spreadsheet in a fluent manner.
 * 
 * The default format is opinionated.
 * 
 * <pre>
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
 * </pre>
 * @author Benoit Xhenseval
 */
public class ExcelWorkbook {
    private XSSFWorkbook workbook;
    private CellStyle wrapStyle;
    private CellStyle headerStyle;
    private CellStyle boldStyle;

    public ExcelWorkbook() {
        workbook = new XSSFWorkbook();
        wrapStyle = workbook.createCellStyle();
        wrapStyle.setWrapText(true);

        boldStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldStyle.setFont(boldFont);

        Font fontHeader = workbook.createFont();
        fontHeader.setBold(true);
        headerStyle = workbook.createCellStyle();
        headerStyle.setFont(fontHeader);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    public static ExcelWorkbook newBook() {
        return new ExcelWorkbook();
    }

    public ExcelSheet newSheet(String name) {
        return new ExcelSheet(workbook.createSheet(name), this);
    }

    public CellStyle wrapStyle() {
        return wrapStyle;
    }

    public CellStyle headerStyle() {
        return headerStyle;
    }

    public CellStyle boldStyle() {
        return boldStyle;
    }

    public Workbook poiWorkbook() {
        return workbook;
    }

    public ExcelWorkbook save(String fileName) throws IOException {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            workbook.write(out);
        }
        return this;
    }

    public Font createFont() {
        return workbook.createFont();
    }

}
