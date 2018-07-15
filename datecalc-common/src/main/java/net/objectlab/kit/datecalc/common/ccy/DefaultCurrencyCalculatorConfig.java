package net.objectlab.kit.datecalc.common.ccy;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.objectlab.kit.datecalc.common.CalculatorConstants;
import net.objectlab.kit.datecalc.common.WorkingWeek;

/**
 * Default for:
 * <ul><li>currencies subject to USD Holidays for T+1: MXN, CLP, ARS (Mexican Peso, Chile Pese and Argentina Peso).</li>
 * <li>Arabic currencies Sun-Thu: AED, BHD, EGP, KWD, OMR, QAR.</li>
 * <li>Arabic currencies Mon-Thu: SAR, JOD.</li>
 * </ul>
 * @author Benoit Xhenseval
 * @since 1.4.0
 */
public class DefaultCurrencyCalculatorConfig implements CurrencyCalculatorConfig {
    private Map<String, Set<String>> currenciesSubjectToCrossCcyForT1 = new HashMap<String, Set<String>>();
    private Map<String, WorkingWeek> workingWeeks = new HashMap<String, WorkingWeek>();

    public DefaultCurrencyCalculatorConfig() {
        super();
        final Set<String> subjectToUsd = new HashSet<String>();
        subjectToUsd.add("MXN");
        subjectToUsd.add("CLP");
        subjectToUsd.add("ARS");
        currenciesSubjectToCrossCcyForT1.put(CalculatorConstants.USD_CODE, subjectToUsd);

        workingWeeks.put("AED", WorkingWeek.ARABIC_WEEK);
        workingWeeks.put("BHD", WorkingWeek.ARABIC_WEEK);
        workingWeeks.put("EGP", WorkingWeek.ARABIC_WEEK);
        workingWeeks.put("KWD", WorkingWeek.ARABIC_WEEK);
        workingWeeks.put("OMR", WorkingWeek.ARABIC_WEEK);
        workingWeeks.put("QAR", WorkingWeek.ARABIC_WEEK);
        workingWeeks.put("SAR", WorkingWeek.ARABIC_WEEK.intersection(WorkingWeek.DEFAULT));
        workingWeeks.put("JOD", WorkingWeek.ARABIC_WEEK.intersection(WorkingWeek.DEFAULT));
    }

    /**
     * Will take a copy of a non null set but doing so by replacing the internal one in one go for consistency.
     */
    public void setCurrenciesSubjectToCrossCcyForT1(final Map<String, Set<String>> currenciesSubjectToCrossCcyForT1) {
        final Map<String, Set<String>> copy = new HashMap<String, Set<String>>();
        if (currenciesSubjectToCrossCcyForT1 != null) {
            copy.putAll(currenciesSubjectToCrossCcyForT1);
        }
        this.currenciesSubjectToCrossCcyForT1 = copy;
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
    @Override
    public Set<String> getCurrenciesSubjectToCrossCcyForT1(final String crossCcy) {
        final Set<String> s = currenciesSubjectToCrossCcyForT1.get(crossCcy);
        return s != null ? Collections.unmodifiableSet(s) : Collections.<String> emptySet();
    }

    /**
     * Return a default Mon-Fri for most, but some might be Sun-Thu (Arabic countries).
     * @param currency
     * @return the WorkingWeek registered for this currency other the default Mon-Fri.
     */
    @Override
    public WorkingWeek getWorkingWeek(final String currency) {
        final WorkingWeek workingWeek = workingWeeks.get(currency);
        return workingWeek != null ? workingWeek : WorkingWeek.DEFAULT;
    }
}
