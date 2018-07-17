package net.objectlab.kit.report;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import net.objectlab.kit.util.IntegerUtil;
import net.objectlab.kit.util.StringUtil;

public class TextRenderer<T> implements TableRenderer<T> {
    private StringWriter writer;

    public TextRenderer(StringWriter w) {
        writer = w;
    }

    @Override
    public Writer getWriter() {
        return writer;
    }

    @Override
    public String getCellCorner() {
        return "+";
    }

    @Override
    public String getNextRow() {
        return System.lineSeparator();
    }

    public String getCellLine() {
        return "-";
    }

    @Override
    public String startHeaderCol() {
        return "|";
    }

    @Override
    public void renderColumnTitle(ReportColumn c, Writer writer) {
        try {
            writer.append(StringUtils.repeat(getCellLine(), c.getSize() + 2)).append(getCellCorner());
        } catch (IOException e) {
            throw new ReportException(e);
        }
    }

    private String truncate(int size, String value) {
        return StringUtils.length(value) <= size ? value : value.substring(0, size);
    }

    @Override
    public void formatColumn(Writer writer, ReportColumn c, String title) {
        try {
            writer.append(" ").append(formatColumn(c, title)).append(" ").append(startHeaderCol());
        } catch (IOException e) {
            throw new ReportException(e);
        }
    }

    private String formatColumn(ReportColumn c, String value) {
        if (IntegerUtil.assign(c.getSize(), 0) > 3) {
            if (c.isTruncate()) {
                return StringUtils.leftPad(truncate(c.getSize(), value), c.getSize());
            }
            return StringUtils.abbreviate(StringUtils.leftPad(value, c.getSize()), c.getSize());
        }
        return StringUtils.leftPad(truncate(c.getSize(), value), c.getSize());
    }

    @Override
    public String startCol() {
        return "| ";
    }

    @Override
    public String endCol() {
        return " ";
    }

    @Override
    public String startRow() {
        return System.lineSeparator();
    }

    @Override
    public String endRow() {
        return "|";
    }

    @Override
    public String startHeaderRow() {
        return System.lineSeparator();
    }

    @Override
    public String endHeaderRow() {
        return "";
    }

    @Override
    public void renderRow(List<ReportColumn> columns, Writer writer, T t) {
        try {
            writer.append(startRow());
            BeanWrapper bw = new BeanWrapperImpl(t);
            columns.forEach(c -> {
                String valStr = getCellValue(bw, c);
                try {
                    writer.append(startCol()).append(formatColumn(c, valStr)).append(endCol());
                } catch (IOException e) {
                    throw new ReportException(e);
                }
            });
            writer.append(endRow());
        } catch (IOException e) {
            throw new ReportException(e);
        }

    }

    private String getCellValue(BeanWrapper bw, ReportColumn c) {
        final Object propertyValue = bw.getPropertyValue(c.getPropertyName());
        String valStr;
        if (propertyValue instanceof Boolean) {
            valStr = (Boolean) propertyValue ? "T" : "F";
        } else if (propertyValue instanceof Number && c.isFormatAsANumber() && !(propertyValue instanceof BigDecimal)) {
            valStr = String.format("%,d", propertyValue);
        } else {
            valStr = StringUtil.toStringOrEmpty(propertyValue);
        }
        return valStr;
    }
}
