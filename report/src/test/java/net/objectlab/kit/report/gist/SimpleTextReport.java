package net.objectlab.kit.report.gist;

import java.io.StringWriter;
import java.util.List;

import com.google.common.collect.ImmutableList;

import lombok.Getter;
import lombok.Setter;
import net.objectlab.kit.report.ReportTable;
import net.objectlab.kit.report.ReportTextColumn;
import net.objectlab.kit.report.SimpleData;
import net.objectlab.kit.report.TextRenderer;

public class SimpleTextReport {
    @Getter
    @Setter
    public static class Data {
        private String name;
        private long revenue;
        private String currency;
        private Boolean international;
    }

    private static List<SimpleData> createDataSet() {
        SimpleData sd = new SimpleData("Apple", 50_000_00, "USD", true);
        SimpleData sd2 = new SimpleData("Internal Business Machine Inc", 270_000_000, "USD", true);
        return ImmutableList.of(sd, sd2);
    }

    /** Will Generate: 
     * 
    +----------------------+----------------------+----------+---------------+
    |              Company |              Revenue | Currency | International |
    +----------------------+----------------------+----------+---------------+
    |                Apple |            5,000,000 |      USD |             T |
    | Internal Business... |          270,000,000 |      USD |             T |
    +----------------------+----------------------+----------+---------------+
    |                TOTAL |          320,000,000 |      USD |               |
    +----------------------+----------------------+----------+---------------+
     */
    public static void main(String[] args) {
        ReportTable<SimpleData> report = new ReportTable<>(ImmutableList.of( //
                new ReportTextColumn("Company", "name", 20), //
                new ReportTextColumn("Revenue", "revenue", 20, false, true), //
                new ReportTextColumn("Currency", "currency", 8), //
                new ReportTextColumn("International", "international", 13) //
        ));
        StringWriter sw = new StringWriter();

        report.setValues(createDataSet()) //
                .setTotalRow(new SimpleData("TOTAL", 320_000_000, "USD", null))//
                .export(new TextRenderer<SimpleData>(sw));

        System.out.println(sw.toString());
    }
}
