package net.objectlab.kit.fxcalc.gist;

import java.util.Optional;

import net.objectlab.kit.fxcalc.CurrencyPair;
import net.objectlab.kit.fxcalc.FxRate;
import net.objectlab.kit.fxcalc.FxRateCalculator;
import net.objectlab.kit.fxcalc.FxRateCalculatorBuilder;
import net.objectlab.kit.fxcalc.FxRateCalculatorImpl;
import net.objectlab.kit.fxcalc.FxRateImpl;
import net.objectlab.kit.fxcalc.MonetaryAmount;
import net.objectlab.kit.fxcalc.Money;
import net.objectlab.kit.util.BigDecimalUtil;

public final class FxRateExample {
    public static void main(final String[] args) {
        FxRateCalculatorBuilder builder = new FxRateCalculatorBuilder() //
                .addRate(new FxRateImpl(CurrencyPair.of("EUR", "USD"), null, true, BigDecimalUtil.bd("1.6"), BigDecimalUtil.bd("1.61")))//
                .addRate(new FxRateImpl(CurrencyPair.of("GBP", "CHF"), null, true, BigDecimalUtil.bd("2.1702"), BigDecimalUtil.bd("2.1707")))//
                .addRate(new FxRateImpl(CurrencyPair.of("EUR", "GBP"), null, true, BigDecimalUtil.bd("0.7374"), BigDecimalUtil.bd("0.7379")))//
        ;

        // immutable, can be re-used, shared etc
        final FxRateCalculator calc = new FxRateCalculatorImpl(builder);

        calcEurUsd(calc);
        calcEurChf(calc);
    }

    private static void calcEurChf(final FxRateCalculator calc) {
        System.out.println("Calc EUR / CHF =========================");
        CurrencyPair target = CurrencyPair.of("EUR", "CHF");
        final Optional<FxRate> fx = calc.findFx(target);

        if (fx.isPresent()) {
            // I want to sell 1m Euros in CHF
            final MonetaryAmount amountInCHF = fx.get().convertAmountUsingBidOrAsk(Money.of("EUR", 1_000_000L));
            System.out.println(target + " €1m => " + amountInCHF); // CHF 1600305.48

            // I want to BUY 1m CHF with Euros
            final MonetaryAmount amountBuyInEUR = fx.get().convertAmountUsingBidOrAsk(Money.of("CHF", 1_000_000L));
            System.out.println(target + " " + amountBuyInEUR); // EUR 624313.44
        }

        // order of the Currency Pair does NOT matter for conversions!
        CurrencyPair inverseTarget = target.createInverse();
        final Optional<FxRate> fx2 = calc.findFx(inverseTarget);

        if (fx2.isPresent()) {
            // I want to sell 1m Euros in USD
            final MonetaryAmount amountInCHF = fx2.get().convertAmountUsingBidOrAsk(Money.of("EUR", 1_000_000L));
            System.out.println(inverseTarget + " €1m => " + amountInCHF); // CHF 1600305.48

            // I want to BUY 1m USD with Euros
            final MonetaryAmount amountBuyInEUR = fx2.get().convertAmountUsingBidOrAsk(Money.of("CHF", 1_000_000L));
            System.out.println(inverseTarget + " " + amountBuyInEUR); // EUR 624313.44
        }
    }

    private static void calcEurUsd(final FxRateCalculator calc) {
        System.out.println("Calc EUR / USD =========================");
        CurrencyPair target = CurrencyPair.of("EUR", "USD");
        final Optional<FxRate> fx = calc.findFx(target);

        if (fx.isPresent()) {
            // I want to sell 1m Euros in USD
            final MonetaryAmount amountInUSD = fx.get().convertAmountUsingBidOrAsk(Money.of("EUR", 1_000_000L));
            System.out.println(target + " €1m => " + amountInUSD); // $1.6m

            // I want to BUY 1m USD with Euros
            final MonetaryAmount amountBuyInUSD = fx.get().convertAmountUsingBidOrAsk(Money.of("USD", 1_000_000L));
            System.out.println(target + " $1m => " + amountBuyInUSD); // EUR621,118.01
        }

        CurrencyPair invTgt = target.createInverse();
        // order of the Currency Pair does NOT matter for conversions!
        final Optional<FxRate> fx2 = calc.findFx(invTgt);

        if (fx2.isPresent()) {
            // I want to sell 1m Euros in USD
            final MonetaryAmount amountInUSD = fx2.get().convertAmountUsingBidOrAsk(Money.of("EUR", 1_000_000L));
            System.out.println(invTgt + " €1m => " + amountInUSD); // $1.6m

            // I want to BUY 1m USD with Euros
            final MonetaryAmount amountBuyInUSD = fx2.get().convertAmountUsingBidOrAsk(Money.of("USD", 1_000_000L));
            System.out.println(invTgt + " $1m +> " + amountBuyInUSD); // EUR621,118.01
        }
    }
}
