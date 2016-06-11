/**
 *
 */
package net.objectlab.kit.util;

/**
 * A simple helper class that is NOT SCIENTIFIC but accurate enough
 * for the mere mortals, to calculate the number of milliseconds to
 * be used in say a timer but setup in a easier way...
 * @author Benoit Xhenseval
 *
 */
public class PeriodBuilder {
    private static final long MS_IN_1_SEC = 1_000L;
    private static final long S_IN_1_MIN = 60L;
    private static final long M_IN_1_HOUR = 60L;
    private static final long H_IN_1_DAY = 24L;
    private static final long D_IN_1_WEEK = 7L;
    private int weeks;
    private int days;
    private int hours;
    private int minutes;
    private int seconds;
    private long milliseconds;

    public long calculateMilliseconds() {
        return milliseconds //
                + seconds * MS_IN_1_SEC //
                + minutes * S_IN_1_MIN * MS_IN_1_SEC //
                + hours * M_IN_1_HOUR * S_IN_1_MIN * MS_IN_1_SEC //
                + days * H_IN_1_DAY * M_IN_1_HOUR * S_IN_1_MIN * MS_IN_1_SEC //
                + weeks * D_IN_1_WEEK * H_IN_1_DAY * M_IN_1_HOUR * S_IN_1_MIN * MS_IN_1_SEC //
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
