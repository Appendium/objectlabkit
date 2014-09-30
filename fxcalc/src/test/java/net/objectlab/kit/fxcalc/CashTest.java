package net.objectlab.kit.fxcalc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class CashTest {
    @Test
    public void testEquals() {
        final Cash money = Cash.of("USD", 1L);
        final Cash money2 = Cash.of("USD", 1L);
        assertThat(money).isEqualTo(money2);
    }

    @Test
    public void testHash() {
        final Cash money = Cash.of("USD", 1L);
        final Cash money2 = Cash.of("USD", 1L);
        final Set<Cash> s = new HashSet<>();
        s.add(money);
        s.add(money2);
        assertThat(s).hasSize(1);
    }

    @Test
    public void testNegate() {
        final Cash money = Cash.of("USD", 1L);

        final CurrencyAmount money3 = money.negate();
        assertThat(money3).isNotSameAs(money);
        assertThat(money3).isEqualTo(Cash.of("USD", -1L));
    }

    @Test
    public void testAdd() {
        final Cash money = Cash.of("USD", 1L);
        final Cash money2 = Cash.of("USD", 10L);

        final CurrencyAmount money3 = money.add(money2);
        assertThat(money3).isNotSameAs(money);
        assertThat(money3).isEqualTo(Cash.of("USD", 11L));
        final CurrencyAmount money4 = money2.add(money);
        assertThat(money4).isNotSameAs(money);
        assertThat(money4).isEqualTo(Cash.of("USD", 11L));
    }

    @Test
    public void testSubtract() {
        final Cash money = Cash.of("USD", 1L);
        final Cash money2 = Cash.of("USD", 10L);

        final CurrencyAmount money3 = money.subtract(money2);
        assertThat(money3).isNotSameAs(money);
        assertThat(money3).isEqualTo(Cash.of("USD", -9L));

        final CurrencyAmount money4 = money2.subtract(money);
        assertThat(money4).isNotSameAs(money);
        assertThat(money4).isEqualTo(Cash.of("USD", 9));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAdd() {
        final Cash money = Cash.of("USD", 1L);
        final Cash money2 = Cash.of("EUR", 10L);

        money.add(money2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSubtract() {
        final Cash money = Cash.of("USD", 1L);
        final Cash money2 = Cash.of("EUR", 10L);

        money.subtract(money2);
    }
}
