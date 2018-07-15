package net.objectlab.kit.util.excel;

import java.io.IOException;
import java.util.stream.IntStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelSheet {
    private int currentRow;
    private ExcelWorkbook workbook;
    private XSSFSheet currentSheet;

    public ExcelSheet(XSSFSheet sheet, ExcelWorkbook book) {
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

    public int rowIndex() {
        return currentRow;
    }

    public ExcelWorkbook book() {
        return workbook;
    }

    public ExcelSheet newSheet(String title) {
        return workbook.newSheet(title);
    }

    public ExcelSheet autoSizeColumn(int startCol, int endCol) {
        IntStream.rangeClosed(startCol, endCol).forEach(i -> currentSheet.autoSizeColumn(i));
        return this;
    }

    public ExcelWorkbook workbook() {
        return workbook;
    }

    public ExcelSheet save(String fileName) throws IOException {
        workbook.save(fileName);
        return this;
    }
}
