package net.objectlab.kit.datecalc.jdk;

import java.util.Date;

import net.objectlab.kit.datecalc.common.AbstractIMMDateTest;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;
import net.objectlab.kit.datecalc.common.Utils;

public class JdkDateIMMDateTest extends AbstractIMMDateTest<Date> {

    @Override
    protected IMMDateCalculator<Date> getDateCalculator(final String name) {
        return DateKitCalculatorsFactory.getDefaultInstance().getIMMDateCalculator();
    }

    @Override
    protected Date parseDate(final String string) {
        return Utils.createDate(string);
    }

}