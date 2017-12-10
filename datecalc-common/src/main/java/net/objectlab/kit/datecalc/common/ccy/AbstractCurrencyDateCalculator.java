package net.objectlab.kit.datecalc.common.ccy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.objectlab.kit.datecalc.common.CurrencyDateCalculator;
import net.objectlab.kit.datecalc.common.CurrencyDateCalculatorBuilder;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.ImmutableHolidayCalendar;
import net.objectlab.kit.datecalc.common.NonWorkingDayChecker;
import net.objectlab.kit.datecalc.common.ReadOnlyHolidayCalendar;
import net.objectlab.kit.datecalc.common.SpotLag;
import net.objectlab.kit.datecalc.common.Tenor;
import net.objectlab.kit.datecalc.common.TenorCode;
import net.objectlab.kit.datecalc.common.WorkingWeek;

/**
 * Abstract Currency calculator implementation in order to encapsulate all the common functionality
 * between Jdk/Jdk8 and Joda implementations. It is parameterized on &lt;E&gt;
 * but basically <code>Date</code> and <code>LocalDate</code> are the only
 * viable values for it for now.
 *
 * This follows convention for currency, see http://www.londonfx.co.uk/valdates.html
 *
 * <h3>Currency Holiday</h3>
 * For most T+2 currency pairs (spotLag=2), if T+1 is a USD holiday, then this does not normally affect the spot date,
 * but if a non-USD currency in the currency pair has a holiday on T+1, then it will make the spot date
 * become T+3. If USD or either currency of a pair have a holiday on T+2, then the spot date
 * will be T+3. This means, for example, that crosses such as EUR/GBP can never have a spot date
 * on 4th July (although such a date could be quoted as an outright).
 *
 * <h3>Latin American currencies</h3>
 * USD holidays normally affect the spot date only if T+2 is a USD holiday.
 * If T+1 is a USD holiday, this does not normally prevent T+2 from being the spot date.
 * Certain Latin American currencies (ARS, CLP and MXN) are an exception to this.
 * If T+1 is a USD holiday, then the spot date for the affected currencies will be T+3.
 * For example, if the trade date is a Monday and a USD holiday falls on the Tuesday,
 * then the spot date for EUR/USD will be the Wednesday, but the spot date for USD/MXN will be the Thursday.
 *
 * @since 1.4.0
 */
public abstract class AbstractCurrencyDateCalculator<E extends Serializable> implements CurrencyDateCalculator<E>, NonWorkingDayChecker<E> {
    private static final int MONTHS_IN_YEAR = 12;
    private static final int DAYS_IN_WEEK = 7;
    private final String ccy1;
    private final String ccy2;
    private final String crossCcy;
    private final HolidayCalendar<E> ccy1HolidayCalendar;
    private final HolidayCalendar<E> ccy2HolidayCalendar;
    private final HolidayCalendar<E> crossCcyHolidayCalendar;
    private final HolidayHandler<E> holidayHandler;
    private final WorkingWeek ccy1Week;
    private final WorkingWeek ccy2Week;
    private final WorkingWeek crossCcyWeek;
    private final boolean brokenDateAllowed;
    private final boolean useCrossCcyOnT1ForCcy1;
    private final boolean useCrossCcyOnT1ForCcy2;
    private final boolean adjustStartDateWithCcy1Ccy2;
    private final SpotLag spotLag;

    protected AbstractCurrencyDateCalculator(final CurrencyDateCalculatorBuilder<E> builder) {
        builder.checkValidity();
        this.ccy1 = builder.getCcy1();
        this.ccy2 = builder.getCcy2();
        this.crossCcy = builder.getCrossCcy();
        this.ccy1HolidayCalendar = new ImmutableHolidayCalendar<E>(
                builder.getCcy1Calendar() != null ? builder.getCcy1Calendar() : new DefaultHolidayCalendar<E>());
        this.ccy2HolidayCalendar = new ImmutableHolidayCalendar<E>(
                builder.getCcy2Calendar() != null ? builder.getCcy2Calendar() : new DefaultHolidayCalendar<E>());
        this.crossCcyHolidayCalendar = new ImmutableHolidayCalendar<E>(
                builder.getCrossCcyCalendar() != null ? builder.getCrossCcyCalendar() : new DefaultHolidayCalendar<E>());
        this.holidayHandler = builder.getTenorHolidayHandler();
        this.ccy1Week = builder.getCcy1Week();
        this.ccy2Week = builder.getCcy2Week();
        this.crossCcyWeek = builder.getCrossCcyWeek();
        this.brokenDateAllowed = builder.isBrokenDateAllowed();
        this.spotLag = builder.getSpotLag();
        this.adjustStartDateWithCcy1Ccy2 = builder.isAdjustStartDateWithCurrencyPair();
        this.useCrossCcyOnT1ForCcy1 = builder.getCurrencyCalculatorConfig() != null
                && builder.getCurrencyCalculatorConfig().getCurrenciesSubjectToCrossCcyForT1(crossCcy).contains(ccy1);
        this.useCrossCcyOnT1ForCcy2 = builder.getCurrencyCalculatorConfig() != null
                && builder.getCurrencyCalculatorConfig().getCurrenciesSubjectToCrossCcyForT1(crossCcy).contains(ccy2);
    }

    @Override
    public boolean isUseCrossCcyOnT1ForCcy1() {
        return useCrossCcyOnT1ForCcy1;
    }

    @Override
    public boolean isUseCrossCcyOnT1ForCcy2() {
        return useCrossCcyOnT1ForCcy2;
    }

    @Override
    public SpotLag getSpotLag() {
        return spotLag;
    }

    @Override
    public boolean isAdjustStartDateWithCurrencyPair() {
        return adjustStartDateWithCcy1Ccy2;
    }

    @Override
    public boolean isBrokenDateAllowed() {
        return brokenDateAllowed;
    }

    @Override
    public String getCrossCcy() {
        return crossCcy;
    }

    @Override
    public String getCcy1() {
        return ccy1;
    }

    @Override
    public String getCcy2() {
        return ccy2;
    }

    @Override
    public String getName() {
        return getCcy1() + "." + getCcy2();
    }

    @Override
    public ReadOnlyHolidayCalendar<E> getCcy1Calendar() {
        return ccy1HolidayCalendar;
    }

    @Override
    public ReadOnlyHolidayCalendar<E> getCcy2Calendar() {
        return ccy2HolidayCalendar;
    }

    @Override
    public ReadOnlyHolidayCalendar<E> getCrossCcyCalendar() {
        return crossCcyHolidayCalendar;
    }

    @Override
    public WorkingWeek getCcy1Week() {
        return ccy1Week;
    }

    @Override
    public WorkingWeek getCcy2Week() {
        return ccy2Week;
    }

    @Override
    public WorkingWeek getCrossCcyWeek() {
        return crossCcyWeek;
    }

    protected abstract E calculateNextDay(E date);

    protected abstract int calendarWeekDay(E date);

    protected abstract E max(E d1, E d2);

    private boolean isNonWorkingDay(final E date, final WorkingWeek ww, final HolidayCalendar<E> calendar) {
        return !ww.isWorkingDayFromCalendar(calendarWeekDay(date)) || calendar != null && calendar.isHoliday(date);
    }

    @Override
    public boolean isNonWorkingDay(final E date) {
        return isNonWorkingDay(date, ccy1Week, ccy1HolidayCalendar) || isNonWorkingDay(date, ccy2Week, ccy2HolidayCalendar)
                || !brokenDateAllowed && isNonWorkingDay(date, crossCcyWeek, crossCcyHolidayCalendar);
    }

    private E adjustToNextWorkingDateForCcyPairIfRequired(final E startDate) {
        E date = startDate;
        while (isNonWorkingDay(date, ccy1Week, ccy1HolidayCalendar) || isNonWorkingDay(date, ccy2Week, ccy2HolidayCalendar)) {
            date = calculateNextDay(date);
        }
        return date;
    }

    private E adjustToNextWorkingDateForCcyPairAndUsdIfRequired(final E startDate) {
        E date = startDate;
        while (isNonWorkingDay(date, crossCcyWeek, crossCcyHolidayCalendar) || isNonWorkingDay(date, ccy1Week, ccy1HolidayCalendar)
                || isNonWorkingDay(date, ccy2Week, ccy2HolidayCalendar)) {
            date = calculateNextDay(date);
        }
        return date;
    }

    private E calculateNextWorkingDay(final E startDate, final WorkingWeek ww, final HolidayCalendar<E> calendar) {
        E date = calculateNextDay(startDate);
        while (isNonWorkingDay(date, ww, calendar)) {
            date = calculateNextDay(date);
        }
        return date;
    }

    private E calculateNextWorkingDayIfRequired(final E startDate, final WorkingWeek ww, final HolidayCalendar<E> calendar) {
        E date = startDate;
        while (isNonWorkingDay(date, ww, calendar)) {
            date = calculateNextDay(date);
        }
        return date;
    }

    @Override
    public E calculateSpotDate(final E startDate) {
        E date = startDate;
        if (adjustStartDateWithCcy1Ccy2 || spotLag == SpotLag.T_0) {
            date = adjustToNextWorkingDateForCcyPairIfRequired(startDate);
        }

        // calculate Spot for ccy1
        final E spotCcy1 = calculateCcySpot(ccy1, date, ccy1Week, ccy1HolidayCalendar);

        // calculate Spot for ccy2
        final E spotCcy2 = calculateCcySpot(ccy2, date, ccy2Week, ccy2HolidayCalendar);

        // if spotCcy1 == spotCcy2 -> return it
        E spotDate = max(spotCcy1, spotCcy2);

        // if not, consider max and adjustToNextWorkingDateForCcyPairIfRequired
        if (brokenDateAllowed) {
            spotDate = adjustToNextWorkingDateForCcyPairIfRequired(spotDate);
        } else {
            spotDate = adjustToNextWorkingDateForCcyPairAndUsdIfRequired(spotDate);
        }

        return spotDate;
    }

    private E calculateCcySpot(final String ccy, final E date, final WorkingWeek workingWeek, final HolidayCalendar<E> calendar) {
        // calculate T+1
        E calcSpot = date;

        if (spotLag != SpotLag.T_0) {
            if (spotLag == SpotLag.T_2) {
                calcSpot = calculateNextWorkingDay(calcSpot, workingWeek, crossCcy.equalsIgnoreCase(ccy) ? null : calendar); // crossCcy
                // does
                // not
                // impact
                // T+1

                if (useCrossCcyOnT1ForCcy1 && ccy1.equals(ccy) || useCrossCcyOnT1ForCcy2 && ccy2.equals(ccy)) {
                    // move if USD is holiday
                    calcSpot = calculateNextWorkingDayIfRequired(calcSpot, crossCcyWeek, crossCcyHolidayCalendar);
                    // check that it is still ok for the original ccy
                    calcSpot = calculateNextWorkingDayIfRequired(calcSpot, workingWeek, calendar);
                }
            }

            // calculate T+2
            calcSpot = calculateNextWorkingDay(calcSpot, workingWeek, calendar);
        }
        return calcSpot;
    }

    @Override
    public E calculateTenorDate(final E startDate, final Tenor tenor) {
        if (tenor == null) {
            throw new IllegalArgumentException("Tenor cannot be null");
        }

        TenorCode tenorCode = tenor.getCode();
        E date = startDate;
        if (tenorCode != TenorCode.OVERNIGHT && tenorCode != TenorCode.TOM_NEXT /*&& spotLag != 0*/) {
            // get to the Spot date first:
            date = calculateSpotDate(date);
        }
        int unit = tenor.getUnits();
        if (tenorCode == TenorCode.WEEK) {
            tenorCode = TenorCode.DAY;
            unit *= DAYS_IN_WEEK;
        }

        if (tenorCode == TenorCode.YEAR) {
            tenorCode = TenorCode.MONTH;
            unit *= MONTHS_IN_YEAR;
        }

        return applyTenor(date, tenorCode, unit);
    }

    private E applyTenor(final E date, final TenorCode tenorCode, final int unit) {
        E calc = date;
        // move by tenor
        switch (tenorCode) {
        case OVERNIGHT:
            calc = adjustForCcyPairIfRequired(calc);
            break;
        case TOM_NEXT: // it would have NOT moved by
        case SPOT_NEXT:
            calc = calculateNextDay(calc);
            calc = adjustForCcyPairIfRequired(calc);
            break;
        case SPOT: // good as-is
            break;
        case DAY:
            for (int i = 0; i < unit; i++) {
                calc = calculateNextDay(calc);
            }
            calc = adjustForCcyPairIfRequired(calc);
            break;
        case MONTH:
            calc = addMonths(calc, unit);
            calc = adjustForCcyPairIfRequired(calc);
            break;
        default:
            throw new UnsupportedOperationException("Sorry not yet...");
        }
        return calc;
    }

    private E adjustForCcyPairIfRequired(final E startDate) {
        return holidayHandler.adjustDate(startDate, 1, this);
    }

    protected abstract E addMonths(E calc, int unit);

    @Override
    public List<E> calculateTenorDates(final E startDate, final List<Tenor> tenors) {
        final List<E> results = new ArrayList<E>();
        for (final Tenor tenor : tenors) {
            results.add(calculateTenorDate(startDate, tenor));
        }
        return results;
    }
}
