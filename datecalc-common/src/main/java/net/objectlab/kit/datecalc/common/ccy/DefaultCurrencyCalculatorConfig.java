package net.objectlab.kit.datecalc.common.ccy;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.objectlab.kit.datecalc.common.WorkingWeek;

/**
 * Default for currencies subject to USD Holidays for T+1: MXN, CLP, ARS (Mexican Peso, Chile Pese and Argentina Peso).
 * Arab currencies Sun-Thu: AED, BHD, EGP, KWD, OMR, QAR.
 * Arab currencies Mon-Thu: SAR, JOD.
 *
 * @author Benoit Xhenseval
 */
public class DefaultCurrencyCalculatorConfig implements CurrencyCalculatorConfig {
    private Set<String> currenciesSubjectToUSDForT1 = new HashSet<String>();
    private Map<String, WorkingWeek> workingWeeks = new HashMap<String, WorkingWeek>();

    public DefaultCurrencyCalculatorConfig() {
        super();
        currenciesSubjectToUSDForT1.add("MXN");
        currenciesSubjectToUSDForT1.add("CLP");
        currenciesSubjectToUSDForT1.add("ARS");
        workingWeeks.put("AED", WorkingWeek.ARAB_WEEK);
        workingWeeks.put("BHD", WorkingWeek.ARAB_WEEK);
        workingWeeks.put("EGP", WorkingWeek.ARAB_WEEK);
        workingWeeks.put("KWD", WorkingWeek.ARAB_WEEK);
        workingWeeks.put("OMR", WorkingWeek.ARAB_WEEK);
        workingWeeks.put("QAR", WorkingWeek.ARAB_WEEK);
        workingWeeks.put("SAR", WorkingWeek.ARAB_WEEK.intersection(WorkingWeek.DEFAULT));
        workingWeeks.put("JOD", WorkingWeek.ARAB_WEEK.intersection(WorkingWeek.DEFAULT));
    }

    public void setCurrenciesSubjectToUSDForT1(final Set<String> currenciesSubjectToUSDForT1) {
        final Set<String> copy = new HashSet<String>();
        copy.addAll(currenciesSubjectToUSDForT1);
        this.currenciesSubjectToUSDForT1 = copy;
    }

    public void setWorkingWeeks(final Map<String, WorkingWeek> workingWeeks) {
        this.workingWeeks = workingWeeks;
    }

    public Set<String> getCurrenciesSubjectToUSDForT1() {
        return Collections.unmodifiableSet(currenciesSubjectToUSDForT1);
    }

    public boolean isSubjectToUSDForT1(final String ccy) {
        return currenciesSubjectToUSDForT1.contains(ccy);
    }

    /**
     * Return a default Mon-Fri for most, but some might be Sun-Thu (Arab countries).
     * @param currency
     * @return the WorkingWeek registered for this currency other the default Mon-Fri.
     */
    public WorkingWeek getWorkingWeek(final String currency) {
        final WorkingWeek workingWeek = workingWeeks.get(currency);
        return workingWeek != null ? workingWeek : WorkingWeek.DEFAULT;
    }

    public Map<String, WorkingWeek> getWorkingWeeks() {
        return workingWeeks;
    }
}
