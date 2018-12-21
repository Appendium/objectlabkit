package net.objectlab.kit.util.excel;

import java.io.IOException;
import java.time.temporal.Temporal;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelRow {
    private ExcelSheet sheet;
    private Row currentRow;
    private int currentCol;
    private ExcelStyle style;

    public ExcelRow(Row row, ExcelSheet sheet) {
        this.sheet = sheet;
        currentRow = row;
    }

    public ExcelCell newCell() {
        return new ExcelCell(currentRow.createCell(currentCol++), this).style(style);
    }

    public ExcelCell newCell(String value) {
        return new ExcelCell(currentRow.createCell(currentCol++), this).value(value).style(style);
    }

    public ExcelCell newCell(Temporal value) {
        return new ExcelCell(currentRow.createCell(currentCol++), this).value(value).style(style);
    }

    public ExcelCell newCellFormula(String formula) {
        return new ExcelCell(currentRow.createCell(currentCol++), this).formula(formula).style(style);
    }

    public ExcelCell newCell(long value) {
        return new ExcelCell(currentRow.createCell(currentCol++), this).value(value).style(style);
    }

    public ExcelCell newCell(Number value) {
        return new ExcelCell(currentRow.createCell(currentCol++), this).value(value).style(style);
    }

    public ExcelRow skipCol() {
        currentCol++;
        return this;
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

    public Workbook poiWorkbook() {
        return sheet.workbook().poiWorkbook();
    }

    public ExcelRow save(String fileName) throws IOException {
        sheet.save(fileName);
        return this;
    }

    public ExcelRow autoSizeColumn(int startCol, int endCol) {
        sheet.autoSizeColumn(startCol, endCol);
        return this;
    }

    public ExcelRow style(ExcelStyle style) {
        this.style = style;
        return this;
    }
}
