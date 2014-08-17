package net.objectlab.kit.datecalc.common;

public interface BaseCalculator<E> extends NonWorkingDayChecker<E> {
    /**
     * Gives the current business date held by the calculator.
     *
     * @return a date.
     */
    E getCurrentBusinessDate();

    /**
     * return the current increment in the calculator, this is used by the
     * handler.
     */
    int getCurrentIncrement();
}
