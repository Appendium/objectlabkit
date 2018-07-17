package net.objectlab.kit.report;

import java.io.Writer;
import java.util.List;

public interface TableRenderer<T> {
    Writer getWriter();

    String getCellCorner();

    String getNextRow();

    void renderColumnTitle(ReportColumn c, Writer b);

    String startHeaderCol();

    void formatColumn(Writer writer, ReportColumn c, String title);

    void renderRow(List<ReportColumn> columns, Writer writer, T t);

    String endHeaderRow();

    String startHeaderRow();

    String endRow();

    String startRow();

    String endCol();

    String startCol();
}
