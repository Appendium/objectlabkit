package net.objectlab.kit.datecalc.jdk;

import java.util.Date;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.Tenor;

public interface JdkDateCalculator extends DateCalculator<Date> {

    JdkDateCalculator moveByDays(final int days);

    JdkDateCalculator moveByBusinessDays(final int businessDays);

    JdkDateCalculator combine(DateCalculator<Date> calculator);

    JdkDateCalculator moveByTenor(final Tenor tenor);

}