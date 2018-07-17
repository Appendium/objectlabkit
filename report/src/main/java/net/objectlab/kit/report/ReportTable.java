package net.objectlab.kit.report;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

import net.objectlab.kit.util.CollectionUtil;

public class ReportTable<T> {
    private final List<ReportColumn> columns;
    private Collection<T> values;
    private T totalRow;

    public ReportTable(final List<ReportColumn> columns) {
        super();
        this.columns = columns;
    }

    public T getTotalRow() {
        return totalRow;
    }

    public ReportTable<T> setTotalRow(T totalRow) {
        this.totalRow = totalRow;
        return this;
    }

    public Collection<T> getValues() {
        return values;
    }

    public ReportTable<T> setValues(final Collection<T> values) {
        this.values = values;
        return this;
    }

    public ReportTable<T> export(TableRenderer<T> renderer) {
        final Writer writer = renderer.getWriter();
        try {
            // do Header
            if (CollectionUtil.isNotEmpty(columns)) {
                underline(columns, writer, renderer);
                writer.append(renderer.startHeaderRow());
                writer.append(renderer.startHeaderCol());
                columns.forEach(c -> renderer.formatColumn(writer, c, c.getTitle()));
                writer.append(renderer.endHeaderRow());
                writer.append(renderer.startRow());
                underline(columns, writer, renderer);
            }

            if (CollectionUtil.isNotEmpty(values)) {
                // do values
                values.forEach(t -> renderer.renderRow(columns, writer, t));
            }
            if (totalRow != null) {
                writer.append(renderer.startHeaderRow());
                underline(columns, writer, renderer);
                writer.append(renderer.endHeaderRow());
                renderer.renderRow(columns, writer, totalRow);
            }

            writer.append(renderer.startHeaderRow());
            underline(columns, writer, renderer);
            writer.append(renderer.endHeaderRow());
            // footer?
        } catch (IOException e) {
            throw new ReportException(e);
        }
        return this;
    }

    public ReportTable<T> calculateTotalRow() {
        return this;
    }

    private void underline(List<ReportColumn> columns, Writer b, TableRenderer<T> renderer) throws IOException {
        b.append(renderer.getCellCorner());
        columns.forEach(c -> renderer.renderColumnTitle(c, b));
        // b.append(renderer.getNextRow());
    }

}
