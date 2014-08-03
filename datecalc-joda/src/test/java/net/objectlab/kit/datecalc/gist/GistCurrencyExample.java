package net.objectlab.kit.datecalc.gist;

import java.util.HashSet;
import java.util.Set;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.StandardTenor;
import net.objectlab.kit.datecalc.common.Tenor;
import net.objectlab.kit.datecalc.common.TenorCode;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

import org.joda.time.LocalDate;

public class GistCurrencyExample {
    public static void main(String[] args) {
        // create or get the Holidays
        final Set<LocalDate> holidays = new HashSet<LocalDate>();
        holidays.add(new LocalDate("2006-07-04"));
        // ... keep adding all holidays for 2006

        // create the HolidayCalendar ASSUMING that the set covers 2006!
        final HolidayCalendar<LocalDate> calendar = new DefaultHolidayCalendar<LocalDate>(holidays, new LocalDate("2006-01-01"), new LocalDate(
                "2006-12-31"));

        // register the holidays, any calculator with name "USD" MUST BE the CURRENCY NAME
        // asked from now on will receive an IMMUTABLE reference to this calendar
        LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("USD", calendar);

        // ask for a Currency calculator for "USD/EUR"
        // (even if a new set of holidays is registered, this one calculator is not affected
        DateCalculator<LocalDate> cal = LocalDateKitCalculatorsFactory.currencyCalculator("USD", "EUR");

        // set startDate, this will also set the current business date.
        cal.setStartDate(new LocalDate("2006-07-03")); // Monday

        cal.moveByTenor(StandardTenor.SPOT, 2); // USD/EUR is 2 days to spot (spotLag)
        System.out.println("Expect 5 Jul " + cal.getCurrentBusinessDate()); // 5 July 2006 as 4th is USD holiday BUT we skip that as
        // USD hol on T+1 are ignored

        // set startDate, to
        cal.setStartDate(new LocalDate("2006-06-30")); // Friday
        cal.moveByTenor(StandardTenor.SPOT, 2); // USD/EUR is 2 days to spot (spotLag)
        System.out.println("Expect 5 Jul " + cal.getCurrentBusinessDate()); // 5 July 2006 as 4th is USD holiday

        cal.setStartDate(new LocalDate("2006-05-02"));
        cal.moveByTenor(new Tenor(2, TenorCode.MONTH), 2); // USD/EUR is 2 days to spot (spotLag); 2M falls on July 4
        System.out.println("Expect 5 Jul " + cal.getCurrentBusinessDate()); // 5 July 2006 as 4th is USD holiday
    }
}
