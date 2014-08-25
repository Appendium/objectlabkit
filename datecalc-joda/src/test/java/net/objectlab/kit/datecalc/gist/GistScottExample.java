package net.objectlab.kit.datecalc.gist;

import java.util.HashSet;
import java.util.Set;

import net.objectlab.kit.datecalc.common.CurrencyDateCalculatorBuilder;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.SpotLag;
import net.objectlab.kit.datecalc.common.StandardTenor;
import net.objectlab.kit.datecalc.common.ccy.DefaultCurrencyCalculatorConfig;
import net.objectlab.kit.datecalc.joda.LocalDateCurrencyDateCalculator;
import net.objectlab.kit.datecalc.joda.LocalDateForwardHandler;
import net.objectlab.kit.datecalc.joda.LocalDateModifiedFollowingHandler;

import org.joda.time.LocalDate;

public class GistScottExample {
    public static void main(final String[] args) {
        example1NoHolidays();
        example2EURHolidays();
        example3USDEURHolidays();
        exampleBX1MNoHolidays();
        exampleBX1MOctober();
        exampleBX1MJanuary();
    }

    private static void exampleBX1MJanuary() {
        final CurrencyDateCalculatorBuilder<LocalDate> builder = new CurrencyDateCalculatorBuilder<LocalDate>() //
                .currencyPair("EUR", "USD", SpotLag.T_2) //
                .tenorHolidayHandler(new LocalDateForwardHandler()) // Forward
                // Will be used for finding Working Weeks if not provided and Latin American ccy USD handling.
                .currencyCalculatorConfig(new DefaultCurrencyCalculatorConfig());

        final LocalDateCurrencyDateCalculator calc = new LocalDateCurrencyDateCalculator(builder);

        LocalDate startDate = new LocalDate(2014, 1, 29);
        System.out.println(calc.getName() + " BX JAN No holiday TD: " + startDate + " Calculated Spot " + calc.calculateSpotDate(startDate)
                + " expects 31 Jan!");
        System.out.println(calc.getName() + " BX JAN No holiday TD: " + startDate + " Calculated 1M "
                + calc.calculateTenorDate(startDate, StandardTenor.T_1M) + " expects 28-Feb ");
    }

    private static void exampleBX1MOctober() {
        final CurrencyDateCalculatorBuilder<LocalDate> builder = new CurrencyDateCalculatorBuilder<LocalDate>() //
                .currencyPair("EUR", "USD", SpotLag.T_2) //
                .tenorHolidayHandler(new LocalDateForwardHandler()) // Forward
                // Will be used for finding Working Weeks if not provided and Latin American ccy USD handling.
                .currencyCalculatorConfig(new DefaultCurrencyCalculatorConfig());

        final LocalDateCurrencyDateCalculator calc = new LocalDateCurrencyDateCalculator(builder);

        LocalDate startDate = new LocalDate(2014, 10, 28);
        System.out.println(calc.getName() + " BX Oct No holiday TD: " + startDate + " Calculated Spot " + calc.calculateSpotDate(startDate)
                + " expects 30 Oct!");
        System.out.println(calc.getName() + " BX Oct No holiday TD: " + startDate + " Calculated 1M "
                + calc.calculateTenorDate(startDate, StandardTenor.T_1M)
                + " expects 1-Dec as the given handler handled is a LocalDateForwardHandler ");

        final CurrencyDateCalculatorBuilder<LocalDate> standardBuilder = new CurrencyDateCalculatorBuilder<LocalDate>() //
                .currencyPair("EUR", "USD", SpotLag.T_2) //
                .tenorHolidayHandler(new LocalDateModifiedFollowingHandler()) // MODIFIED Forward
                // Will be used for finding Working Weeks if not provided and Latin American ccy USD handling.
                .currencyCalculatorConfig(new DefaultCurrencyCalculatorConfig());

        final LocalDateCurrencyDateCalculator standardCalc = new LocalDateCurrencyDateCalculator(standardBuilder);

        System.out.println(calc.getName() + " BX Oct No holiday TD: " + startDate + " Calculated 1M "
                + standardCalc.calculateTenorDate(startDate, StandardTenor.T_1M)
                + " expects 28-Nov as the given handler handled is a LocalDateModifiedFollowingHandler ");
    }

    private static void exampleBX1MNoHolidays() {
        final CurrencyDateCalculatorBuilder<LocalDate> builder = new CurrencyDateCalculatorBuilder<LocalDate>() //
                .currencyPair("EUR", "USD", SpotLag.T_2) //
                .tenorHolidayHandler(new LocalDateForwardHandler()) // Forward
                // Will be used for finding Working Weeks if not provided and Latin American ccy USD handling.
                .currencyCalculatorConfig(new DefaultCurrencyCalculatorConfig());

        final LocalDateCurrencyDateCalculator calc = new LocalDateCurrencyDateCalculator(builder);

        LocalDate startDate = new LocalDate(2014, 6, 26);
        System.out.println(calc.getName() + " BX Ex1 No holiday TD: " + startDate + " Calculated Spot " + calc.calculateSpotDate(startDate)
                + " expects 30 Jun!");
        System.out.println(calc.getName() + " BX Ex1 No holiday TD: " + startDate + " Calculated 1M "
                + calc.calculateTenorDate(startDate, StandardTenor.T_1M) + " expects 30 Jul");

        startDate = new LocalDate(2014, 10, 28);
        System.out.println(calc.getName() + " BX Ex1 No holiday TD: " + startDate + " Calculated Spot " + calc.calculateSpotDate(startDate)
                + " expects 30 Oct!");
        System.out.println(calc.getName() + " BX Ex1 No holiday TD: " + startDate + " Calculated 1M "
                + calc.calculateTenorDate(startDate, StandardTenor.T_1M) + " expects 01-Dec");
    }

    private static void example1NoHolidays() {
        final CurrencyDateCalculatorBuilder<LocalDate> builder = new CurrencyDateCalculatorBuilder<LocalDate>() //
                .currencyPair("EUR", "USD", SpotLag.T_2) //
                .tenorHolidayHandler(new LocalDateForwardHandler()) // Forward
                // Will be used for finding Working Weeks if not provided and Latin American ccy USD handling.
                .currencyCalculatorConfig(new DefaultCurrencyCalculatorConfig());

        final LocalDateCurrencyDateCalculator calc = new LocalDateCurrencyDateCalculator(builder);

        final LocalDate startDate = new LocalDate(2014, 7, 1);
        System.out.println(calc.getName() + " Ex1 No holiday TD: " + startDate + " Calculated Spot " + calc.calculateSpotDate(startDate)
                + " expects 3 Jul");
    }

    private static void example2EURHolidays() {
        final Set<LocalDate> holidays = new HashSet<LocalDate>();
        holidays.add(new LocalDate("2014-07-03"));
        // create the HolidayCalendar ASSUMING that the set covers 2006!
        final HolidayCalendar<LocalDate> eurCalendar = new DefaultHolidayCalendar<LocalDate>(holidays, new LocalDate("2014-01-01"), new LocalDate(
                "2014-12-31"));
        final CurrencyDateCalculatorBuilder<LocalDate> builder = new CurrencyDateCalculatorBuilder<LocalDate>() //
                .currencyPair("EUR", "USD", SpotLag.T_2) //
                .ccy1Calendar(eurCalendar) //
                .tenorHolidayHandler(new LocalDateForwardHandler()) // Forward
                // Will be used for finding Working Weeks if not provided and Latin American ccy USD handling.
                .currencyCalculatorConfig(new DefaultCurrencyCalculatorConfig());

        final LocalDateCurrencyDateCalculator calc = new LocalDateCurrencyDateCalculator(builder);

        final LocalDate startDate = new LocalDate(2014, 7, 1);
        System.out.println(calc.getName() + " Ex2 EUR holiday TD: " + startDate + " Calculated Spot " + calc.calculateSpotDate(startDate)
                + " expects 4 Jul");
    }

    private static void example3USDEURHolidays() {
        final Set<LocalDate> holidays = new HashSet<LocalDate>();
        holidays.add(new LocalDate("2014-07-03"));
        // create the HolidayCalendar ASSUMING that the set covers 2006!
        final HolidayCalendar<LocalDate> eurCalendar = new DefaultHolidayCalendar<LocalDate>(holidays, new LocalDate("2014-01-01"), new LocalDate(
                "2014-12-31"));

        final Set<LocalDate> holidays2 = new HashSet<LocalDate>();
        holidays2.add(new LocalDate("2014-07-04"));
        // create the HolidayCalendar ASSUMING that the set covers 2006!
        final HolidayCalendar<LocalDate> usdCalendar = new DefaultHolidayCalendar<LocalDate>(holidays2, new LocalDate("2014-01-01"), new LocalDate(
                "2014-12-31"));
        final CurrencyDateCalculatorBuilder<LocalDate> builder = new CurrencyDateCalculatorBuilder<LocalDate>() //
                .currencyPair("EUR", "USD", SpotLag.T_2) //
                .ccy1Calendar(eurCalendar) //
                .ccy2Calendar(usdCalendar) //
                .tenorHolidayHandler(new LocalDateForwardHandler()) // Forward
                // Will be used for finding Working Weeks if not provided and Latin American ccy USD handling.
                .currencyCalculatorConfig(new DefaultCurrencyCalculatorConfig());

        final LocalDateCurrencyDateCalculator calc = new LocalDateCurrencyDateCalculator(builder);

        final LocalDate startDate = new LocalDate(2014, 7, 1);
        System.out.println(calc.getName() + " Ex3 USD+EUR holiday TD: " + startDate + " Calculated Spot " + calc.calculateSpotDate(startDate)
                + " expects 7 Jul");
    }
}
