package net.objectlab.kit.datecalc.joda;

import java.util.Set;

import net.objectlab.kit.datecalc.common.AbstractDateCalculatorCombinationTest;
import net.objectlab.kit.datecalc.common.KitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.WorkingWeek;

import org.joda.time.YearMonthDay;

public class YearMonthDayDateCalculatorCombinationTest extends AbstractDateCalculatorCombinationTest<YearMonthDay> {

    @Override
    protected YearMonthDay newDate(final String date) {
        return new YearMonthDay(date);
    }

    @Override
    protected void registerHolidays(final String name, final Set<YearMonthDay> holidays) {
        YearMonthDayKitCalculatorsFactory.getDefaultInstance().registerHolidays(name, holidays);
    }

    @Override
    protected WorkingWeek getWorkingWeek(final WorkingWeek ww) {
        return new JodaWorkingWeek(ww);
    }

    @Override
    protected KitCalculatorsFactory<YearMonthDay> getDateCalculatorFactory() {
        return YearMonthDayKitCalculatorsFactory.getDefaultInstance();
    }
}
