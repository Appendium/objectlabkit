package net.objectlab.kit.datecalc.jdk;

import java.util.Date;

import net.objectlab.kit.datecalc.common.AbstractIMMDateTest;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.Utils;

public class IMMDateTest extends AbstractIMMDateTest<Date> {

    @Override
    protected DateCalculator<Date> getDateCalculator(final String name) {
        return DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator(name, HolidayHandlerType.FORWARD);
    }

    @Override
    protected Date parseDate(final String string) {
        return Utils.createDate(string);
    }

}