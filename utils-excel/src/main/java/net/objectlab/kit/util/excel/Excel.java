package net.objectlab.kit.util.excel;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

public class Excel {

    private Workbook workbook;

    public Excel(final InputStream in) {
        init(in);
    }

    private void init(final InputStream inputStream) {
        if (inputStream == null) {
            throw new NullPointerException("inputStream cannot be null");
        }

        try {
            workbook = WorkbookFactory.create(inputStream);
        } catch (final Exception e) {
            throw new ExcelException(e);
        }
    }

    public <E> E readValueAt(final String cellAddress, final Class<E> type) {
        return readCell(cellAt(cellAddress), type);
    }

    public <E> List<E> readColumn(final String rangeOrStartAddress, final Class<E> type) {

        final Object[][] arr = readBlock(rangeOrStartAddress, type);

        final List<E> result = new LinkedList<>();
        for (final Object[] element : arr) {
            result.add((E) element[0]);
        }

        return result;
    }

    public String namedRangeToRangeAddress(final String namedRange) {
        final int namedCellIndex = getWorkbook().getNameIndex(namedRange);
        final Name namedCell = getWorkbook().getNameAt(namedCellIndex);

        return namedCell.getRefersToFormula();
    }

    public Cell cellAt(final String cellAddr) {
        final CellReference cr = new CellReference(cellAddr);

        return workbook.getSheet(cr.getSheetName()).getRow(cr.getRow()).getCell(cr.getCol());
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
    public Object[][] readBlock(final String range, final Class<?>... columnTypes) {

        if (columnTypes == null || columnTypes.length == 0) {
            throw new ExcelException("columnTypes cannot be null / empty");
        }

        final CellRangeAddress cra = CellRangeAddress.valueOf(range);
        final AreaReference ar = new AreaReference(range, null);
        final Sheet sheet = workbook.getSheet(ar.getFirstCell().getSheetName());

        final int firstColumn = cra.getFirstColumn();
        final int firstRow = cra.getFirstRow();
        final int lastRow = cra.getLastRow();
        final int height = lastRow - firstRow + 1;
        final int width = cra.getLastColumn() - firstColumn + 1;

        List<Object> result;
        if (height == 1) {
            result = new LinkedList<>();
        } else {
            result = new ArrayList<>(height);
        }

        for (int rowNum = 0; moreDataToRead(sheet, firstColumn, firstRow, lastRow, rowNum); rowNum++) {
            final Row row = sheet.getRow(firstRow + rowNum);
            final Object[] resultRow = new Object[width];
            result.add(resultRow);
            for (int colNum = 0; colNum < width; colNum++) {

                Class<?> colType;
                if (colNum < columnTypes.length - 1) {
                    colType = columnTypes[colNum];
                } else {
                    colType = columnTypes[columnTypes.length - 1];
                }

                final Cell cell = row.getCell(firstColumn + colNum);
                resultRow[colNum] = readCell(cell, colType);
            }

        }

        return result.toArray(new Object[][] {});
    }

    private <E> E readCell(final Cell cell, final Class<E> colType) {

        if (colType == Date.class) {
            return (E) cell.getDateCellValue();
        } else if (colType == Calendar.class) {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(cell.getDateCellValue());
            return (E) cal;
        } else if (colType == Integer.class) {
            return (E) (Integer) ((Double) cell.getNumericCellValue()).intValue();
        } else if (colType == Double.class) {
            return (E) (Double) cell.getNumericCellValue();
        } else if (colType == BigDecimal.class) {
            return (E) new BigDecimal(String.valueOf(cell.getNumericCellValue()));
        } else if (colType == String.class) {
            return (E) cell.getRichStringCellValue().getString();
        } else {
            throw new ExcelException("Column type not supported: " + colType);
        }

    }

    private boolean moreDataToRead(final Sheet sheet, final int firstColumn, final int firstRow, final int lastRow, final int rowNum) {

        final int height = lastRow - firstRow + 1;
        if (height > 1 && firstRow + rowNum > lastRow) {
            return false;
        }

        // check if the cell is empty
        final Row row = sheet.getRow(firstRow + rowNum);
        if (row == null) {
            return false;
        }

        final Cell cell = row.getCell(firstColumn);
        if (cell == null) {
            return false;
        }
        final String str = cell.toString();
        return !(str == null || "".equals(str));
    }

    public Workbook getWorkbook() {
        return workbook;
    }
}
