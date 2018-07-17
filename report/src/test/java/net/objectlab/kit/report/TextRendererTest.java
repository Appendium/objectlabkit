package net.objectlab.kit.report;

import java.io.StringWriter;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class TextRendererTest {
    protected List<SimpleData> createDataSet() {
        SimpleData sd = new SimpleData("Apple", 50_000_00, "USD", true);
        SimpleData sd2 = new SimpleData("Internal Business Machine Inc", 270_000_000, "USD", true);
        return ImmutableList.of(sd, sd2);
    }

    @Test
    public void testTextReport() {
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
