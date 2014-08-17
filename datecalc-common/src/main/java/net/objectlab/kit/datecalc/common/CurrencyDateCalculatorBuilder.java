package net.objectlab.kit.datecalc.common;

import net.objectlab.kit.datecalc.common.ccy.CurrencyCalculatorConfig;

public class CurrencyDateCalculatorBuilder<E> {
    private String ccy1;
    private String ccy2;
    private HolidayCalendar<E> ccy1Calendar = new DefaultHolidayCalendar();
    private HolidayCalendar<E> ccy2Calendar = new DefaultHolidayCalendar();
    private HolidayCalendar<E> usdCalendar = new DefaultHolidayCalendar();
    private HolidayHandler<E> holidayHandler;
    private WorkingWeek ccy1Week;
    private WorkingWeek ccy2Week;
    private WorkingWeek usdWeek;
    private CurrencyCalculatorConfig currencyCalculatorConfig;
    private boolean useUsdNonWorkingDaysOnSpotDate = true;
    private boolean adjustStartDateWithCcy1Ccy2 = true;
    private SpotLag spotLag = SpotLag.T_2;

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
        if (useUsdNonWorkingDaysOnSpotDate) {
            if (getUsdWeek() == null) {
                append(b, "usdWeek is required");
            }
            if (getUsdCalendar() == null) {
                append(b, "usdCalendar is required");
            }
        }
        if (spotLag == null) {
            append(b, "spotLag is required");
        }
        if (holidayHandler == null) {
            append(b, "holidayHandler is required");
        }
        if (b.length() > 0) {
            throw new IllegalArgumentException(b.toString());
        }
    }

    private void append(StringBuilder b, String string) {
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

    public HolidayCalendar<E> getCcy1Calendar() {
        return ccy1Calendar;
    }

    public HolidayCalendar<E> getCcy2Calendar() {
        return ccy2Calendar;
    }

    public HolidayCalendar<E> getUsdCalendar() {
        return usdCalendar;
    }

    public HolidayHandler<E> getHolidayHandler() {
        return holidayHandler;
    }

    public WorkingWeek getCcy1Week() {
        return ccy1Week != null ? ccy1Week : currencyCalculatorConfig != null ? currencyCalculatorConfig.getWorkingWeek(ccy1) : null;
    }

    public WorkingWeek getCcy2Week() {
        return ccy2Week != null ? ccy2Week : currencyCalculatorConfig != null ? currencyCalculatorConfig.getWorkingWeek(ccy2) : null;
    }

    public WorkingWeek getUsdWeek() {
        return usdWeek != null ? usdWeek : currencyCalculatorConfig != null ? currencyCalculatorConfig
                .getWorkingWeek(CurrencyDateCalculator.USD_CODE) : null;
    }

    public CurrencyCalculatorConfig getCurrencyCalculatorConfig() {
        return currencyCalculatorConfig;
    }

    public boolean isUseUsdNonWorkingDaysOnSpotDate() {
        return useUsdNonWorkingDaysOnSpotDate;
    }

    public CurrencyDateCalculatorBuilder<E> currencyPair(final String ccy1, final String ccy2, final SpotLag spotLag) {
        this.ccy1 = ccy1;
        this.ccy2 = ccy2;
        this.spotLag = spotLag;
        return this;
    }

    public SpotLag getSpotLag() {
        return spotLag;
    }

    public boolean isAdjustStartDateWithCcy1Ccy2() {
        return adjustStartDateWithCcy1Ccy2;
    }

    public CurrencyDateCalculatorBuilder<E> adjustStartDateWithCcy1Ccy2(final boolean adjustStartDateWithCcy1Ccy2) {
        this.adjustStartDateWithCcy1Ccy2 = adjustStartDateWithCcy1Ccy2;
        return this;
    }

    public CurrencyDateCalculatorBuilder<E> useUsdNonWorkingDaysOnSpotDate(final boolean useUsdOnSpotDate) {
        this.useUsdNonWorkingDaysOnSpotDate = useUsdOnSpotDate;
        return this;
    }

    public CurrencyDateCalculatorBuilder<E> currencyCalculatorConfig(final CurrencyCalculatorConfig currencyCalculatorConfig) {
        this.currencyCalculatorConfig = currencyCalculatorConfig;
        return this;
    }

    public CurrencyDateCalculatorBuilder<E> ccy1Calendar(final HolidayCalendar<E> ccy1Calendar) {
        this.ccy1Calendar = ccy1Calendar;
        return this;
    }

    public CurrencyDateCalculatorBuilder<E> ccy2Calendar(final HolidayCalendar<E> ccy2Calendar) {
        this.ccy2Calendar = ccy2Calendar;
        return this;
    }

    public CurrencyDateCalculatorBuilder<E> usdCalendar(final HolidayCalendar<E> usdCalendar) {
        this.usdCalendar = usdCalendar;
        return this;
    }

    public CurrencyDateCalculatorBuilder<E> holidayHandler(final HolidayHandler<E> holidayHandler) {
        this.holidayHandler = holidayHandler;
        return this;
    }

    public CurrencyDateCalculatorBuilder<E> ccy1Week(final WorkingWeek ccy1Week) {
        this.ccy1Week = ccy1Week;
        return this;
    }

    public CurrencyDateCalculatorBuilder<E> ccy2Week(final WorkingWeek ccy2Week) {
        this.ccy2Week = ccy2Week;
        return this;
    }

    public CurrencyDateCalculatorBuilder<E> usdWeek(final WorkingWeek usdWeek) {
        this.usdWeek = usdWeek;
        return this;
    }
}
