package net.objectlab.kit.datecalc.common;

import java.util.Collections;
import java.util.Set;

public abstract class AbstractDateCalculator<DateType> implements DateCalculator<DateType> {

    protected static final int MONTHS_IN_QUARTER = 3;
    protected static final int MONTH_IN_YEAR = 12;
    protected static final int DAYS_IN_WEEK = 7;
    protected String name;
    protected DateType startDate;
    protected DateType currentDate;
    protected Set<DateType> nonWorkingDays;
    protected HolidayHandler<DateType> holidayHandler;
    
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public DateType getStartDate() {
        return startDate;
    }

    /** Set both start date and current date */
    public void setStartDate(final DateType startDate) {
        this.startDate = startDate;
        setCurrentBusinessDate(startDate);
    }

    public DateType getCurrentDate() {
        return currentDate;
    }

    public Set<DateType> getNonWorkingDays() {
        return Collections.unmodifiableSet(nonWorkingDays);
    }
    
    public void setNonWorkingDays(final Set<DateType> holidays) {
        if (holidays == null) {
            nonWorkingDays = Collections.emptySet();
        } else {
            nonWorkingDays = holidays;
        }
    }

    /**
     * move the current date by a given tenor, this means that if a date is
     * either a 'weekend' or holiday, it will be skipped acording to the holiday
     * handler and not count towards the number of days to move.
     * 
     * @param businessDays
     * @return the current businessCalendar (so one can do
     *         calendar.moveByTenor(StandardTenor.T_2M).getCurrentBusinessDate();)
     */
    public DateCalculator<DateType> moveByTenor(final Tenor tenor) {
        if (tenor == null) {
            throw new IllegalArgumentException("Tenor cannot be null");
        }
        
        switch (tenor.getCode()) {
        case DAY:
            return moveByDays(tenor.getUnits());
        case WEEK:
            return moveByDays(tenor.getUnits() * DAYS_IN_WEEK);
        case IMM:
            setCurrentBusinessDate(getNextIMMDate());
            return this;
        default:
            throw new UnsupportedOperationException("Sorry not yet...");
        }
        
    }

    public void setHolidayHandler(final HolidayHandler<DateType> holidayHandler) {
        this.holidayHandler = holidayHandler;
    }

    public String getHolidayHandlerType() {
        return (holidayHandler != null ? holidayHandler.getType() : null);
    }

}
