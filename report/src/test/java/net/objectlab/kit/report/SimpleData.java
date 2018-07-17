package net.objectlab.kit.report;

public class SimpleData {
    private String name;
    private long revenue;
    private String currency;
    private Boolean international;

    public SimpleData(String name, long revenue, String currency, Boolean international) {
        super();
        this.name = name;
        this.revenue = revenue;
        this.currency = currency;
        this.international = international;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getInternational() {
        return international;
    }

    public void setInternational(Boolean international) {
        this.international = international;
    }
}
