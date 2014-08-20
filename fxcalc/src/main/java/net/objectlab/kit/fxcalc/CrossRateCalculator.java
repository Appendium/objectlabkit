package net.objectlab.kit.fxcalc;

import java.math.BigDecimal;
import java.util.Optional;

import net.objectlab.kit.util.BigDecimalUtil;

public class CrossRateCalculator {

    public static FxRate calculateCross(final CurrencyPair targetPair, final FxRate fx1, final FxRate fx2, final int precision,
            final MajorCurrencyRanking ranking) {
        final Optional<String> crossCcy = fx1.getCurrencyPair().findCommonCcy(fx2.getCurrencyPair());
        if (!crossCcy.isPresent()) {
            throw new IllegalArgumentException("The 2 FXRates do not share a ccy " + fx1.getCurrencyPair() + " " + fx2.getCurrencyPair());
        }
        final String xCcy = crossCcy.get();
        final boolean shouldDivide = fx1.getCurrencyPair().getCcy1().equals(xCcy) && fx2.getCurrencyPair().getCcy1().equals(xCcy)
                || fx1.getCurrencyPair().getCcy2().equals(xCcy) && fx2.getCurrencyPair().getCcy2().equals(xCcy); // what if it i both ccy2?

        final FxRateImpl crossRate = new FxRateImpl(targetPair, xCcy, ranking.isMarketConvention(targetPair.getCcy1(), targetPair.getCcy2()));

        if (shouldDivide) {
            final FxRate numeratorFx = targetPair.getCcy1().equals(fx2.getCurrencyPair().getCcy2())
                    || targetPair.getCcy1().equals(fx1.getCurrencyPair().getCcy1()) ? fx1 : fx2;
            final FxRate denominatorFx = numeratorFx == fx1 ? fx2 : fx1;
            System.out.println("CALC " + numeratorFx + " / " + denominatorFx);

            crossRate.setBid(BigDecimalUtil.divide(precision, numeratorFx.getBid(), denominatorFx.getAsk(), BigDecimal.ROUND_HALF_UP));
            crossRate.setAsk(BigDecimalUtil.divide(precision, numeratorFx.getAsk(), denominatorFx.getBid(), BigDecimal.ROUND_HALF_UP));
        } else {
            final boolean inverse = targetPair.getCcy1().equals(fx2.getCurrencyPair().getCcy2())
                    || targetPair.getCcy1().equals(fx1.getCurrencyPair().getCcy2());
            System.out.println("CALC " + fx1 + " x " + fx2);
            if (inverse) {
                crossRate.setAsk(BigDecimalUtil.setScale(BigDecimalUtil.inverse(BigDecimalUtil.multiply(fx1.getBid(), fx2.getBid())), precision));
                crossRate.setBid(BigDecimalUtil.setScale(BigDecimalUtil.inverse(BigDecimalUtil.multiply(fx1.getAsk(), fx2.getAsk())), precision));
            } else {
                crossRate.setBid(BigDecimalUtil.setScale(BigDecimalUtil.multiply(fx1.getBid(), fx2.getBid()), precision));
                crossRate.setAsk(BigDecimalUtil.setScale(BigDecimalUtil.multiply(fx1.getAsk(), fx2.getAsk()), precision));
            }
        }
        System.out.println("X RATE " + crossRate);
        System.out.println(crossRate.getDescription());
        return crossRate;
    }
}
