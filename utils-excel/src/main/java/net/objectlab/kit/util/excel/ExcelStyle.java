package net.objectlab.kit.util.excel;

import java.util.Optional;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ExcelStyle {
    private final boolean center;
    private final boolean left;
    private final boolean right;
    private final boolean bold;
    private final boolean italic;
    private final boolean underline;
    private final boolean numericFormat;
    private final boolean percentFormat;
    private final boolean wrap;
    private final boolean header;
    private final boolean strikeout;
    private final BorderStyle borderTop;
    private final BorderStyle borderBottom;
    private final BorderStyle borderLeft;
    private final BorderStyle borderRight;
    private final String dataFormat;
    private final IndexedColors fontColour;
    private final IndexedColors backgroundColour;
    private final FillPatternType backgroundFillPatternType;

    public static Builder builder() {
        return new Builder();
    }

    private ExcelStyle(Builder b) {
        center = b.center;
        left = b.left;
        right = b.right;
        bold = b.bold;
        italic = b.italic;
        underline = b.underline;
        numericFormat = b.numericFormat;
        percentFormat = b.percentFormat;
        wrap = b.wrap;
        header = b.header;
        strikeout = b.strikeout;
        dataFormat = b.dataFormat;
        fontColour = b.fontColour;
        backgroundColour = b.backgroundColour;
        backgroundFillPatternType = b.backgroundFillPatternType;
        borderRight = b.borderRight;
        borderLeft = b.borderLeft;
        borderTop = b.borderTop;
        borderBottom = b.borderBottom;
    }

    public CellStyle build(ExcelCell cell) {
        Optional<CellStyle> cs = cell.findStyle(hashCode());
        if (cs.isPresent()) {
            return cs.get();
        }

        CellStyle cellStyle = cell.cloneStyle(hashCode());
        addFontFormat(cell, cellStyle);

        if (header) {
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        if (borderTop != null) {
            cellStyle.setBorderTop(borderTop);
            cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        }

        if (borderBottom != null) {
            cellStyle.setBorderBottom(borderBottom);
            cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        }

        if (borderRight != null) {
            cellStyle.setBorderRight(borderRight);
            cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        }

        if (borderLeft != null) {
            cellStyle.setBorderLeft(borderLeft);
            cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        }

        addFormat(cell, cellStyle);

        if (backgroundColour != null) {
            cellStyle.setFillForegroundColor(backgroundColour.getIndex());
            cellStyle.setFillPattern(backgroundFillPatternType != null ? backgroundFillPatternType : FillPatternType.SOLID_FOREGROUND);
        }

        addAlignment(cellStyle);

        cellStyle.setWrapText(wrap);

        return cellStyle;
    }

    private void addFontFormat(ExcelCell cell, CellStyle cellStyle) {
        if (bold || italic || underline || header || fontColour != null || strikeout) {
            Font f = cell.workbook().createFont();
            f.setBold(bold || header);
            if (underline) {
                f.setUnderline(Font.U_SINGLE);
            }
            if (fontColour != null) {
                f.setColor(fontColour.getIndex());
            }
            f.setItalic(italic);
            f.setStrikeout(strikeout);
            cellStyle.setFont(f);
        }
    }

    private void addFormat(ExcelCell cell, CellStyle cellStyle) {
        if (dataFormat != null && dataFormat.length() > 0) {
            DataFormat format = cell.poiWorkbook().createDataFormat();
            cellStyle.setDataFormat(format.getFormat(dataFormat));
        } else if (numericFormat) {
            DataFormat format = cell.poiWorkbook().createDataFormat();
            cellStyle.setDataFormat(format.getFormat("#,###,###,###"));
        } else if (percentFormat) {
            DataFormat format = cell.poiWorkbook().createDataFormat();
            cellStyle.setDataFormat(format.getFormat("#,###,###,##0.00%"));
        }
    }

    private void addAlignment(CellStyle cellStyle) {
        if (center) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        } else if (right) {
            cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        } else if (left) {
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
        }
    }

    public static class Builder {
        private boolean center;
        private boolean left;
        private boolean right;
        private boolean bold;
        private boolean italic;
        private boolean underline;
        private boolean numericFormat;
        private boolean percentFormat;
        private boolean wrap;
        private boolean header;
        private boolean strikeout;
        private String dataFormat;
        private IndexedColors fontColour;
        private IndexedColors backgroundColour;
        private FillPatternType backgroundFillPatternType = FillPatternType.SOLID_FOREGROUND;
        private BorderStyle borderTop;
        private BorderStyle borderBottom;
        private BorderStyle borderLeft;
        private BorderStyle borderRight;

        public Builder backgroundFillPatternType(FillPatternType backgroundFillPatternType) {
            this.backgroundFillPatternType = backgroundFillPatternType;
            return this;
        }

        public Builder borderTop(BorderStyle borderTop) {
            this.borderTop = borderTop;
            return this;
        }

        public Builder borderBottom(BorderStyle borderBottom) {
            this.borderBottom = borderBottom;
            return this;
        }

        public Builder borderLeft(BorderStyle borderLeft) {
            this.borderLeft = borderLeft;
            return this;
        }

        public Builder borderRight(BorderStyle borderRight) {
            this.borderRight = borderRight;
            return this;
        }

        public Builder backgroundColour(IndexedColors backgroundColour) {
            this.backgroundColour = backgroundColour;
            return this;
        }

        public Builder fontColour(IndexedColors fontColour) {
            this.fontColour = fontColour;
            return this;
        }

        public Builder strikeout() {
            strikeout = true;
            return this;
        }

        public Builder center() {
            center = true;
            return this;
        }

        public Builder left() {
            left = true;
            return this;
        }

        public Builder right() {
            right = true;
            return this;
        }

        public Builder bold() {
            bold = true;
            return this;
        }

        public Builder italic() {
            italic = true;
            return this;
        }

        public Builder underline() {
            underline = true;
            return this;
        }

        public Builder numericFormat() {
            numericFormat = true;
            return this;
        }

        public Builder percentFormat() {
            percentFormat = true;
            return this;
        }

        public Builder wrap() {
            wrap = true;
            return this;
        }

        public Builder header() {
            header = true;
            return this;
        }

        public Builder dataFormat(String dataFormat) {
            this.dataFormat = dataFormat;
            return this;
        }

        public ExcelStyle build() {
            return new ExcelStyle(this);
        }
    }
}
