package net.objectlab.kit.datecalc.gist;

import java.util.HashSet;
import java.util.Set;

import net.objectlab.kit.datecalc.common.CurrencyDateCalculatorBuilder;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.SpotLag;
import net.objectlab.kit.datecalc.common.WorkingWeek;
import net.objectlab.kit.datecalc.common.ccy.DefaultCurrencyCalculatorConfig;
import net.objectlab.kit.datecalc.joda.LocalDateCurrencyDateCalculator;
import net.objectlab.kit.datecalc.joda.LocalDateForwardHandler;

import org.joda.time.LocalDate;

public class GistCurrencyCalculatorWithBuilderExample {
    public static void main(final String[] args) {
        simpleEurUsd();
    }

    private static void simpleEurUsd() {
        final Set<LocalDate> holidays = new HashSet<LocalDate>();
        holidays.add(new LocalDate("2006-07-04"));
        // create the HolidayCalendar ASSUMING that the set covers 2006!
        final HolidayCalendar<LocalDate> usdCalendar = new DefaultHolidayCalendar<LocalDate>(holidays, new LocalDate("2006-01-01"), new LocalDate(
                "2006-12-31"));

        final CurrencyDateCalculatorBuilder<LocalDate> builder = new CurrencyDateCalculatorBuilder<LocalDate>() //
                .currencyPair("EUR", "USD", SpotLag.T_2) //
                .ccy1Calendar(new DefaultHolidayCalendar<LocalDate>()) // empty
                .ccy1Week(WorkingWeek.DEFAULT) // Mon-Fri
                .ccy2Calendar(usdCalendar) //
                .ccy2Week(WorkingWeek.DEFAULT) // Mon-Fri
                .crossCcyCalendar(usdCalendar) //
                .crossCcyWeek(WorkingWeek.DEFAULT) // Mon-Fri;
                .adjustStartDateWithCurrencyPair(true) // default is true, Move the startDate to a working date for ccy1 and ccy2
                .tenorHolidayHandler(new LocalDateForwardHandler()) // Forward
                .brokenDateAllowed(true) // use USD holidays on Spot Date
                .currencyCalculatorConfig(new DefaultCurrencyCalculatorConfig()) // Will be used for finding Working Weeks if not provided and Latin
                                                                                 // American ccy USD handling.
        ;

        final LocalDateCurrencyDateCalculator calc = new LocalDateCurrencyDateCalculator(builder);

        LocalDate startDate = new LocalDate(2006, 6, 30);
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects 5 Jul");
        startDate = new LocalDate(2006, 7, 2);
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects 5 Jul");
        startDate = new LocalDate(2006, 7, 3);
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects 5 Jul");
    }
}
