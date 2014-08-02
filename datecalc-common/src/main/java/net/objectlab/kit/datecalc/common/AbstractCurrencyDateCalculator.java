package net.objectlab.kit.datecalc.common;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractCurrencyDateCalculator<E> {
    private final DateCalculator<E> calculator;
    private HolidayCalendar<E> ccy1HolidayCalendar;
    private HolidayCalendar<E> ccy2HolidayCalendar;
    private HolidayCalendar<E> usdHolidayCalendar;
    private final String ccy1;
    private final String ccy2;

    public AbstractCurrencyDateCalculator(String ccy1, String ccy2, DateCalculator<E> calculator) {
        super();
        this.calculator = calculator;
        this.ccy1 = ccy1;
        this.ccy2 = ccy2;
    }

    protected DateCalculator<E> getCalculator() {
        return calculator;
    }

    public void setHolidayCalendars(final HolidayCalendar<E> ccy1HolidayCalendar, final HolidayCalendar<E> ccy2HolidayCalendar,
            final HolidayCalendar<E> usdHolidayCalendar) {
        this.ccy1HolidayCalendar = ccy1HolidayCalendar != null ? ccy1HolidayCalendar : new DefaultHolidayCalendar();
        this.ccy2HolidayCalendar = ccy2HolidayCalendar != null ? ccy2HolidayCalendar : new DefaultHolidayCalendar();
        this.usdHolidayCalendar = usdHolidayCalendar != null ? usdHolidayCalendar : new DefaultHolidayCalendar();
    }

    protected Set<String> getCurrenciesSubjectToUsdCalendar() {
        final Set<String> set = new HashSet<String>();
        set.add("MXN");
        set.add("ARS");
        set.add("CLP");
        return set;
    }

    private void applyNonUsdCalendars() {
        if (getCurrenciesSubjectToUsdCalendar().contains(ccy1) || getCurrenciesSubjectToUsdCalendar().contains(ccy2)) {
            applyCalendar(usdHolidayCalendar);
        }
        if (!"USD".equalsIgnoreCase(ccy1)) {
            applyCalendar(ccy1HolidayCalendar);
        }
        if (!"USD".equalsIgnoreCase(ccy2)) {
            applyCalendar(ccy2HolidayCalendar);
        }
    }

    public void applyCurrencyPairCalendars() {
        while (true) {
            E currentBusinessDate = calculator.getCurrentBusinessDate();
            if (calculator.isWeekend(currentBusinessDate) //
                    || ccy1HolidayCalendar.isHoliday(currentBusinessDate) //
                    || ccy2HolidayCalendar.isHoliday(currentBusinessDate) //
            ) {
                moveToNextWeekday();
            } else {
                break;
            }
        }
    }

    private void applyCalendar(final HolidayCalendar<E> holidayCalendar) {
        while (true) {
            if (calculator.isWeekend(calculator.getCurrentBusinessDate()) || holidayCalendar.isHoliday(calculator.getCurrentBusinessDate())) {
                moveToNextWeekday();
            } else {
                break;
            }
        }
    }

    protected abstract void moveToNextWeekday();

    public void applyAllCcyCalendars() {
        while (true) {
            E currentBusinessDate = calculator.getCurrentBusinessDate();
            if (calculator.isWeekend(calculator.getCurrentBusinessDate())  //
                    || ccy1HolidayCalendar.isHoliday(currentBusinessDate) //
                    || ccy2HolidayCalendar.isHoliday(currentBusinessDate) //
                    || usdHolidayCalendar.isHoliday(currentBusinessDate)) {
                moveToNextWeekday();
            } else {
                break;
            }
        }
    }

    public void moveToSpotDate(int spotLag) {
        if (spotLag == 0) {
            applyAllCcyCalendars();
        } else if (spotLag == 1) {
            moveToNextWeekday();
            applyAllCcyCalendars();
        } else {
            // Step 1) move date by 1, check date using calendar for non-USD ccy part
            // of the part AND USD calendar if one of the ccy is MXN/CLP/ARS. Move
            // fwd if required
            moveToNextWeekday();
            applyNonUsdCalendars();

            // Step 2) Move date by 1, check date using calendars for ccy pair AND
            // USD, move fwd if required
            moveToNextWeekday();
            applyAllCcyCalendars();
        }
    }
}
