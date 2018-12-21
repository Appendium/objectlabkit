package net.objectlab.kit.util.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Wrapper for the POI Workbook, allowing creation of simple spreadsheet in a fluent manner.
 * 
 * The default format is opinionated.
 * 
 * <pre>{@code
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
 * }</pre>
 * @author Benoit Xhenseval
 */
public class ExcelWorkbook {
    private XSSFWorkbook xssfWorkbook;
    private SXSSFWorkbook sxssfWorkbook;
    private Map<Integer, CellStyle> existingStyles = new HashMap<>();

    public ExcelWorkbook(boolean streaming) {
        if (streaming) {
            sxssfWorkbook = new SXSSFWorkbook(100);
            sxssfWorkbook.setCompressTempFiles(true);

        } else {
            xssfWorkbook = new XSSFWorkbook();
        }
    }

    public static ExcelWorkbook newStreamingWorkbook() {
        return new ExcelWorkbook(true);
    }

    public static ExcelWorkbook newInMemoryWorkbook() {
        return new ExcelWorkbook(false);
    }

    public ExcelSheet newSheet(String name) {
        return sxssfWorkbook != null ? new ExcelSheet(sxssfWorkbook.createSheet(name), this) : new ExcelSheet(xssfWorkbook.createSheet(name), this);
    }

    public Workbook poiWorkbook() {
        return sxssfWorkbook != null ? sxssfWorkbook : xssfWorkbook;
    }

    public ExcelWorkbook dispose() {
        if (sxssfWorkbook != null) {
            sxssfWorkbook.dispose();
        }
        return this;
    }

    public ExcelWorkbook save(String fileName) throws IOException {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            if (sxssfWorkbook != null) {
                sxssfWorkbook.write(out);
            } else {
                xssfWorkbook.write(out);
            }
        }
        return this;
    }

    public Font createFont() {
        return sxssfWorkbook != null ? sxssfWorkbook.createFont() : xssfWorkbook.createFont();
    }

    public Optional<CellStyle> findStyle(int styleHashCode) {
        return Optional.ofNullable(existingStyles.get(styleHashCode));
    }

    public CellStyle cloneStyle(int styleHashCode) {
        final CellStyle cellStyle = sxssfWorkbook != null ? sxssfWorkbook.createCellStyle() : xssfWorkbook.createCellStyle();
        existingStyles.put(styleHashCode, cellStyle);
        return cellStyle;
    }

}
