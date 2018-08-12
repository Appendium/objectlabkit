package net.objectlab.kit.util.excel;

import java.io.IOException;
import java.util.Optional;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ExcelCell {
    private static final ExcelStyle LINK = ExcelStyle.builder().underline().fontColour(IndexedColors.BLUE).build();
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

    /**
     * Add a hyperlink
     * @param url URL 
     * @param label Label for the cell
     * @return the cell
     */
    public ExcelCell newCell(String url, String label) {
        return row.newCell().value(url, label);
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

    public ExcelCell value(String url, String label) {
        final CreationHelper creationHelper = row().sheet().workbook().poiWorkbook().getCreationHelper();
        final Hyperlink hl = creationHelper.createHyperlink(HyperlinkType.URL);
        hl.setAddress(url);
        hl.setLabel(label);
        currentCell.setCellValue(label);
        currentCell.setHyperlink(hl);
        style(LINK);
        return this;
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
