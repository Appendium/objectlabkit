package net.objectlab.kit.fxcalc;

import static net.objectlab.kit.util.BigDecimalUtil.bd;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.objectlab.kit.util.BigDecimalUtil;

public class FxRateImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(FxRateImplTest.class);

    @Test
    public void testMid() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true, bd("1.6"), bd("1.61"), new JdkCurrencyProvider());
        assertThat(fx.getMid()).isEqualByComparingTo("1.605");
    }

    @Test
    public void testConversionMid() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true, bd("1.6"), bd("1.61"), new JdkCurrencyProvider());
        LOG.debug(fx.getDescription());

        assertThat(fx.convertAmountUsingMid(Cash.of("EUR", 1_000))).isEqualTo(Cash.of("USD", "1605.00"));
        assertThat(fx.convertAmountUsingMid(Cash.of("USD", 1_000))).isEqualTo(Cash.of("EUR", "623.05"));
    }

    @Test
    public void testConversionMidJpy() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("USD", "JPY"), null, true, bd("133.23"), bd("133.34"), new JdkCurrencyProvider());
        LOG.debug(fx.getDescription());

        assertThat(fx.getMid()).isEqualByComparingTo(bd("133.285"));
        assertThat(fx.convertAmountUsingMid(Cash.of("USD", 10))).isEqualTo(Cash.of("JPY", "1332"));
        assertThat(fx.convertAmountUsingMid(Cash.of("JPY", 1_000))).isEqualTo(Cash.of("USD", "7.50"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConversionMidWrongPair() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true, bd("1.6"), bd("1.61"), new JdkCurrencyProvider());
        LOG.debug(fx.getDescription());

        assertThat(fx.convertAmountUsingMid(Cash.of("CAD", 1_000))).describedAs("Should not be called!").isNull();
    }

    @Test
    public void testConversionBidAsk() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true, bd("1.6"), bd("1.61"), new JdkCurrencyProvider());
        LOG.debug(fx.getDescription());

        assertThat(fx.convertAmountUsingBidOrAsk(Cash.of("EUR", 1_000))).isEqualTo(Cash.of("USD", "1600.00"));  // 1000 * 1.6
        assertThat(fx.convertAmountUsingBidOrAsk(Cash.of("USD", 1_000))).isEqualTo(Cash.of("EUR", "621.12"));  // 1000 * 1.6
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConversionBidOrAskWrongPair() {
        FxRateImpl fx = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true, bd("1.6"), bd("1.61"), new JdkCurrencyProvider());
        LOG.debug(fx.getDescription());

        assertThat(fx.convertAmountUsingBidOrAsk(Cash.of("CAD", 1_000))).describedAs("Should throw an exception").isNull();
        ;
    }

    @Test
    public void testGetPaymentAmountForBuying() {
        FxRateImpl f = new FxRateImpl(CurrencyPair.of("EUR", "USD"), null, true, BigDecimalUtil.bd("1.6"), BigDecimalUtil.bd("1.61"),
                new JdkCurrencyProvider());
        final CurrencyAmount paymentAmountForBuying = f.getPaymentAmountForBuying(Cash.of("USD", BigDecimalUtil.bd("1000")));
        assertThat(paymentAmountForBuying.getCurrency()).isEqualTo("EUR");
        assertThat(paymentAmountForBuying.getAmount()).isEqualByComparingTo("625");

        final CurrencyAmount paymentAmountForBuying2 = f.getPaymentAmountForBuying(Cash.of("EUR", BigDecimalUtil.bd("1000")));
        assertThat(paymentAmountForBuying2.getCurrency()).isEqualTo("USD");
        assertThat(paymentAmountForBuying2.getAmount()).isEqualByComparingTo("1610");
    }

    @Test
    public void testGetReceipAmountForSelling() {
        FxRateImpl f = new FxRateImpl(CurrencyPair.of("EUR", "USD"), null, true, BigDecimalUtil.bd("1.6"), BigDecimalUtil.bd("1.61"),
                new JdkCurrencyProvider());
        final CurrencyAmount receipt = f.getReceiptAmountForSelling(Cash.of("USD", BigDecimalUtil.bd("1000")));
        assertThat(receipt.getCurrency()).isEqualTo("EUR");
        assertThat(receipt.getAmount()).isEqualByComparingTo("621.12");

        final CurrencyAmount receipt2 = f.getReceiptAmountForSelling(Cash.of("EUR", BigDecimalUtil.bd("1000")));
        assertThat(receipt2.getCurrency()).isEqualTo("USD");
        assertThat(receipt2.getAmount()).isEqualByComparingTo("1600");
    }

    public static void main(String[] args) {
        FxRateImpl f = new FxRateImpl(CurrencyPair.of("EUR", "USD"), null, true, BigDecimalUtil.bd("1.6"), BigDecimalUtil.bd("1.61"),
                new JdkCurrencyProvider());
        System.out.println(f);
        System.out.println("Buy $1000 for :" + f.getPaymentAmountForBuying(Cash.of("USD", BigDecimalUtil.bd("1000"))));
        System.out.println("Buy €1000 for :" + f.getPaymentAmountForBuying(Cash.of("EUR", BigDecimalUtil.bd("1000"))));

        System.out.println("Sell $1000 for:" + f.getReceiptAmountForSelling(Cash.of("USD", BigDecimalUtil.bd("1000"))));
        System.out.println("Sell €1000 for:" + f.getReceiptAmountForSelling(Cash.of("EUR", BigDecimalUtil.bd("1000"))));
    }
    /* OUTPUT
     EUR.USD B:1.6 A:1.61
    Buy $1000 for :EUR 625.00
    Buy €1000 for :USD 1610.00
    Sell $1000 for:EUR 621.12
    Sell €1000 for:USD 1600.00
     */

}
