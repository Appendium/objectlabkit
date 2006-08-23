package net.objectlab.kit.datecalc.common;

import java.util.List;
import java.util.Set;


public interface DateCalculator<E> {

    /**
     * @return Calendar name (Typically the name associated with the holiday
     *         set).
     */
    public String getName();

    /**
     * @param startDate
     *            the reference date for this calendar, the current date is also
     *            updated and may be moved if it falls on a non working day
     *            (holiday/weekend).
     */
    public void setStartDate(final E startDate);

    /**
     * @return startDate the reference date for this calendar.
     */
    public E getStartDate();

    /**
     * @param currentDate held by the calendar.
     */
    public E getCurrentDate();

    /**
     * is the given date on a weekend, according to the WorkingWeek
     */
    public boolean isWeekend(final E date);

    /**
     * is the given date a non working day, i.e. either a "weekend" or a
     * holiday?
     */
    public boolean isNonWorkingDay(final E date);

    /**
     * @return true if the current date is either a weekend or a holiday.
     */
    public boolean isCurrentDateNonWorking();

    /**
     * This is typically used at the construction of a DateCalculator.
     * 
     * @param holidays
     *            the holiday (if null, an empty set will be put in place)
     */
    public void setNonWorkingDays(final Set<E> holidays);

    /**
     * @return an immutable copy of the holiday set.
     */
    public Set<E> getNonWorkingDays();

    /**
     * Allow user to define what their Working Week should be (default is
     * Mon-Fri).
     * 
     * @param week
     */
    public void setWorkingWeek(final WorkingWeek week);

    /**
     * give a current business date which may be moved if it falls on a non
     * working day.
     * 
     * @param date
     * @return new current date if moved.
     */
    public E setCurrentBusinessDate(final E date);

    /**
     * @return the holiday handler type, can be null
     */
    public String getHolidayHandlerType();

    /**
     * move the current date by the number of days and, if it falls on a weekend
     * or holiday, move it according to the HolidayHandler given in this
     * DateCalculator.
     * 
     * @param days
     *            number of day
     * @return the businessCalendar (so one can do
     *         calendar.moveByDays(-2).getCurrentBusinessDate();)
     */
    public DateCalculator<E> moveByDays(final int days);

    /**
     * move the current date by a number of business days, this means that if a
     * date is either a 'weekend' or holiday, it will be skipped acording to the
     * holiday handler and not count towards the number of days to move.
     * 
     * @param businessDays
     * @return the current businessCalendar (so one can do
     *         calendar.moveByBusinessDays(2).getCurrentBusinessDate();)
     */
    public DateCalculator<E> moveByBusinessDays(final int businessDays);

    /**
     * By combining several calendars, we take into account several set of
     * holidays.
     * 
     * @param calendar
     * @return a new DateCalculator
     */
    public DateCalculator<E> combine(DateCalculator<E> calendar);

    /**
     * move the current date by a given tenor, this means that if a date is
     * either a 'weekend' or holiday, it will be skipped acording to the holiday
     * handler and not count towards the number of days to move.
     * 
     * @param businessDays
     * @return the current businessCalendar (so one can do
     *         calendar.moveByTenor(StandardTenor.T_2M).getCurrentBusinessDate();)
     */
    public DateCalculator<E> moveByTenor(final Tenor tenor);

    /**
     * @return the next IMMDate based on current date.
     */
    public E getNextIMMDate();

    /**
     * @return the previous IMMDate based on current date.
     */
    public E getPreviousIMMDate();

    /**
     * Returns a list of IMM dates between 2 dates, it will exclude the start
     * date if it is an IMM date but would include the end date if it is an IMM.
     * 
     * @param start
     *            start of the interval, excluded
     * @param end
     *            end of the interval, may be included.
     * @return list of IMM dates
     */
    public List<E> getIMMDates(final E start, final E end);

}