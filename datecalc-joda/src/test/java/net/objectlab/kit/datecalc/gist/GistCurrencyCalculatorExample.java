package net.objectlab.kit.datecalc.gist;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import net.objectlab.kit.datecalc.common.CalculatorConstants;
import net.objectlab.kit.datecalc.common.CurrencyDateCalculatorBuilder;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.SpotLag;
import net.objectlab.kit.datecalc.common.StandardTenor;
import net.objectlab.kit.datecalc.common.ccy.DefaultCurrencyCalculatorConfig;
import net.objectlab.kit.datecalc.joda.LocalDateCurrencyDateCalculator;
import net.objectlab.kit.datecalc.joda.LocalDateForwardHandler;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

public class GistCurrencyCalculatorExample {
    public static void main(final String[] args) {
        simpleEurUsd();
        simpleEurUsd1M();
        crossEurGbp();
        crossGbpJpyWithHolidays();
        crossEurMxn();
        simpleUsdAed();
        simpleUsdJod();
        simpleAudUsd();
    }

    private static void simpleUsdJod() {
        final LocalDateCurrencyDateCalculator calc = LocalDateKitCalculatorsFactory.forwardCurrencyDateCalculator(CalculatorConstants.USD_CODE, "JOD",
                SpotLag.T_2);

        final LocalDate startDate = new LocalDate(2006, 7, 6); // Thursday
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects Tuesday 11 Jul");
    }

    private static void simpleUsdAed() {
        final LocalDateCurrencyDateCalculator calc = LocalDateKitCalculatorsFactory.forwardCurrencyDateCalculator(CalculatorConstants.USD_CODE, "AED",
                SpotLag.T_2);

        final LocalDate startDate = new LocalDate(2006, 7, 6); // Thursday
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects Monday 10 Jul");
    }

    private static void crossGbpJpyWithHolidays() {
        final Set<LocalDate> gbpHholidays = new HashSet<LocalDate>();
        gbpHholidays.add(new LocalDate("2014-08-04"));
        final HolidayCalendar<LocalDate> gbpCalendar = new DefaultHolidayCalendar<LocalDate>(gbpHholidays, new LocalDate("2014-01-01"),
                new LocalDate("2014-12-31"));
        LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("GBP", gbpCalendar);

        final Set<LocalDate> jpyHolidays = new HashSet<LocalDate>();
        jpyHolidays.add(new LocalDate("2014-08-05"));
        final HolidayCalendar<LocalDate> jpyCalendar = new DefaultHolidayCalendar<LocalDate>(jpyHolidays, new LocalDate("2014-01-01"),
                new LocalDate("2014-12-31"));
        LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("JPY", jpyCalendar);

        final LocalDateCurrencyDateCalculator calc = LocalDateKitCalculatorsFactory.forwardCurrencyDateCalculator("GBP", "JPY", SpotLag.T_2);

        // set startDate, this will also set the current business date.
        final LocalDate startDate = new LocalDate(2014, 8, 1); // Friday
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects 6 Aug");
    }

    private static void crossEurMxn() {
        final Set<LocalDate> holidays = new HashSet<LocalDate>();
        holidays.add(new LocalDate("2006-07-04"));
        // create the HolidayCalendar ASSUMING that the set covers 2006!
        final HolidayCalendar<LocalDate> usdCalendar = new DefaultHolidayCalendar<LocalDate>(holidays, new LocalDate("2006-01-01"),
                new LocalDate("2006-12-31"));

        final CurrencyDateCalculatorBuilder<LocalDate> builder = new CurrencyDateCalculatorBuilder<LocalDate>() //
                .currencyPair("EUR", "MXN", SpotLag.T_2) //
                .currencyCalculatorConfig(new DefaultCurrencyCalculatorConfig())//
                .crossCcyCalendar(usdCalendar) //
                .tenorHolidayHandler(new LocalDateForwardHandler());

        final LocalDateCurrencyDateCalculator calc = new LocalDateCurrencyDateCalculator(builder);

        LocalDate startDate = new LocalDate(2006, 6, 30);
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects 5 Jul");
        startDate = new LocalDate(2006, 7, 2);
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects 6 Jul");
        startDate = new LocalDate(2006, 7, 3);
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects 6 Jul");
    }

    private static void simpleEurUsd() {
        final Set<LocalDate> holidays = new HashSet<LocalDate>();
        holidays.add(new LocalDate("2006-07-04"));
        // create the HolidayCalendar ASSUMING that the set covers 2006!
        final HolidayCalendar<LocalDate> usdCalendar = new DefaultHolidayCalendar<LocalDate>(holidays, new LocalDate("2006-01-01"),
                new LocalDate("2006-12-31"));

        final CurrencyDateCalculatorBuilder<LocalDate> builder = new CurrencyDateCalculatorBuilder<LocalDate>() //
                .currencyPair("EUR", CalculatorConstants.USD_CODE, SpotLag.T_2) //
                .currencyCalculatorConfig(new DefaultCurrencyCalculatorConfig())//
                .crossCcyCalendar(usdCalendar) //
                .ccy2Calendar(usdCalendar)//
                .tenorHolidayHandler(new LocalDateForwardHandler());

        final LocalDateCurrencyDateCalculator calc = new LocalDateCurrencyDateCalculator(builder);

        LocalDate startDate = new LocalDate(2006, 6, 30);
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects 5 Jul");
        startDate = new LocalDate(2006, 7, 2);
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects 5 Jul");
        startDate = new LocalDate(2006, 7, 3);
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects 5 Jul");
    }

    private static void simpleAudUsd() {
        final Set<LocalDate> holidays = new HashSet<LocalDate>();
        holidays.add(new LocalDate("2015-01-19"));
        final HolidayCalendar<LocalDate> usdCalendar = new DefaultHolidayCalendar<LocalDate>(holidays, new LocalDate("2006-01-01"),
                new LocalDate("2015-12-31"));

        final CurrencyDateCalculatorBuilder<LocalDate> builder = new CurrencyDateCalculatorBuilder<LocalDate>() //
                .currencyPair("AUD", CalculatorConstants.USD_CODE, SpotLag.T_2) //
                .currencyCalculatorConfig(new DefaultCurrencyCalculatorConfig())//
                .ccy2Calendar(usdCalendar)//
                .tenorHolidayHandler(new LocalDateForwardHandler());

        final LocalDateCurrencyDateCalculator calc = new LocalDateCurrencyDateCalculator(builder);

        LocalDate startDate = new LocalDate(2015, 1, 16);
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects 20 Jan");
    }

    private static void simpleEurUsd1M() {
        final LocalDateCurrencyDateCalculator calc = LocalDateKitCalculatorsFactory.forwardCurrencyDateCalculator("EUR", "USD", SpotLag.T_2);

        LocalDate startDate = new LocalDate(2006, 6, 26);
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects 30 Jun");
        System.out.println(
                calc.getName() + " TD: " + startDate + " 1-M  " + calc.calculateTenorDate(startDate, StandardTenor.T_1M) + " expects 30 Jul");
    }

    private static void crossEurGbp() {
        final Set<LocalDate> holidays = new HashSet<LocalDate>();
        holidays.add(new LocalDate("2006-07-04"));
        // create the HolidayCalendar ASSUMING that the set covers 2006!
        final HolidayCalendar<LocalDate> usdCalendar = new DefaultHolidayCalendar<LocalDate>(holidays, new LocalDate("2006-01-01"),
                new LocalDate("2006-12-31"));

        final CurrencyDateCalculatorBuilder<LocalDate> builder = new CurrencyDateCalculatorBuilder<LocalDate>() //
                .currencyPair("EUR", "GBP", SpotLag.T_2) //
                .currencyCalculatorConfig(new DefaultCurrencyCalculatorConfig())//
                .crossCcyCalendar(usdCalendar) //
                .tenorHolidayHandler(new LocalDateForwardHandler());

        final LocalDateCurrencyDateCalculator calc = new LocalDateCurrencyDateCalculator(builder);

        LocalDate startDate = new LocalDate(2006, 7, 2);
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects 5 Jul");
        startDate = new LocalDate(2006, 6, 30);
        System.out.println(calc.getName() + " TD: " + startDate + " Spot " + calc.calculateSpotDate(startDate) + " expects 5 Jul");
    }
}
