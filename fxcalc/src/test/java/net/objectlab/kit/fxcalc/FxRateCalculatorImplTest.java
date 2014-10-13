package net.objectlab.kit.fxcalc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import net.objectlab.kit.util.BigDecimalUtil;

import org.assertj.core.util.Lists;
import org.junit.Test;

public class FxRateCalculatorImplTest {
    @Test
    public void testEurUsd() {
        FxRateCalculatorBuilder builder = new FxRateCalculatorBuilder()
                //
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("EUR", "USD"), null, true, BigDecimalUtil.bd("1.6"), BigDecimalUtil.bd("1.61"),
                                new JdkCurrencyProvider()))//
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("GBP", "CHF"), null, true, BigDecimalUtil.bd("2.1702"), BigDecimalUtil.bd("2.1707"),
                                new JdkCurrencyProvider()))//
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("EUR", "GBP"), null, true, BigDecimalUtil.bd("0.7374"), BigDecimalUtil.bd("0.7379"),
                                new JdkCurrencyProvider()))//
                .orderedCurrenciesForCross(Lists.newArrayList("GBP")) //
        ;

        final FxRateCalculator calc = new FxRateCalculatorImpl(builder);

        CurrencyPair target = CurrencyPair.of("EUR", "USD");
        final Optional<FxRate> fx = calc.findFx(target);

        assertThat(fx.isPresent()).isTrue();
        assertThat(fx.get().getCurrencyPair()).isEqualTo(target);
        assertThat(fx.get().getCrossCcy().isPresent()).isFalse();
        assertThat(fx.get().isMarketConvention()).isTrue();
        assertThat(fx.get().getBid()).isEqualByComparingTo("1.6");
        assertThat(fx.get().getAsk()).isEqualByComparingTo("1.61");
        final CurrencyAmount amountInUSD = fx.get().convertAmountUsingBidOrAsk(Cash.of("EUR", 1_000_000L));
        assertThat(amountInUSD.getCurrency()).isEqualTo("USD");
        assertThat(amountInUSD.getAmount()).isEqualByComparingTo("1600000");

        // I want to BUY 1m USD with Euros
        final CurrencyAmount amountBuyInUSD = fx.get().convertAmountUsingBidOrAsk(Cash.of("USD", 1_000_000L));
        assertThat(amountBuyInUSD.getCurrency()).isEqualTo("EUR");
        assertThat(amountBuyInUSD.getAmount()).isEqualByComparingTo("621118.01");

        CurrencyPair invTgt = target.createInverse();
        // order of the Currency Pair does NOT matter for conversions!
        final Optional<FxRate> fx2 = calc.findFx(invTgt);

        assertThat(fx2.isPresent()).isTrue();
        assertThat(fx2.get().getCurrencyPair()).isEqualTo(invTgt);
        assertThat(fx2.get().getCrossCcy().isPresent()).isFalse();
        assertThat(fx2.get().isMarketConvention()).isFalse();
        assertThat(fx2.get().getBid()).isEqualByComparingTo("0.621118012422");
        assertThat(fx2.get().getAsk()).isEqualByComparingTo("0.625");
        final CurrencyAmount amountInUSD2 = fx2.get().convertAmountUsingBidOrAsk(Cash.of("EUR", 1_000_000L));
        assertThat(amountInUSD2.getCurrency()).isEqualTo("USD");
        assertThat(amountInUSD2.getAmount()).isEqualByComparingTo("1600000");

        // I want to BUY 1m USD with Euros
        final CurrencyAmount amountBuyInUSD2 = fx2.get().convertAmountUsingBidOrAsk(Cash.of("USD", 1_000_000L));
        assertThat(amountBuyInUSD2.getCurrency()).isEqualTo("EUR");
        assertThat(amountBuyInUSD2.getAmount()).isEqualByComparingTo("621118.01");
    }

    @Test
    public void testEurChf() {
        FxRateCalculatorBuilder builder = new FxRateCalculatorBuilder()
                //
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("EUR", "USD"), null, true, BigDecimalUtil.bd("1.6"), BigDecimalUtil.bd("1.61"),
                                new JdkCurrencyProvider()))//
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("GBP", "CHF"), null, true, BigDecimalUtil.bd("2.1702"), BigDecimalUtil.bd("2.1707"),
                                new JdkCurrencyProvider()))//
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("EUR", "GBP"), null, true, BigDecimalUtil.bd("0.7374"), BigDecimalUtil.bd("0.7379"),
                                new JdkCurrencyProvider()))//
                .orderedCurrenciesForCross(Lists.newArrayList("GBP")) //
        ;

        final FxRateCalculator calc = new FxRateCalculatorImpl(builder);

        CurrencyPair target = CurrencyPair.of("EUR", "CHF");
        final Optional<FxRate> fx = calc.findFx(target);

        assertThat(fx.isPresent()).isTrue();
        assertThat(fx.get().isMarketConvention()).isTrue();
        assertThat(fx.get().getCrossCcy().get()).isEqualTo("GBP");
        assertThat(fx.get().getBid()).isEqualByComparingTo("1.600305");
        assertThat(fx.get().getAsk()).isEqualByComparingTo("1.601760");
        final CurrencyAmount amountInCHF = fx.get().convertAmountUsingBidOrAsk(Cash.of("EUR", 1_000_000L));
        assertThat(amountInCHF.getCurrency()).isEqualTo("CHF");
        assertThat(amountInCHF.getAmount()).isEqualTo("1600305.00");
        final CurrencyAmount amountBuyInEUR = fx.get().convertAmountUsingBidOrAsk(Cash.of("CHF", 1_000_000L));
        assertThat(amountBuyInEUR.getCurrency()).isEqualTo("EUR");
        assertThat(amountBuyInEUR.getAmount()).isEqualTo("624313.26");

        // order of the Currency Pair does NOT matter for conversions!
        CurrencyPair inverseTarget = target.createInverse();
        final Optional<FxRate> fx2 = calc.findFx(inverseTarget);
        assertThat(fx2.isPresent()).isTrue();
        assertThat(fx2.get().getCrossCcy().get()).isEqualTo("GBP");
        assertThat(fx2.get().isMarketConvention()).isFalse();
        assertThat(fx2.get().getBid()).isEqualByComparingTo("0.624313255419");
        assertThat(fx2.get().getAsk()).isEqualByComparingTo("0.624880882082");
        final CurrencyAmount amountInCHF2 = fx2.get().convertAmountUsingBidOrAsk(Cash.of("EUR", 1_000_000L));
        assertThat(amountInCHF2.getCurrency()).isEqualTo("CHF");
        assertThat(amountInCHF2.getAmount()).isEqualTo("1600305.00");
        final CurrencyAmount amountBuyInEUR2 = fx2.get().convertAmountUsingBidOrAsk(Cash.of("CHF", 1_000_000L));
        assertThat(amountBuyInEUR2.getCurrency()).isEqualTo("EUR");
        assertThat(amountBuyInEUR2.getAmount()).isEqualTo("624313.26");
    }

    @Test
    public void testNoPossibleCross() {
        FxRateCalculatorBuilder builder = new FxRateCalculatorBuilder()
                //
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("EUR", "USD"), null, true, BigDecimalUtil.bd("1.6"), BigDecimalUtil.bd("1.61"),
                                new JdkCurrencyProvider()))//
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("GBP", "CHF"), null, true, BigDecimalUtil.bd("2.1702"), BigDecimalUtil.bd("2.1707"),
                                new JdkCurrencyProvider()))//
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("EUR", "GBP"), null, true, BigDecimalUtil.bd("0.7374"), BigDecimalUtil.bd("0.7379"),
                                new JdkCurrencyProvider()))//
                .orderedCurrenciesForCross(Lists.newArrayList("USD")) // impossible to find EUR/CHF
        ;

        final FxRateCalculator calc = new FxRateCalculatorImpl(builder);

        CurrencyPair target = CurrencyPair.of("EUR", "CHF");
        final Optional<FxRate> fx = calc.findFx(target);

        assertThat(fx.isPresent()).isFalse();
    }

    @Test
    public void testSecondPossibleCross() {
        FxRateCalculatorBuilder builder = new FxRateCalculatorBuilder()
                //
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("EUR", "USD"), null, true, BigDecimalUtil.bd("1.6"), BigDecimalUtil.bd("1.61"),
                                new JdkCurrencyProvider()))//
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("GBP", "CHF"), null, true, BigDecimalUtil.bd("2.1702"), BigDecimalUtil.bd("2.1707"),
                                new JdkCurrencyProvider()))//
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("EUR", "GBP"), null, true, BigDecimalUtil.bd("0.7374"), BigDecimalUtil.bd("0.7379"),
                                new JdkCurrencyProvider()))//
                .orderedCurrenciesForCross(Lists.newArrayList("USD", "GBP")) // impossible to find EUR/CHF via USD but ok with GBP
        ;

        final FxRateCalculator calc = new FxRateCalculatorImpl(builder);

        CurrencyPair target = CurrencyPair.of("EUR", "CHF");
        final Optional<FxRate> fx = calc.findFx(target);

        assertThat(fx.isPresent()).isTrue();
        assertThat(fx.get().isMarketConvention()).isTrue();
        assertThat(fx.get().getCrossCcy().get()).isEqualTo("GBP");
        assertThat(fx.get().getBid()).isEqualByComparingTo("1.600305");
        assertThat(fx.get().getAsk()).isEqualByComparingTo("1.601760");
        final CurrencyAmount amountInCHF = fx.get().convertAmountUsingBidOrAsk(Cash.of("EUR", 1_000_000L));
        assertThat(amountInCHF.getCurrency()).isEqualTo("CHF");
        assertThat(amountInCHF.getAmount()).isEqualTo("1600305.00");
        final CurrencyAmount amountBuyInEUR = fx.get().convertAmountUsingBidOrAsk(Cash.of("CHF", 1_000_000L));
        assertThat(amountBuyInEUR.getCurrency()).isEqualTo("EUR");
        assertThat(amountBuyInEUR.getAmount()).isEqualTo("624313.26");

        // order of the Currency Pair does NOT matter for conversions!
        CurrencyPair inverseTarget = target.createInverse();
        final Optional<FxRate> fx2 = calc.findFx(inverseTarget);
        assertThat(fx2.isPresent()).isTrue();
        assertThat(fx2.get().getCrossCcy().get()).isEqualTo("GBP");
        assertThat(fx2.get().isMarketConvention()).isFalse();
        assertThat(fx2.get().getBid()).isEqualByComparingTo("0.624313255419");
        assertThat(fx2.get().getAsk()).isEqualByComparingTo("0.624880882082");
        final CurrencyAmount amountInCHF2 = fx2.get().convertAmountUsingBidOrAsk(Cash.of("EUR", 1_000_000L));
        assertThat(amountInCHF2.getCurrency()).isEqualTo("CHF");
        assertThat(amountInCHF2.getAmount()).isEqualTo("1600305.00");
        final CurrencyAmount amountBuyInEUR2 = fx2.get().convertAmountUsingBidOrAsk(Cash.of("CHF", 1_000_000L));
        assertThat(amountBuyInEUR2.getCurrency()).isEqualTo("EUR");
        assertThat(amountBuyInEUR2.getAmount()).isEqualTo("624313.26");
    }
}
