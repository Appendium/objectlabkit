package net.objectlab.kit.datecalc.common;

import java.util.List;

import net.objectlab.kit.datecalc.common.ccy.CurrencyCalculatorConfig;

/**
 * A DateCalculator specialised for a currency pair. Implementations should be thread safe and immutable.
 *
 * 'E' will be parameterized to be a Date-like class, i.e. java.util.Date or
 * java.util.Calendar (and LocalDate or YearMonthDay for Joda-time / JDK8).
 *
 * @author Benoit Xhenseval
 *
 * @param <E>
 *            a representation of a date, typically JDK: Date, Calendar;
 *            Joda:LocalDate, YearMonthDay; JDK8: LocalDate
 * @since 1.4.0
 */
public interface CurrencyDateCalculator<E> {
    /**
     * Calculate the Spot Date taking into account the working weeks, holidays and spot lag (T+1, T+2 etc), the 
     * calculator also takes into account special rules for Latin American Currencies or Arabic currencies.
     * @param startDate the start date which may be adjusted for the ccy pair if enabled.
     * @return the Spot Date
     */
    E calculateSpotDate(E startDate);

    /**
     * Calculate the Tenor Date from Spot Date taking into account the working weeks, holidays and spot lag (T+1, T+2 etc).
     * @param startDate the start date which may be adjusted for the ccy pair if enabled.
     * @param tenor e.g. 1M
     * @return the Tenor Date
     */
    E calculateTenorDate(E startDate, Tenor tenor);

    /**
     * Calculate a list of Tenor Dates from Spot Date taking into account the working weeks, holidays and spot lag (T+1, T+2 etc).
     * @param startDate the start date which may be adjusted for the ccy pair if enabled.
     * @param tenors the list of tenors ('chronological order is not mandatory)
     * @return the Tenor Dates (same order as the list of Tenors)
     */
    List<E> calculateTenorDates(E startDate, List<Tenor> tenors);

    String getName();

    String getCcy1();

    ReadOnlyHolidayCalendar<E> getCcy1Calendar();

    WorkingWeek getCcy1Week();

    String getCcy2();

    ReadOnlyHolidayCalendar<E> getCcy2Calendar();

    WorkingWeek getCcy2Week();

    /**
     * If enabled show the CrossCcy calendar that may be used on Spot or Tenor dates (in some cases, it is required that GBP/EUR AND USD [the
     * crossCcy] have all a working day in common for the Spot/Tenor date).  This is configurable {@link CurrencyDateCalculatorBuilder#brokenDateAllowed(boolean)}.
     */
    ReadOnlyHolidayCalendar<E> getCrossCcyCalendar();

    /**
     * The cross currency used by this calculator.
     */
    String getCrossCcy();

    WorkingWeek getCrossCcyWeek();

    /**
     * @return the Spot Lag (0,1,2) used by this calculator.
     */
    SpotLag getSpotLag();

    /**
     * @return true if the calculator adjusts the start date based on the working week and holidays for the currency pair (not the crossCcy).
     * {@link CurrencyDateCalculatorBuilder#adjustStartDateWithCurrencyPair(boolean)}
     */
    boolean isAdjustStartDateWithCurrencyPair();

    /**
     * @return true if the calculator allows a broken date (i.e. a Spot or Tenor date that can fall on a weekend or holiday for the crossCcy).
     * {@link CurrencyDateCalculatorBuilder#brokenDateAllowed(boolean)}
     */
    boolean isBrokenDateAllowed();

    /**
     * @return true if the calculator is using the CrossCcy holidays for T+1 for Ccy1.
     * {@link CurrencyCalculatorConfig#getCurrenciesSubjectToCrossCcyForT1(String)}
     */
    boolean isUseCrossCcyOnT1ForCcy1();

    /**
     * @return true if the calculator is using the CrossCcy holidays for T+1 for Ccy2.
     * {@link CurrencyCalculatorConfig#getCurrenciesSubjectToCrossCcyForT1(String)}
     */
    boolean isUseCrossCcyOnT1ForCcy2();
}
