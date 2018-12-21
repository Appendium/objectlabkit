package net.objectlab.kit.util.excel;

import java.io.IOException;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelSheet {
    private int currentRow;
    private ExcelWorkbook workbook;
    private Sheet currentSheet;

    public ExcelSheet(XSSFSheet sheet, ExcelWorkbook book) {
        workbook = book;
        currentSheet = sheet;
    }

    public ExcelSheet(SXSSFSheet sheet, ExcelWorkbook book) {
        workbook = book;
        currentSheet = sheet;
    }

    public ExcelRow newRow() {
        return new ExcelRow(currentSheet.createRow(currentRow++), this);
    }

    public ExcelRow newRow(int rowIdx) {
        currentRow = rowIdx;
        return new ExcelRow(currentSheet.createRow(currentRow++), this);
    }

    public Sheet sheet() {
        return currentSheet;
    }

    public Workbook poiWorkbook() {
        return workbook.poiWorkbook();
    }

    public ExcelSheet skipRow() {
        currentRow++;
        return this;
    }

    public int rowIndex() {
        return currentRow;
    }

    public ExcelSheet newSheet(String title) {
        return workbook.newSheet(title);
    }

    public ExcelSheet autoSizeColumn(int startCol, int endCol) {
        if (currentSheet instanceof SXSSFSheet) {
            ((SXSSFSheet) currentSheet).trackAllColumnsForAutoSizing();
        }
        IntStream.rangeClosed(startCol, endCol).forEach(i -> currentSheet.autoSizeColumn(i));
        return this;
    }

    public ExcelSheet mergeRegion(int startRow, int endRow, int startCol, int endCol) {
        currentSheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startCol, endCol));
        return this;
    }

    public ExcelSheet tabColour(IndexedColors colour) {
        if (sheet() instanceof XSSFSheet) {
            ((XSSFSheet) sheet()).setTabColor(new XSSFColor(colour, new DefaultIndexedColorMap()));
        } else if (sheet() instanceof SXSSFSheet) {
            ((SXSSFSheet) sheet()).setTabColor(new XSSFColor(colour, new DefaultIndexedColorMap()));
        }
        return this;
    }

    public ExcelWorkbook workbook() {
        return workbook;
    }

    public ExcelSheet save(String fileName) throws IOException {
        workbook.save(fileName);
        return this;
    }

    public Sheet poiSheet() {
        return currentSheet;
    }
}
