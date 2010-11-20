/**
 * 
 */
package net.objectlab.kit.util;

/**
 * A simple helper class that is NOT SCIENTIFIC but accurate enough
 * for the mere mortals, to calculate the number of milliseconds to
 * be used in say a timer but setup in a easier way...
 * @author xhensevalb
 *
 */
public class PeriodBuilder {
    private int weeks = 0;
    private int days = 0;
    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;
    private long milliseconds = 0;

    public long calculateMilliseconds() {
        return milliseconds //
                + seconds * 1000L //
                + minutes * 60L * 1000L //
                + hours * 60L * 60L * 1000L //
                + days * 24L * 60L * 60L * 1000L //
                + weeks * 7L * 24L * 60L * 60L * 1000L //
        ;
    }

    public PeriodBuilder weeks(final int weeks) {
        this.weeks = weeks;
        return this;
    }

    public PeriodBuilder days(final int days) {
        this.days = days;
        return this;
    }

    public PeriodBuilder hours(final int hours) {
        this.hours = hours;
        return this;
    }

    public PeriodBuilder minutes(final int minutes) {
        this.minutes = minutes;
        return this;
    }

    public PeriodBuilder seconds(final int seconds) {
        this.seconds = seconds;
        return this;
    }

    public PeriodBuilder milliseconds(final int milliseconds) {
        this.milliseconds = milliseconds;
        return this;
    }
}
