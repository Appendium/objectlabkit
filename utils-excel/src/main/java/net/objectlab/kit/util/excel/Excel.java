package net.objectlab.kit.util.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

public class Excel {

    private Workbook workbook;

    public Excel(InputStream in) {
        init(in);
    }

    private void init(InputStream inputStream) throws RuntimeException {

        if (inputStream == null) {
            throw new NullPointerException("inputStream cannot be null");
        }

        try {
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <E> E readValueAt(String cellAddress, Class<E> type) {
        return readCell(cellAt(cellAddress), type);
    }

    public <E> List<E> readColumn(String rangeOrStartAddress, Class<E> type) {

        Object[][] arr = readBlock(rangeOrStartAddress, type);

        List<E> result = new LinkedList<E>();
        for (int i = 0; i < arr.length; i++) {
            result.add((E)arr[i][0]);
        }

        return result;
    }

    public String namedRangeToRangeAddress(String namedRange) {
        int namedCellIndex = getWorkbook().getNameIndex(namedRange);
        Name namedCell = getWorkbook().getNameAt(namedCellIndex);

        return namedCell.getRefersToFormula();
    }

    public Cell cellAt(String cellAddr) {
        CellReference cr = new CellReference(cellAddr);

        return workbook
                .getSheet(cr.getSheetName())
                .getRow(cr.getRow())
                .getCell((int) cr.getCol());
    }

    /**
     * @param range either the range of the entire block to be read, or just the
     *              top row of the cells, in which case the method will stop when
     *              the first empty cell is reached in the first column
     * @param columnTypes An array of data types expected at each column.
     *                 If this array is shorter than the number of column, then the last
     *                 data type is used until the end. So if only one value is given,
     *                 then that is used for the entire block.
     */
    public Object[][] readBlock(String range, Class... columnTypes) {

        if (columnTypes == null || columnTypes.length == 0) {
            throw new RuntimeException("columnTypes cannot be null / empty");
        }

        CellRangeAddress cra = CellRangeAddress.valueOf(range);
        AreaReference ar = new AreaReference(range);
        Sheet sheet = workbook.getSheet(ar.getFirstCell().getSheetName());

        int firstColumn = cra.getFirstColumn();
        int firstRow = cra.getFirstRow();
        int lastRow = cra.getLastRow();
        int height = lastRow - firstRow + 1;
        int width = cra.getLastColumn() - firstColumn + 1;

        List<Object> result;
        if (height == 1) {
            result = new LinkedList<Object>();
        } else {
            result = new ArrayList<Object>(height);
        }

        for (int rowNum = 0; moreDataToRead(sheet, firstColumn, firstRow, lastRow, rowNum); rowNum++) {
            Row row = sheet.getRow(firstRow + rowNum);
            Object[] resultRow = new Object[width];
            result.add(resultRow);
            for (int colNum = 0; colNum < width; colNum++) {

                Class colType;
                if (colNum < columnTypes.length - 1) {
                    colType = columnTypes[colNum];
                } else {
                    colType = columnTypes[columnTypes.length - 1];
                }

                Cell cell = row.getCell(firstColumn + colNum);
                resultRow[colNum] = readCell(cell, colType);
            }

        }

        return result.toArray(new Object[][] {});
    }

    private <E> E readCell(Cell cell, Class<E> colType) {

        if (colType == Date.class) {
            return (E) cell.getDateCellValue();
        } else if (colType == Calendar.class) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(cell.getDateCellValue());
            return (E) cal;
        } else if (colType == Integer.class) {
            return (E) ((Integer) ((Double) cell.getNumericCellValue()).intValue());
        } else if (colType == Double.class) {
            return (E) (Double) cell.getNumericCellValue();
        } else if (colType == BigDecimal.class) {
            return (E) new BigDecimal(String.valueOf(cell.getNumericCellValue()));
        } else if (colType == String.class) {
            return (E) cell.getRichStringCellValue().getString();
        } else {
            throw new RuntimeException("Column type not supported: " + colType);
        }

    }

    private boolean moreDataToRead(Sheet sheet, int firstColumn, int firstRow, int lastRow, int rowNum) {

        int height = lastRow - firstRow + 1;
        if (height > 1 && firstRow + rowNum > lastRow) {
            return false;
        }

        // check if the cell is empty
        Row row = sheet.getRow(firstRow + rowNum);
        if (row == null) {
            return false;
        }

        Cell cell = row.getCell(firstColumn);
        if (cell == null) {
            return false;
        }
        String str = cell.toString();
        return !(str == null || "".equals(str));
    }

    public Workbook getWorkbook() {
        return workbook;
    }
}
