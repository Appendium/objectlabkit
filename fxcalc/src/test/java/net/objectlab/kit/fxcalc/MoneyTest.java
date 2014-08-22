package net.objectlab.kit.fxcalc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class MoneyTest {
    @Test
    public void testEquals() {
        final Money money = Money.of("USD", 1L);
        final Money money2 = Money.of("USD", 1L);
        assertThat(money).isEqualTo(money2);
    }

    @Test
    public void testHash() {
        final Money money = Money.of("USD", 1L);
        final Money money2 = Money.of("USD", 1L);
        final Set<Money> s = new HashSet<>();
        s.add(money);
        s.add(money2);
        assertThat(s).hasSize(1);
    }

    @Test
    public void testNegate() {
        final Money money = Money.of("USD", 1L);

        final Money money3 = money.negate();
        assertThat(money3).isNotSameAs(money);
        assertThat(money3).isEqualTo(Money.of("USD", -1L));
    }

    @Test
    public void testAdd() {
        final Money money = Money.of("USD", 1L);
        final Money money2 = Money.of("USD", 10L);

        final Money money3 = money.add(money2);
        assertThat(money3).isNotSameAs(money);
        assertThat(money3).isEqualTo(Money.of("USD", 11L));
        final Money money4 = money2.add(money);
        assertThat(money4).isNotSameAs(money);
        assertThat(money4).isEqualTo(Money.of("USD", 11L));
    }

    @Test
    public void testSubtract() {
        final Money money = Money.of("USD", 1L);
        final Money money2 = Money.of("USD", 10L);

        final Money money3 = money.subtract(money2);
        assertThat(money3).isNotSameAs(money);
        assertThat(money3).isEqualTo(Money.of("USD", -9L));

        final Money money4 = money2.subtract(money);
        assertThat(money4).isNotSameAs(money);
        assertThat(money4).isEqualTo(Money.of("USD", 9));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAdd() {
        final Money money = Money.of("USD", 1L);
        final Money money2 = Money.of("EUR", 10L);

        money.add(money2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSubtract() {
        final Money money = Money.of("USD", 1L);
        final Money money2 = Money.of("EUR", 10L);

        money.subtract(money2);
    }
}
