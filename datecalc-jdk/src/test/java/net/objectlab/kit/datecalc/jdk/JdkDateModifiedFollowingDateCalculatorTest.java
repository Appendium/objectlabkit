package net.objectlab.kit.datecalc.jdk;

import net.objectlab.kit.datecalc.common.AbstractModifiedFollowingDateCalculatorTest;
import net.objectlab.kit.datecalc.common.KitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.Utils;

public class JdkDateModifiedFollowingDateCalculatorTest extends AbstractModifiedFollowingDateCalculatorTest {

    @Override
    protected Object newDate(final String date) {
        return Utils.createDate(date);
    }

    @Override
    protected KitCalculatorsFactory getDateCalculatorFactory() {
        return DateKitCalculatorsFactory.getDefaultInstance();
    }

}
