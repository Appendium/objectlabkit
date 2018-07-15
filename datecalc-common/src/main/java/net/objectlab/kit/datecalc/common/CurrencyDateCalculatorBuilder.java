package net.objectlab.kit.datecalc.common;

import java.io.Serializable;

import net.objectlab.kit.datecalc.common.ccy.CurrencyCalculatorConfig;

/**
 * Provides enough information to create an immutable CurrencyDateCalculator.
 *
 * <pre>
    CurrencyDateCalculatorBuilder&lt;LocalDate&gt; builder = new CurrencyDateCalculatorBuilder&lt;LocalDate&gt;() //
            .currencyPair("EUR", "GBP", SpotLag.T_2) //
            .ccy1Calendar(new DefaultHolidayCalendar&lt;LocalDate&gt;()) // empty
            .ccy1Week(WorkingWeek.DEFAULT) // Mon-Fri
            .ccy2Calendar(gbpCalendar) //
            .ccy2Week(WorkingWeek.DEFAULT) // Mon-Fri
            .crossCcy("USD") // the usual suspect
            .crossCcyCalendar(usdCalendar) //
            .crossCcyWeek(WorkingWeek.DEFAULT) // Mon-Fri;
            .adjustStartDateWithCurrencyPair(true) // default is true, Move the startDate to a working date for ccy1 and ccy2
            .tenorHolidayHandler(new LocalDateForwardHandler()) // Forward (or equivalent for your implementation)
            .brokenDateAllowed(false) // use the CrossCcy holidays on Spot and Tenor Date
            .currencyCalculatorConfig(new DefaultCurrencyCalculatorConfig()) // Will be used for finding Working Weeks IF NOT PROVIDED and Latin
                                                                             // American ccy USD handling.
 * </pre>
 *
 * @param <E> JDK Date/Calendar, JDK8 LocalDate or Joda LocalDate
 * @since 1.4.0
 */
public class CurrencyDateCalculatorBuilder<E extends Serializable> {
    private String ccy1;
    private String ccy2;
    private String crossCcy = CalculatorConstants.USD_CODE;
    private HolidayCalendar<E> ccy1Calendar = new DefaultHolidayCalendar<E>();
    private HolidayCalendar<E> ccy2Calendar = new DefaultHolidayCalendar<E>();
    private HolidayCalendar<E> crossCcyCalendar = new DefaultHolidayCalendar<E>();
    private HolidayHandler<E> tenorHolidayHandler;
    private WorkingWeek ccy1Week;
    private WorkingWeek ccy2Week;
    private WorkingWeek crossCcyWeek;
    private CurrencyCalculatorConfig currencyCalculatorConfig;
    private boolean brokenDateAllowed;
    private boolean adjustStartDateWithCurrencyPair = true;
    private SpotLag spotLag = SpotLag.T_2;

    /**
     * Default values are:
     * <ul>
     * <li>crossCcy = USD</li>
     * <li>ccy1Calendar = Empty Calendar</li>
     * <li>ccy2Calendar = Empty Calendar</li>
     * <li>crossCcyCalendar = Empty Calendar</li>
     * <li>brokenDateAllowed = false</li>
     * <li>adjustStartDateWithCurrencyPair = true</li>
     * <li>spotLag = SpotLag.T_2</li>
     * </ul>
     */
    public CurrencyDateCalculatorBuilder() {
        // using default values ok
    }

    /**
     * Checks the builder and throws an IllegalArgumentException if there are issues e.g.
     * <ul>
     * <li>ccy1 or ccy2 missing</li>
     * <li>ccy1Week or ccy2Week missing</li>
     * <li>spotLag is missing</li>
     * <li>tenorHolidayHandler is missing</li>
     * <li>If brokenDate is not allowed, we need crossCcy, crossCcyWeek and crossCcyCalendar.</li>
     * </ul>
     */
    public void checkValidity() {
        StringBuilder b = new StringBuilder();
        if (ccy1 == null || ccy1.length() == 0) {
            b.append("ccy1 is required");
        }
        if (ccy2 == null || ccy2.length() == 0) {
            append(b, "ccy2 is required");
        }
        if (getCcy1Week() == null) {
            append(b, "ccy1Week is required");
        }
        if (getCcy2Week() == null) {
            append(b, "ccy2Week is required");
        }
        if (!brokenDateAllowed) {
            checkBrokenDate(b);
        }
        if (spotLag == null) {
            append(b, "spotLag is required");
        }
        if (tenorHolidayHandler == null) {
            append(b, "tenorHolidayHandler is required");
        }
        if (b.length() > 0) {
            throw new IllegalArgumentException(b.toString());
        }
    }

    private void checkBrokenDate(StringBuilder b) {
        if (getCrossCcy() == null) {
            append(b, "crossCcy is required");
        }
        if (getCrossCcyWeek() == null) {
            append(b, "crossCcyWeek is required");
        }
        if (getCrossCcyCalendar() == null) {
            append(b, "crossCcyCalendar is required");
        }
    }

    private static void append(StringBuilder b, String string) {
        if (b.length() > 0) {
            b.append(",");
        }
        b.append(string);
    }

    public String getCcy1() {
        return ccy1;
    }

    public String getCcy2() {
        return ccy2;
    }

    public String getCrossCcy() {
        return crossCcy;
    }

    public HolidayCalendar<E> getCcy1Calendar() {
        return ccy1Calendar;
    }

    public HolidayCalendar<E> getCcy2Calendar() {
        return ccy2Calendar;
    }

    public HolidayCalendar<E> getCrossCcyCalendar() {
        return crossCcyCalendar;
    }

    public HolidayHandler<E> getTenorHolidayHandler() {
        return tenorHolidayHandler;
    }

    public WorkingWeek getCcy1Week() {
        if (ccy1Week != null) {
            return ccy1Week;
        }
        return currencyCalculatorConfig != null ? currencyCalculatorConfig.getWorkingWeek(ccy1) : null;
    }

    public WorkingWeek getCcy2Week() {
        if (ccy2Week != null) {
            return ccy2Week;
        }
        return currencyCalculatorConfig != null ? currencyCalculatorConfig.getWorkingWeek(ccy2) : null;
    }

    public WorkingWeek getCrossCcyWeek() {
        if (crossCcyWeek != null) {
            return crossCcyWeek;
        }
        return currencyCalculatorConfig != null ? currencyCalculatorConfig.getWorkingWeek(crossCcy) : null;
    }

    public CurrencyCalculatorConfig getCurrencyCalculatorConfig() {
        return currencyCalculatorConfig;
    }

    public boolean isBrokenDateAllowed() {
        return brokenDateAllowed;
    }

    /**
     * This specialises the calculator to the given currency pair and the SpotLag (0, 1, 2). Given than some currencies can have different
     * SpotLag depending on the business, this is something that the user will have to provide.
     * @param ccy1
     * @param ccy2
     * @param spotLag
     * @return the builder
     */
    public CurrencyDateCalculatorBuilder<E> currencyPair(final String ccy1, final String ccy2, final SpotLag spotLag) {
        this.ccy1 = ccy1;
        this.ccy2 = ccy2;
        this.spotLag = spotLag;
        return this;
    }

    public SpotLag getSpotLag() {
        return spotLag;
    }

    public boolean isAdjustStartDateWithCurrencyPair() {
        return adjustStartDateWithCurrencyPair;
    }

    /**
     * If true, the startDate given to the calculator will be move to the NEXT working day for both currencies (e.g. it will skip weekends
     * and any holidays).
     * @param adjustStartDateWithCurrencyPair default true
     * @return the builder
     */
    public CurrencyDateCalculatorBuilder<E> adjustStartDateWithCurrencyPair(final boolean adjustStartDateWithCurrencyPair) {
        this.adjustStartDateWithCurrencyPair = adjustStartDateWithCurrencyPair;
        return this;
    }

    /**
     * If true, then the calculator can return a SpotDate/TenorDate where the cross currency is NOT a trading date (e.g. July 4 for EUR/GBP which
     * usually would be skipped).
     * @param brokenDateAllowed default false
     * @return the builder
     */
    public CurrencyDateCalculatorBuilder<E> brokenDateAllowed(final boolean brokenDateAllowed) {
        this.brokenDateAllowed = brokenDateAllowed;
        return this;
    }

    /**
     * Provides information about currencies subject to USD on T+1 and WorkingWeeks if not specified individually.
     * @param currencyCalculatorConfig the config
     * @return
     */
    public CurrencyDateCalculatorBuilder<E> currencyCalculatorConfig(final CurrencyCalculatorConfig currencyCalculatorConfig) {
        this.currencyCalculatorConfig = currencyCalculatorConfig;
        return this;
    }

    /**
     * The holiday calendar for ccy1, if null or not set, then a default calendar will be used with NO holidays.
     * @param ccy1Calendar the Calendar for ccy1
     * @return the builder
     */
    public CurrencyDateCalculatorBuilder<E> ccy1Calendar(final HolidayCalendar<E> ccy1Calendar) {
        if (ccy1Calendar != null) {
            this.ccy1Calendar = ccy1Calendar;
        }
        return this;
    }

    /**
     * The holiday calendar for ccy2, if null or not set, then a default calendar will be used with NO holidays.
     * @param ccy2Calendar the Calendar for ccy2
     * @return the builder
     */
    public CurrencyDateCalculatorBuilder<E> ccy2Calendar(final HolidayCalendar<E> ccy2Calendar) {
        if (ccy2Calendar != null) {
            this.ccy2Calendar = ccy2Calendar;
        }
        return this;
    }

    /**
     * If brokenDate is not allowed, we do require to check the WorkingWeek and Holiday for the crossCcy when
     * validating the SpotDate or a Tenor date; if null or not set, then a default calendar will be used with NO holidays.
     * @param crossCcy the crossCcy (default USD).
     * @return the builder
     */
    public CurrencyDateCalculatorBuilder<E> crossCcy(final String crossCcy) {
        this.crossCcy = crossCcy;
        return this;
    }

    /**
     * If brokenDate is not allowed, we do require to check the WorkingWeek and Holiday for the crossCcy when
     * validating the SpotDate or a Tenor date.
     * @param crossCcyCalendar the set of holidays for the crossCcy
     * @return the builder
     */
    public CurrencyDateCalculatorBuilder<E> crossCcyCalendar(final HolidayCalendar<E> crossCcyCalendar) {
        if (crossCcyCalendar != null) {
            this.crossCcyCalendar = crossCcyCalendar;
        }
        return this;
    }

    /**
     * Provides the holiday handler for the Tenor Date, note that Spot is ALWAYS using Forward.
     * @param holidayHandler the Handler to work out what to do if a Tenor Date falls on a non WorkingDay.
     * @return the builder
     */
    public CurrencyDateCalculatorBuilder<E> tenorHolidayHandler(final HolidayHandler<E> holidayHandler) {
        this.tenorHolidayHandler = holidayHandler;
        return this;
    }

    /**
     * Provides the definition of a working week for the currency; if not provided and the currencyCalculatorConfig is given, it 
     * will do a look up for this currency.
     * @param ccy1Week WorkingWeek definition
     * @return the builder
     */
    public CurrencyDateCalculatorBuilder<E> ccy1Week(final WorkingWeek ccy1Week) {
        this.ccy1Week = ccy1Week;
        return this;
    }

    /**
     * Provides the definition of a working week for the currency; if not provided and the currencyCalculatorConfig is given, it 
     * will do a look up for this currency.
     * @param ccy2Week WorkingWeek definition
     * @return the builder
     */
    public CurrencyDateCalculatorBuilder<E> ccy2Week(final WorkingWeek ccy2Week) {
        this.ccy2Week = ccy2Week;
        return this;
    }

    /**
     * If brokenDate is not allowed, we do require to check the WorkingWeek and Holiday for the crossCcy when
     * validating the SpotDate or a Tenor date.
     * @param crossCcyWeek the crossCcy WorkingWeek.
     * @return the builder
     */
    public CurrencyDateCalculatorBuilder<E> crossCcyWeek(final WorkingWeek crossCcyWeek) {
        this.crossCcyWeek = crossCcyWeek;
        return this;
    }
}
