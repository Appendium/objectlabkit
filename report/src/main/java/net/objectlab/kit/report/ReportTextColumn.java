package net.objectlab.kit.report;

public class ReportTextColumn implements ReportColumn {
    private final String title;
    private final String propertyName;
    private final boolean truncate;
    private final boolean formatAsANumber;
    private final Integer size;

    public ReportTextColumn(String title, String propertyName, Integer size) {
        this(title, propertyName, size, false, false);
    }

    public ReportTextColumn(String title, String propertyName, Integer size, boolean truncate, boolean formatAsANumber) {
        super();
        this.title = title;
        this.propertyName = propertyName;
        this.truncate = truncate;
        this.size = size;
        this.formatAsANumber = formatAsANumber;
    }

    @Override
    public boolean isFormatAsANumber() {
        return formatAsANumber;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public boolean isTruncate() {
        return truncate;
    }

    @Override
    public Integer getSize() {
        return size;
    }
}
