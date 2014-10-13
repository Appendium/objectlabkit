package net.objectlab.kit.fxcalc.gist;

import java.util.Optional;

import net.objectlab.kit.fxcalc.Cash;
import net.objectlab.kit.fxcalc.CurrencyAmount;
import net.objectlab.kit.fxcalc.CurrencyPair;
import net.objectlab.kit.fxcalc.FxRate;
import net.objectlab.kit.fxcalc.FxRateCalculator;
import net.objectlab.kit.fxcalc.FxRateCalculatorBuilder;
import net.objectlab.kit.fxcalc.FxRateCalculatorImpl;
import net.objectlab.kit.fxcalc.FxRateImpl;
import net.objectlab.kit.fxcalc.JdkCurrencyProvider;
import net.objectlab.kit.util.BigDecimalUtil;

import org.assertj.core.util.Lists;

public final class FxRateExample {
    public static void main(final String[] args) {
        final FxRateCalculatorBuilder builder = new FxRateCalculatorBuilder()
                //
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("EUR", "USD"), null, true, BigDecimalUtil.bd("1.3"), BigDecimalUtil.bd("1.31"),
                                new JdkCurrencyProvider()))//
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("GBP", "CHF"), null, true, BigDecimalUtil.bd("1.51589"), BigDecimalUtil.bd("1.5156"),
                                new JdkCurrencyProvider()))//
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("EUR", "GBP"), null, true, BigDecimalUtil.bd("0.79"), BigDecimalUtil.bd("0.796"),
                                new JdkCurrencyProvider()))//
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("USD", "JPY"), null, true, BigDecimalUtil.bd("103.931"), BigDecimalUtil.bd("103.94"),
                                new JdkCurrencyProvider()))//
                .addRateSnapshot(
                        new FxRateImpl(CurrencyPair.of("USD", "CAD"), null, true, BigDecimalUtil.bd("1.089"), BigDecimalUtil.bd("1.090"),
                                new JdkCurrencyProvider()))//
                .orderedCurrenciesForCross(Lists.newArrayList("GBP", "USD")) //
        ;

        // immutable, can be re-used, shared etc PROVIDED that the FxRate does not change (safe with FxRateImpl)
        final FxRateCalculator calc = new FxRateCalculatorImpl(builder);

        calcEurUsd(calc);
        calcEurChf(calc);
        calcCadJpy(calc);
    }

    private static void calcCadJpy(final FxRateCalculator calc) {
        System.out.println("Calc CAD / JPY via USD =========================");
        final CurrencyPair target = CurrencyPair.of("CAD", "JPY");
        final Optional<FxRate> fx = calc.findFx(target);

        if (fx.isPresent()) {
            // I want to sell 1m CAD in JPY
            final CurrencyAmount amountInJPY = fx.get().convertAmountUsingBidOrAsk(Cash.of("CAD", 1_000_000L));
            System.out.println(target + " CAD 1m => " + amountInJPY); // JPY 95349541.00

            // I want to BUY 1m JPY with CAD
            final CurrencyAmount amountBuyInCAD = fx.get().convertAmountUsingBidOrAsk(Cash.of("JPY", 1_000_000L));
            System.out.println(target + " JPY 1m => " + amountBuyInCAD); // CAD 10477.20
        }

        // order of the Currency Pair does NOT matter for conversions!
        final CurrencyPair inverseTarget = target.createInverse();
        final Optional<FxRate> fx2 = calc.findFx(inverseTarget);

        if (fx2.isPresent()) {
            // I want to sell 1m Euros in USD
            final CurrencyAmount amountInJPY = fx2.get().convertAmountUsingBidOrAsk(Cash.of("CAD", 1_000_000L));
            System.out.println(inverseTarget + " CAD 1m => " + amountInJPY); // JPY 95349541.00

            // I want to BUY 1m USD with Euros
            final CurrencyAmount amountBuyInCAD = fx2.get().convertAmountUsingBidOrAsk(Cash.of("JPY", 1_000_000L));
            System.out.println(inverseTarget + " JPY 1m => " + amountBuyInCAD); // CAD 10477.20
        }
    }

    private static void calcEurChf(final FxRateCalculator calc) {
        System.out.println("Calc EUR / CHF via GBP =========================");
        final CurrencyPair target = CurrencyPair.of("EUR", "CHF");
        final Optional<FxRate> fx = calc.findFx(target);

        if (fx.isPresent()) {
            // I want to sell 1m Euros in CHF
            final CurrencyAmount amountInCHF = fx.get().convertAmountUsingBidOrAsk(Cash.of("EUR", 1_000_000L));
            System.out.println(target + " €1m => " + amountInCHF); // CHF 1197553.00

            // I want to BUY 1m CHF with Euros
            final CurrencyAmount amountBuyInEUR = fx.get().convertAmountUsingBidOrAsk(Cash.of("CHF", 1_000_000L));
            System.out.println(target + " CHF1m: " + amountBuyInEUR); // EUR 828900.10
        }

        // order of the Currency Pair does NOT matter for conversions!
        final CurrencyPair inverseTarget = target.createInverse();
        final Optional<FxRate> fx2 = calc.findFx(inverseTarget);

        if (fx2.isPresent()) {
            // I want to sell 1m Euros in USD
            final CurrencyAmount amountInCHF = fx2.get().convertAmountUsingBidOrAsk(Cash.of("EUR", 1_000_000L));
            System.out.println(inverseTarget + " €1m => " + amountInCHF); // CHF 1197553.00

            // I want to BUY 1m USD with Euros
            final CurrencyAmount amountBuyInEUR = fx2.get().convertAmountUsingBidOrAsk(Cash.of("CHF", 1_000_000L));
            System.out.println(inverseTarget + " CHF1m: " + amountBuyInEUR); // EUR 828900.10
        }
    }

    private static void calcEurUsd(final FxRateCalculator calc) {
        System.out.println("Calc EUR / USD (Not a Cross) =========================");
        final CurrencyPair target = CurrencyPair.of("EUR", "USD");
        final Optional<FxRate> fx = calc.findFx(target);

        if (fx.isPresent()) {
            // I want to sell 1m Euros in USD
            final CurrencyAmount amountInUSD = fx.get().convertAmountUsingBidOrAsk(Cash.of("EUR", 1_000_000L));
            System.out.println(target + " €1m => " + amountInUSD); // $1300000.00

            // I want to BUY 1m USD with Euros
            final CurrencyAmount amountBuyInUSD = fx.get().convertAmountUsingBidOrAsk(Cash.of("USD", 1_000_000L));
            System.out.println(target + " $1m => " + amountBuyInUSD); // EUR763358.78

            System.out.println("BUY / SELL AMOUNT -------- " + fx.get());
            System.out.println("Buy $1000 for :" + fx.get().getPaymentAmountForBuying(Cash.of("USD", BigDecimalUtil.bd("1000"))));
            System.out.println("Buy €1000 for :" + fx.get().getPaymentAmountForBuying(Cash.of("EUR", BigDecimalUtil.bd("1000"))));

            System.out.println("Sell $1000 for:" + fx.get().getReceiptAmountForSelling(Cash.of("USD", BigDecimalUtil.bd("1000"))));
            System.out.println("Sell €1000 for:" + fx.get().getReceiptAmountForSelling(Cash.of("EUR", BigDecimalUtil.bd("1000"))));
            /* OUTPUT
            BUY / SELL AMOUNT -------- EUR.USD B:1.3 A:1.31
            Buy $1000 for :EUR 769.23
            Buy €1000 for :USD 1310.00
            Sell $1000 for:EUR 763.36
            Sell €1000 for:USD 1300.00
             */
        }

        final CurrencyPair invTgt = target.createInverse();
        // order of the Currency Pair does NOT matter for conversions!
        final Optional<FxRate> fx2 = calc.findFx(invTgt);

        if (fx2.isPresent()) {
            // I want to sell 1m Euros in USD
            final CurrencyAmount amountInUSD = fx2.get().convertAmountUsingBidOrAsk(Cash.of("EUR", 1_000_000L));
            System.out.println(invTgt + " €1m => " + amountInUSD); // $1300000.00

            // I want to BUY 1m USD with Euros
            final CurrencyAmount amountBuyInUSD = fx2.get().convertAmountUsingBidOrAsk(Cash.of("USD", 1_000_000L));
            System.out.println(invTgt + " $1m +> " + amountBuyInUSD); // EUR763358.78
        }
    }
    /* OUTPUT
    Calc EUR / USD (Not a Cross) =========================
    EUR.USD €1m => USD 1300000.00
    EUR.USD $1m => EUR 763358.78
    BUY / SELL AMOUNT -------- EUR.USD B:1.3 A:1.31
    Buy $1000 for :EUR 769.23
    Buy €1000 for :USD 1310.00
    Sell $1000 for:EUR 763.36
    Sell €1000 for:USD 1300.00

    USD.EUR €1m => USD 1300000.00
    USD.EUR $1m +> EUR 763358.78
    
    Calc EUR / CHF via GBP =========================
    11:51:58.830 [main] DEBUG n.o.kit.fxcalc.CrossRateCalculator - CALC GBP.CHF B:1.51589 A:1.5156 / GBP.EUR B:1.256281407035 A:1.265822784810
    11:51:58.835 [main] DEBUG n.o.kit.fxcalc.CrossRateCalculator - X RATE EUR.CHF B:1.197553 A:1.206418
    11:51:58.873 [main] DEBUG n.o.kit.fxcalc.CrossRateCalculator - EUR.CHF Mkt Convention:true Cross Ccy:GBP
    Quoter buys  EUR and sells CHF at 1.197553
    Quoter sells EUR and buys  CHF at 1.206418
    EUR.CHF €1m => CHF 1197553.00
    EUR.CHF CHF1m: EUR 828900.10
    CHF.EUR €1m => CHF 1197553.00
    CHF.EUR CHF1m: EUR 828900.10
    
    Calc CAD / JPY via USD =========================
    11:51:58.874 [main] DEBUG n.o.kit.fxcalc.CrossRateCalculator - CALC USD.JPY B:103.931 A:103.94 / USD.CAD B:1.089 A:1.090
    11:51:58.875 [main] DEBUG n.o.kit.fxcalc.CrossRateCalculator - X RATE CAD.JPY B:95.349541 A:95.445363
    11:51:58.875 [main] DEBUG n.o.kit.fxcalc.CrossRateCalculator - CAD.JPY Mkt Convention:true Cross Ccy:USD
    Quoter buys  CAD and sells JPY at 95.349541
    Quoter sells CAD and buys  JPY at 95.445363
    CAD.JPY CAD 1m => JPY 95349541.00
    CAD.JPY JPY 1m => CAD 10477.20
    JPY.CAD CAD 1m => JPY 95349541.00
    JPY.CAD JPY 1m => CAD 10477.20
     */
}
