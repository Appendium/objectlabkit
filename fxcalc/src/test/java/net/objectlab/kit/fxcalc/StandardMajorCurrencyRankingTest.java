package net.objectlab.kit.fxcalc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StandardMajorCurrencyRankingTest {
    private final MajorCurrencyRanking majorCurrencyRanking = StandardMajorCurrencyRanking.getDefault();

    @Test
    public void testSelectMajorCurrency() throws Exception {
        assertThat(majorCurrencyRanking.selectMajorCurrency("XXX", "USD")).isEqualTo("USD");
        assertThat(majorCurrencyRanking.selectMajorCurrency("EUR", "USD")).isEqualTo("EUR");
        assertThat(majorCurrencyRanking.selectMajorCurrency("USD", "EUR")).isEqualTo("EUR");
        assertThat(majorCurrencyRanking.selectMajorCurrency("GBP", "EUR")).isEqualTo("EUR");
        assertThat(majorCurrencyRanking.selectMajorCurrency("CAD", "USD")).isEqualTo("USD");
        assertThat(majorCurrencyRanking.selectMajorCurrency("JPY", "CHF")).isEqualTo("CHF");
        assertThat(majorCurrencyRanking.selectMajorCurrency("JPY", "XXX")).isEqualTo("JPY");
        assertThat(majorCurrencyRanking.selectMajorCurrency("JPY", "GBP")).isEqualTo("GBP");
        assertThat(majorCurrencyRanking.selectMajorCurrency("AED", "JOD")).isEqualTo("AED");
        assertThat(majorCurrencyRanking.selectMajorCurrency("JOD", "AED")).isEqualTo("JOD");
        assertThat(majorCurrencyRanking.selectMajorCurrency("NOK", "SEK")).isEqualTo("NOK");
        assertThat(majorCurrencyRanking.selectMajorCurrency("SEK", "NOK")).isEqualTo("NOK");
        assertThat(majorCurrencyRanking.selectMajorCurrency("NOK", "JPY")).isEqualTo("NOK");
        assertThat(majorCurrencyRanking.selectMajorCurrency("JPY", "SEK")).isEqualTo("SEK");
    }
}
