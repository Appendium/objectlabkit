package net.objectlab.kit.datecalc.gist;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.objectlab.kit.datecalc.common.IMMDateCalculator;
import net.objectlab.kit.datecalc.common.Tenor;
import net.objectlab.kit.datecalc.common.TenorCode;
import net.objectlab.kit.datecalc.joda.LocalDateCalculator;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

import org.joda.time.LocalDate;

public class GistIMMCalculatorExample {
    private final LocalDateCalculator dateCalculator = LocalDateKitCalculatorsFactory.forwardCalculator("hi");
    private final IMMDateCalculator<LocalDate> immDateCalculator = LocalDateKitCalculatorsFactory.getDefaultInstance().getIMMDateCalculator();

    public static void main(final String[] args) {
        GistIMMCalculatorExample ex = new GistIMMCalculatorExample();
        ex.getIMMDates();
        ex.getNewIMMDates();
    }

    /**
     * Using new method which returns the next N IMM Dates
     * @return
     */
    private Map<String, LocalDate> getNewIMMDates() {
        final LocalDate tradeDate = new LocalDate(2014, 9, 12);
        dateCalculator.setStartDate(tradeDate);
        final LocalDate startDate = dateCalculator.moveByBusinessDays(3).getCurrentBusinessDate();
        final List<LocalDate> immDates = immDateCalculator.getNextIMMDates(startDate, 8);
        final LinkedHashMap<String, LocalDate> map = new LinkedHashMap<String, LocalDate>();
        int ctr = 1;
        for (final LocalDate immDate : immDates) {
            final StringBuilder immKey = new StringBuilder("IMM").append(ctr++);
            map.put(immKey.toString(), immDate);
        }
        System.out.println("getNewIMMDates " + map);
        return map;
    }

    private Map<String, LocalDate> getIMMDates() {
        final LocalDate tradeDate = new LocalDate(2014, 9, 12);
        dateCalculator.setStartDate(tradeDate);
        final LocalDate startDate = dateCalculator.moveByBusinessDays(3).getCurrentBusinessDate();
        final LocalDate fwdDate = dateCalculator.moveByTenor(new Tenor(27, TenorCode.MONTH), 0).getCurrentBusinessDate();
        System.out.println("Start:" + startDate.toString("E dd-MMM-yyyy") + " End:" + fwdDate.toString("E dd-MMM-yyyy"));
        final List<LocalDate> immDates = immDateCalculator.getIMMDates(startDate, fwdDate);
        final LinkedHashMap<String, LocalDate> map = new LinkedHashMap<String, LocalDate>();
        int ctr = 1;
        for (final LocalDate immDate : immDates) {
            final StringBuilder immKey = new StringBuilder("IMM").append(ctr++);
            map.put(immKey.toString(), immDate);
            if (ctr >= 9) { // Only want IMM1 thru to IMM8 being published
                break;
            }
        }
        System.out.println("getIMMDates    " + map);
        return map;
    }

}
