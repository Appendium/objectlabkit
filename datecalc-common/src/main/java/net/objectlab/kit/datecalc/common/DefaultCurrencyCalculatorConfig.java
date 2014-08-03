package net.objectlab.kit.datecalc.common;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Default for MXN, CLP, ARS (Mexican Peso, Chile Pese and Argentina Peso).
 * @author Benoit Xhenseval
 */
public class DefaultCurrencyCalculatorConfig implements CurrencyCalculatorConfig {
    private Set<String> currenciesSubjectToUSDForT1 = new HashSet<String>();

    public DefaultCurrencyCalculatorConfig() {
        super();
        currenciesSubjectToUSDForT1.add("MXN");
        currenciesSubjectToUSDForT1.add("CLP");
        currenciesSubjectToUSDForT1.add("ARS");
    }

    public void setCurrenciesSubjectToUSDForT1(final Set<String> currenciesSubjectToUSDForT1) {
        final Set<String> copy = new HashSet<String>();
        copy.addAll(currenciesSubjectToUSDForT1);
        this.currenciesSubjectToUSDForT1 = copy;
    }

    public Set<String> getCurrenciesSubjectToUSDForT1() {
        return Collections.unmodifiableSet(currenciesSubjectToUSDForT1);
    }

    public boolean isSubjectToUSDForT1(final String ccy) {
        return currenciesSubjectToUSDForT1.contains(ccy);
    }
}
