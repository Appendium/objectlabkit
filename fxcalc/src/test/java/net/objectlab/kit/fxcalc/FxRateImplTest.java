package net.objectlab.kit.fxcalc;

import static org.assertj.core.api.Assertions.assertThat;
import net.objectlab.kit.util.BigDecimalUtil;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FxRateImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(FxRateImplTest.class);

    @Test
    public void testMid() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true);
        fx.setBid(BigDecimalUtil.bd("1.6"));
        fx.setAsk(BigDecimalUtil.bd("1.61"));
        assertThat(fx.getMid()).isEqualByComparingTo("1.605");
    }

    @Test
    public void testConversionMid() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true);
        fx.setBid(BigDecimalUtil.bd("1.6"));
        fx.setAsk(BigDecimalUtil.bd("1.61"));
        LOG.debug(fx.getDescription());

        assertThat(fx.convertAmountUsingMid(Money.of("EUR", 1_000))).isEqualTo(Money.of("USD", "1605"));
        assertThat(fx.convertAmountUsingMid(Money.of("USD", 1_000))).isEqualTo(Money.of("EUR", "623.05"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConversionMidWrongPair() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true);
        fx.setBid(BigDecimalUtil.bd("1.6"));
        fx.setAsk(BigDecimalUtil.bd("1.61"));
        LOG.debug(fx.getDescription());

        assertThat(fx.convertAmountUsingMid(Money.of("CAD", 1_000)));
    }

    @Test
    public void testConversionBidAsk() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true);
        fx.setBid(BigDecimalUtil.bd("1.6"));
        fx.setAsk(BigDecimalUtil.bd("1.61"));
        LOG.debug(fx.getDescription());

        assertThat(fx.convertAmountUsingBidOrAsk(Money.of("EUR", 1_000))).isEqualTo(Money.of("USD", "1600"));  // 1000 * 1.6
        assertThat(fx.convertAmountUsingBidOrAsk(Money.of("USD", 1_000))).isEqualTo(Money.of("EUR", "621.12"));  // 1000 * 1.6
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConversionBidOrAskWrongPair() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true);
        fx.setBid(BigDecimalUtil.bd("1.6"));
        fx.setAsk(BigDecimalUtil.bd("1.61"));
        LOG.debug(fx.getDescription());

        assertThat(fx.convertAmountUsingBidOrAsk(Money.of("CAD", 1_000)));
    }

}
