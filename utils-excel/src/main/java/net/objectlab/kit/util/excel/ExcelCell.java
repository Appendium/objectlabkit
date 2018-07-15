package net.objectlab.kit.util.excel;

import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
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

    public CellStyle newStyle() {
        return row().sheet().workbook().poiWorkbook().createCellStyle();
    }

    public DataFormat newDataFormat() {
        return workbook().poiWorkbook().createDataFormat();
    }

    public ExcelCell percentFormat() {
        CellStyle cellStyle = newStyle();
        cellStyle.cloneStyleFrom(currentCell.getCellStyle());
        DataFormat format = newDataFormat();
        cellStyle.setDataFormat(format.getFormat("#,###,###,##0.00%"));
        currentCell.setCellStyle(cellStyle);
        return this;
    }

    public ExcelCell numericFormat() {
        CellStyle cellStyle = newStyle();
        cellStyle.cloneStyleFrom(currentCell.getCellStyle());
        DataFormat format = newDataFormat();
        cellStyle.setDataFormat(format.getFormat("#,###,###,###"));
        currentCell.setCellStyle(cellStyle);
        return this;
    }

    public ExcelCell bold() {
        CellStyle cellStyle = newStyle();
        cellStyle.cloneStyleFrom(currentCell.getCellStyle());
        Font boldFont = workbook().createFont();
        boldFont.setBold(true);
        cellStyle.setFont(boldFont);
        currentCell.setCellStyle(cellStyle);
        return this;
    }

    public ExcelCell background(IndexedColors color) {
        CellStyle cellStyle = newStyle();
        cellStyle.cloneStyleFrom(currentCell.getCellStyle());
        cellStyle.setFillBackgroundColor(color.getIndex());
        currentCell.setCellStyle(cellStyle);
        return this;
    }

    public ExcelCell wrap() {
        currentCell.setCellStyle(workbook().wrapStyle());
        return this;
    }

    public ExcelCell header() {
        currentCell.setCellStyle(workbook().headerStyle());
        return this;
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
}
