package net.objectlab.kit.util.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.RangeAddress;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class Excel {

    private HSSFWorkbook workbook;

    private InputStream inputStream;

    public Excel() {
    }

    public Excel(InputStream in) {
        setInputStream(in);
        init();
    }

    public void init() throws RuntimeException {

        POIFSFileSystem fs;
        try {
            fs = new POIFSFileSystem(inputStream);
            workbook = new HSSFWorkbook(fs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <E> E readValue(String cellAddress, Class<E> type) {

        Object[][] result = readBlock(cellAddress, true, type);
        return (E) result[0][0];
    }

    /**
     * 
     * @param range
     *            either the range of the entire block to be read, or just the
     *            top row of the cells, in which case the method will stop when
     *            the first empty cell is reached in the first column
     * @param colTypes
     *            classes of the result types, per column
     * @return 2 dimensional array, containing the data read, cast as per
     *         colTypes
     */
    public Object[][] readBlock(String range, Class... colTypes) {
        return readBlock(range, false, colTypes);
    }

        
    private Object[][] readBlock(String range, boolean oneLiner, Class... colTypes) {

        RangeAddress ra = new RangeAddress(range);
        HSSFSheet sheet = workbook.getSheet(ra.getSheetName());

        oneLiner = (ra.getHeight() == 1 && oneLiner);

        List<Object> result;
        if (ra.getHeight() == 1) {
            result = new LinkedList<Object>();
        } else {
            result = new ArrayList<Object>(ra.getHeight());
        }

        int x = ra.getXPosition(ra.getFromCell());
        int y = ra.getYPosition(ra.getFromCell());
        
        Class colType = colTypes[0];
        for (int i = 0; (isMoreToRead(sheet, oneLiner, x, y, i)); i++) {
            HSSFRow row = sheet.getRow(y + i - 1);
            Object[] resultRow = new Object[ra.getWidth()];
            result.add(resultRow);
            for (int j = 0; j < ra.getWidth(); j++) {
                HSSFCell cell = row.getCell((short) (x + j - 1));

                if (colTypes.length > j) {
                    colType = colTypes[j];
                }

                if (colType == Date.class) {
                    resultRow[j] = cell.getDateCellValue();
                } else if (colType == Calendar.class) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(cell.getDateCellValue());
                    resultRow[j] = cal;
                } else if (colType == Integer.class) {
                    resultRow[j] = ((Double) cell.getNumericCellValue()).intValue();
                } else if (colType == Double.class) {
                    resultRow[j] = (Double) cell.getNumericCellValue();
                } else if (colType == BigDecimal.class) {
                    resultRow[j] = new BigDecimal(String.valueOf(cell.getNumericCellValue()));
                } else if (colType == String.class) {
                    resultRow[j] = cell.getRichStringCellValue().getString();
                }
            }

        }

        return result.toArray(new Object[][] {});
    }

    private boolean isMoreToRead(HSSFSheet sheet, boolean oneLiner, int x, int y, int i) {

        if (oneLiner) {
            return (i < 1);
        }
        
        // check if the cell is empty
        HSSFRow row = sheet.getRow(y + i - 1);
        if (row == null) {
            return false;
        }
        
        HSSFCell cell = row.getCell((short) (x - 1));
        if (cell == null) {
            return false;
        }
        String str = cell.toString();
        return !(str == null || "".equals(str));
    }

    public <E> List<E> readColumn(String rangeOrStartAddress, Class<E> type) {

        Object[][] arr = readBlock(rangeOrStartAddress, false, type);
        
        List<E> result = new LinkedList<E>();
        for (int i = 0; i < arr.length; i++) {
            result.add((E)arr[i][0]);
        }
        
        return result;
    }

    public String namedRangeToRangeAddress(String namedRange) {
        int namedCellIndex = getWorkbook().getNameIndex(namedRange);
        HSSFName namedCell = getWorkbook().getNameAt(namedCellIndex);

        return namedCell.getReference();
    }

    public HSSFCell getCell(String cellAddr) {
        RangeAddress ra = new RangeAddress(cellAddr);

        return workbook.getSheet(ra.getSheetName()).getRow(ra.getYPosition(ra.getFromCell()) - 1).getCell(
                (short) (ra.getXPosition(ra.getFromCell()) - 1));
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setFilename(String filename) {
        try {
            inputStream = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
