package net.objectlab.kit.datecalc.gist;

import java.util.LinkedHashMap;
import java.util.List;

import net.objectlab.kit.datecalc.common.IMMDateCalculator;
import net.objectlab.kit.datecalc.common.Tenor;
import net.objectlab.kit.datecalc.common.TenorCode;
import net.objectlab.kit.datecalc.joda.LocalDateCalculator;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

import org.joda.time.LocalDate;

public class GisIMMCalculatorExample {
    private LocalDateCalculator dateCalculator = LocalDateKitCalculatorsFactory.forwardCalculator("hi");
    private IMMDateCalculator<LocalDate> immDateCalculator = LocalDateKitCalculatorsFactory.getDefaultInstance().getIMMDateCalculator();

    public static void main(String[] args) {
        new GisIMMCalculatorExample().getIMMDates();
    }

    LinkedHashMap<String, LocalDate> getIMMDates() {
        LocalDate tradeDate = new LocalDate(2014, 9, 12);
        dateCalculator.setStartDate(tradeDate);
        LocalDate startDate = dateCalculator.moveByBusinessDays(3).getCurrentBusinessDate();
        LocalDate fwdDate = dateCalculator.moveByTenor(new Tenor(27, TenorCode.MONTH), 0).getCurrentBusinessDate();
        System.out.println("Start:" + startDate.toString("E dd-MMM-yyyy") + " End:" + fwdDate.toString("E dd-MMM-yyyy"));
        List<LocalDate> immDates = immDateCalculator.getIMMDates(startDate, fwdDate);
        LinkedHashMap<String, LocalDate> map = new LinkedHashMap<String, LocalDate>();
        int ctr = 1;
        for (LocalDate immDate : immDates) {
            StringBuilder immKey = new StringBuilder("IMM").append(ctr++);
            if (ctr < 9) // Only want IMM1 thru to IMM8 being published
            {
                map.put(immKey.toString(), immDate);
            }
        }
        System.out.println(map);
        return map;
    }

}
