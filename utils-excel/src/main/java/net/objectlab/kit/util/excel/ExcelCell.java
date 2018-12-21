package net.objectlab.kit.util.excel;

import java.io.IOException;
import java.time.temporal.Temporal;
import java.util.Optional;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelCell {
    private static final ExcelStyle LINK = ExcelStyle.builder().underline().fontColour(IndexedColors.BLUE).build();
    private ExcelRow row;
    private Cell currentCell;

    public ExcelCell(Cell cell, ExcelRow row) {
        currentCell = cell;
        this.row = row;
    }

    public ExcelCell value(String value) {
        currentCell.setCellValue(value != null ? value : "");
        return this;
    }

    public ExcelCell value(Temporal value) {
        currentCell.setCellValue(value != null ? value.toString() : "");
        return this;
    }

    public ExcelRow row() {
        return row;
    }

    public int rowIndex() {
        return row.rowIndex();
    }

    public ExcelRow skipCol() {
        return row.skipCol();
    }

    public ExcelCell newCell(String value) {
        return row.newCell(value);
    }

    public ExcelCell newCell(Temporal value) {
        return row.newCell(value);
    }

    public ExcelCell newCellFormula(String formula) {
        return row.newCellFormula(formula);
    }

    /**
     * Add a hyperlink
     * @param url URL 
     * @param label Label for the cell
     * @return the cell
     */
    public ExcelCell newCell(String url, String label) {
        return row.newCell().link(url, label);
    }

    public ExcelCell newCell(boolean value) {
        return row.newCell(Boolean.toString(value));
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

    public ExcelCell link(String url, String label) {
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

    public ExcelCell formula(String formula) {
        currentCell.setCellFormula(formula);
        currentCell.setCellType(CellType.FORMULA);
        return this;
    }

    public ExcelCell value(Number value) {
        currentCell.setCellValue(value != null ? value.doubleValue() : 0d);
        currentCell.setCellType(CellType.NUMERIC);
        return this;
    }

    public ExcelCell value(boolean value) {
        currentCell.setCellValue(value);
        currentCell.setCellType(CellType.BOOLEAN);
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

    public ExcelCell comment(String commentText) {
        CreationHelper factory = row().sheet().poiWorkbook().getCreationHelper();
        Cell cell = currentCell;
        ClientAnchor anchor = factory.createClientAnchor();
        anchor.setCol1(cell.getColumnIndex());
        anchor.setCol2(cell.getColumnIndex() + 25);
        anchor.setRow1(cell.getRowIndex());
        anchor.setRow2(cell.getRowIndex() + 6);
        anchor.setAnchorType(AnchorType.DONT_MOVE_DO_RESIZE);

        Drawing drawing = row().sheet().poiSheet().createDrawingPatriarch();
        Comment comment = drawing.createCellComment(anchor);
        comment.setString(factory.createRichTextString(commentText));
        cell.setCellComment(comment);
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
