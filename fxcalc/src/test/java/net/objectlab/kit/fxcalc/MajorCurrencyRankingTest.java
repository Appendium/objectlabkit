package net.objectlab.kit.fxcalc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MajorCurrencyRankingTest {

    @Test
    public void testFindMajorCurrency() throws Exception {
        assertThat(MajorCurrencyRanking.findMajorCurrency("XXX", "USD")).isEqualTo("USD");
        assertThat(MajorCurrencyRanking.findMajorCurrency("EUR", "USD")).isEqualTo("EUR");
        assertThat(MajorCurrencyRanking.findMajorCurrency("USD", "EUR")).isEqualTo("EUR");
        assertThat(MajorCurrencyRanking.findMajorCurrency("GBP", "EUR")).isEqualTo("EUR");
        assertThat(MajorCurrencyRanking.findMajorCurrency("CAD", "USD")).isEqualTo("USD");
        assertThat(MajorCurrencyRanking.findMajorCurrency("JPY", "CHF")).isEqualTo("CHF");
        assertThat(MajorCurrencyRanking.findMajorCurrency("JPY", "XXX")).isEqualTo("JPY");
        assertThat(MajorCurrencyRanking.findMajorCurrency("JPY", "GBP")).isEqualTo("GBP");
        assertThat(MajorCurrencyRanking.findMajorCurrency("AED", "JOD")).isEqualTo("AED");
        assertThat(MajorCurrencyRanking.findMajorCurrency("JOD", "AED")).isEqualTo("JOD");
    }
}
