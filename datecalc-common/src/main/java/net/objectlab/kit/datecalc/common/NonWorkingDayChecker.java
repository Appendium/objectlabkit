package net.objectlab.kit.datecalc.common;

public interface NonWorkingDayChecker<E> {

    /**
     * Is the given date a non working day, i.e. either a "weekend" or a
     * holiday?
     *
     * @return true if the given date is non-working.
     */
    boolean isNonWorkingDay(E date);

}
