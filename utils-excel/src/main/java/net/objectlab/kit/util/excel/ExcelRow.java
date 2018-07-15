package net.objectlab.kit.util.excel;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class ExcelRow {
    private ExcelSheet sheet;
    private XSSFRow currentRow;
    private int currentCol;

    public ExcelRow(XSSFRow row, ExcelSheet sheet) {
        this.sheet = sheet;
        currentRow = row;
    }

    public ExcelCell newCell() {
        return new ExcelCell(currentRow.createCell(currentCol++), this);
    }

    public ExcelCell newCell(String value) {
        return new ExcelCell(currentRow.createCell(currentCol++), this).value(value);
    }

    public ExcelCell newCell(long value) {
        return new ExcelCell(currentRow.createCell(currentCol++), this).value(value);
    }

    public ExcelCell newCell(Number value) {
        return new ExcelCell(currentRow.createCell(currentCol++), this).value(value);
    }

    public ExcelRow newRow() {
        return sheet.newRow();
    }

    public ExcelRow newRow(int rowIdx) {
        return sheet.newRow(rowIdx);
    }

    public ExcelSheet newSheet(String title) {
        return sheet.newSheet(title);
    }

    public Row poiRow() {
        return currentRow;
    }

    public int rowIndex() {
        return sheet.rowIndex();
    }

    public int colIndex() {
        return currentCol;
    }

    public ExcelSheet sheet() {
        return sheet;
    }

    public ExcelWorkbook workbook() {
        return sheet.workbook();
    }

    public ExcelRow save(String fileName) throws IOException {
        sheet.save(fileName);
        return this;
    }

    public ExcelRow autoSizeColumn(int startCol, int endCol) {
        sheet.autoSizeColumn(startCol, endCol);
        return this;
    }
}
