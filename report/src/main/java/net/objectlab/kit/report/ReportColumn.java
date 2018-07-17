package net.objectlab.kit.report;

public interface ReportColumn {
    String getTitle();

    String getPropertyName();

    Integer getSize();

    boolean isTruncate();

    boolean isFormatAsANumber();
}
