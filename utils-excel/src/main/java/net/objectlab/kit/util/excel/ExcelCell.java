package net.objectlab.kit.util.excel;

import java.io.IOException;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ExcelCell {
    private ExcelRow row;
    private XSSFCell currentCell;

    public ExcelCell(XSSFCell cell, ExcelRow row) {
        currentCell = cell;
        this.row = row;
    }

    public ExcelCell value(String value) {
        currentCell.setCellValue(value);
        return this;
    }

    public ExcelRow row() {
        return row;
    }

    public int rowIndex() {
        return row.rowIndex();
    }

    public ExcelCell newCell(String value) {
        return row.newCell(value);
    }

    public ExcelCell newCell(long value) {
        return row.newCell(value);
    }

    public ExcelCell newCell(Number value) {
        return row.newCell(value);
    }

    public ExcelRow newRow() {
        return row.newRow();
    }

    public ExcelSheet newSheet(String title) {
        return row.newSheet(title);
    }

    public ExcelCell value(long value) {
        currentCell.setCellValue(value);
        currentCell.setCellType(CellType.NUMERIC);
        return this;
    }

    public ExcelCell value(Number value) {
        currentCell.setCellValue(value != null ? value.doubleValue() : 0d);
        currentCell.setCellType(CellType.NUMERIC);
        return this;
    }

    public CellStyle cloneStyle() {
        final CellStyle cellStyle = poiWorkbook().createCellStyle();
        cellStyle.cloneStyleFrom(currentCell.getCellStyle());
        return cellStyle;
    }

    public ExcelCell setStyle(CellStyle style) {
        currentCell.setCellStyle(style);
        return this;
    }

    public ExcelCell style(ExcelStyle style) {
        if (style != null) {
            setStyle(style.build(this));
        }
        return this;
    }

    public Optional<CellStyle> findStyle(int styleHashcode) {
        return row.sheet().workbook().findStyle(styleHashcode);
    }

    public CellStyle cloneStyle(int styleHashcode) {
        return row.sheet().workbook().cloneStyle(styleHashcode);
    }

    public ExcelRow newRow(int rowIdx) {
        return row.newRow(rowIdx);
    }

    public ExcelWorkbook workbook() {
        return row.workbook();
    }

    public ExcelCell save(String fileName) throws IOException {
        row.save(fileName);
        return this;
    }

    public ExcelCell autoSizeColumn(int startCol, int endCol) {
        row.autoSizeColumn(startCol, endCol);
        return this;
    }

    public Cell poiCell() {
        return currentCell;
    }

    public Row poiRow() {
        return currentCell.getRow();
    }

    public Workbook poiWorkbook() {
        return row.poiWorkbook();
    }
}
