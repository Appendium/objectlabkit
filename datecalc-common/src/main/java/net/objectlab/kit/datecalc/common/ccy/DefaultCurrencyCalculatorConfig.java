package net.objectlab.kit.datecalc.common.ccy;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.objectlab.kit.datecalc.common.WorkingWeek;

/**
 * Default for:
 * <ul><li>currencies subject to USD Holidays for T+1: MXN, CLP, ARS (Mexican Peso, Chile Pese and Argentina Peso).</li>
 * <li>Arab currencies Sun-Thu: AED, BHD, EGP, KWD, OMR, QAR.</li>
 * <li>Arab currencies Mon-Thu: SAR, JOD.</li>
 * </ul>
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

    /**
     * Will take a copy of a non null set but doing so by replacing the internal one in one go for consistency.
     */
    public void setCurrenciesSubjectToUSDForT1(final Set<String> currenciesSubjectToUSDForT1) {
        final Set<String> copy = new HashSet<String>();
        if (currenciesSubjectToUSDForT1 != null) {
            copy.addAll(currenciesSubjectToUSDForT1);
        }
        this.currenciesSubjectToUSDForT1 = copy;
    }

    /**
     * Will take a copy of a non null map but doing so by replacing the internal one in one go for consistency.
     */
    public void setWorkingWeeks(final Map<String, WorkingWeek> workingWeeks) {
        final Map<String, WorkingWeek> ww = new HashMap<String, WorkingWeek>();
        ww.putAll(workingWeeks);
        this.workingWeeks = ww;
    }

    /**
     * @return an unmodifiable set of currencies.
     */
    public Set<String> getCurrenciesSubjectToUSDForT1() {
        return Collections.unmodifiableSet(currenciesSubjectToUSDForT1);
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
}
