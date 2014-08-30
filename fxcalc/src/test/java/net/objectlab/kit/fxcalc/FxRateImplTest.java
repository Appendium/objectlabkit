package net.objectlab.kit.fxcalc;

import static net.objectlab.kit.util.BigDecimalUtil.bd;
import static org.assertj.core.api.Assertions.assertThat;
import net.objectlab.kit.util.BigDecimalUtil;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FxRateImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(FxRateImplTest.class);

    @Test
    public void testMid() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true, bd("1.6"), bd("1.61"));
        assertThat(fx.getMid()).isEqualByComparingTo("1.605");
    }

    @Test
    public void testConversionMid() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true, bd("1.6"), bd("1.61"));
        LOG.debug(fx.getDescription());

        assertThat(fx.convertAmountUsingMid(Money.of("EUR", 1_000))).isEqualTo(Money.of("USD", "1605"));
        assertThat(fx.convertAmountUsingMid(Money.of("USD", 1_000))).isEqualTo(Money.of("EUR", "623.05"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConversionMidWrongPair() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true, bd("1.6"), bd("1.61"));
        LOG.debug(fx.getDescription());

        assertThat(fx.convertAmountUsingMid(Money.of("CAD", 1_000)));
    }

    @Test
    public void testConversionBidAsk() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true, bd("1.6"), bd("1.61"));
        LOG.debug(fx.getDescription());

        assertThat(fx.convertAmountUsingBidOrAsk(Money.of("EUR", 1_000))).isEqualTo(Money.of("USD", "1600"));  // 1000 * 1.6
        assertThat(fx.convertAmountUsingBidOrAsk(Money.of("USD", 1_000))).isEqualTo(Money.of("EUR", "621.12"));  // 1000 * 1.6
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConversionBidOrAskWrongPair() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true, bd("1.6"), bd("1.61"));
        LOG.debug(fx.getDescription());

        assertThat(fx.convertAmountUsingBidOrAsk(Money.of("CAD", 1_000)));
    }

    @Test
    public void testGetPaymentAmountForBuying() {
        FxRateImpl f = new FxRateImpl(CurrencyPair.of("EUR", "USD"), null, true, BigDecimalUtil.bd("1.6"), BigDecimalUtil.bd("1.61"));
        final MonetaryAmount paymentAmountForBuying = f.getPaymentAmountForBuying(Money.of("USD", BigDecimalUtil.bd("1000")));
        assertThat(paymentAmountForBuying.getCurrency()).isEqualTo("EUR");
        assertThat(paymentAmountForBuying.getAmount()).isEqualByComparingTo("625");

        final MonetaryAmount paymentAmountForBuying2 = f.getPaymentAmountForBuying(Money.of("EUR", BigDecimalUtil.bd("1000")));
        assertThat(paymentAmountForBuying2.getCurrency()).isEqualTo("USD");
        assertThat(paymentAmountForBuying2.getAmount()).isEqualByComparingTo("1610");
    }

    @Test
    public void testGetReceipAmountForSelling() {
        FxRateImpl f = new FxRateImpl(CurrencyPair.of("EUR", "USD"), null, true, BigDecimalUtil.bd("1.6"), BigDecimalUtil.bd("1.61"));
        final MonetaryAmount receipt = f.getReceiptAmountForSelling(Money.of("USD", BigDecimalUtil.bd("1000")));
        assertThat(receipt.getCurrency()).isEqualTo("EUR");
        assertThat(receipt.getAmount()).isEqualByComparingTo("621.12");

        final MonetaryAmount receipt2 = f.getReceiptAmountForSelling(Money.of("EUR", BigDecimalUtil.bd("1000")));
        assertThat(receipt2.getCurrency()).isEqualTo("USD");
        assertThat(receipt2.getAmount()).isEqualByComparingTo("1600");
    }

    public static void main(String[] args) {
        FxRateImpl f = new FxRateImpl(CurrencyPair.of("EUR", "USD"), null, true, BigDecimalUtil.bd("1.6"), BigDecimalUtil.bd("1.61"));
        System.out.println(f);
        System.out.println("Buy $1000 for :" + f.getPaymentAmountForBuying(Money.of("USD", BigDecimalUtil.bd("1000"))));
        System.out.println("Buy €1000 for :" + f.getPaymentAmountForBuying(Money.of("EUR", BigDecimalUtil.bd("1000"))));

        System.out.println("Sell $1000 for:" + f.getReceiptAmountForSelling(Money.of("USD", BigDecimalUtil.bd("1000"))));
        System.out.println("Sell €1000 for:" + f.getReceiptAmountForSelling(Money.of("EUR", BigDecimalUtil.bd("1000"))));
    }
    /* OUTPUT
     EUR.USD B:1.6 A:1.61
    Buy $1000 for :EUR 625.00
    Buy €1000 for :USD 1610.00
    Sell $1000 for:EUR 621.12
    Sell €1000 for:USD 1600.00
     */

}
