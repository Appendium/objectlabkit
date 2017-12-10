package net.objectlab.kit.fxcalc;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.objectlab.kit.util.BigDecimalUtil;

/**
 * Calculator responsible for generating a new cross FX Rate based on two relevant (sharing a cross currency) FX Rates; e.g. CHF/JPY based on USD/CHF and USD/JPY.
 * This calculator is able to handle all 'standard' cases where the market convention is different, e.g. GBP/CAD via USD/CAD and EUR/USD or inverted rates
 * if it is what you might have e.g. GBP/CAD via CAD/USD and EUR/USD.
 * @author Benoit Xhenseval
 */
public final class CrossRateCalculator {
    private static final Logger LOG = LoggerFactory.getLogger(CrossRateCalculator.class);

    private CrossRateCalculator() {
    }

    /**
     * Calculate the cross rate, use this only if required.
     * @param targetPair the currency pair we want Bid/Ask for
     * @param fx1 one rate involving either targetPair.ccy1 or targetPair.ccy2
     * @param fx2 one rate involving either targetPair.ccy1 or targetPair.ccy2
     * @param precision required in case we need to divide rates
     * @param ranking link to algorithm to determine if the targetPair will be market convention or not
     * @return a new instance of FxRate
     * @throws IllegalArgumentException if the 2 fx1 and fx2 do not share a common cross currency or either currencies in the targetPair
     */
    public static FxRate calculateCross(final CurrencyPair targetPair, final FxRate fx1, final FxRate fx2, final int precision,
            final int precisionForInverseFxRate, final MajorCurrencyRanking ranking, final int bidRounding, final int askRounding,
            CurrencyProvider currencyProvider) {
        final Optional<String> crossCcy = fx1.getCurrencyPair().findCommonCcy(fx2.getCurrencyPair());
        final String xCcy = crossCcy.orElseThrow(
                () -> new IllegalArgumentException("The 2 FXRates do not share a ccy " + fx1.getCurrencyPair() + " " + fx2.getCurrencyPair()));

        if (crossCcy.isPresent() && targetPair.containsCcy(crossCcy.get())) {
            throw new IllegalArgumentException("The target currency pair " + targetPair + " contains the common ccy " + crossCcy.get());
        }

        final String fx1Ccy1 = fx1.getCurrencyPair().getCcy1();
        final String fx2Ccy1 = fx2.getCurrencyPair().getCcy1();
        final String fx1Ccy2 = fx1.getCurrencyPair().getCcy2();
        final String fx2Ccy2 = fx2.getCurrencyPair().getCcy2();
        // what if it is both ccy2?
        final boolean shouldDivide = fx1Ccy1.equals(xCcy) && fx2Ccy1.equals(xCcy) || fx1Ccy2.equals(xCcy) && fx2Ccy2.equals(xCcy);

        FxRateImpl crossRate = null;

        if (shouldDivide) {
            final FxRate numeratorFx = targetPair.getCcy1().equals(fx2Ccy2) || targetPair.getCcy1().equals(fx1Ccy1) ? fx1 : fx2;
            final FxRate denominatorFx = numeratorFx == fx1 ? fx2 : fx1;
            LOG.debug("CALC {} / {}", numeratorFx, denominatorFx);

            BigDecimal bid = BigDecimalUtil.divide(precision, numeratorFx.getBid(), denominatorFx.getAsk(), bidRounding);
            BigDecimal ask = BigDecimalUtil.divide(precision, numeratorFx.getAsk(), denominatorFx.getBid(), askRounding);
            crossRate = new FxRateImpl(targetPair, xCcy, ranking.isMarketConvention(targetPair), bid, ask, currencyProvider);
        } else {
            crossRate = calculateWithDivide(targetPair, fx1, fx2, precision, precisionForInverseFxRate, ranking, bidRounding, askRounding,
                    currencyProvider, xCcy, fx1Ccy2, fx2Ccy2);
        }
        LOG.debug("X RATE {}", crossRate);
        LOG.debug(crossRate.getDescription());
        return crossRate;
    }

    private static FxRateImpl calculateWithDivide(final CurrencyPair targetPair, final FxRate fx1, final FxRate fx2, final int precision,
            final int precisionForInverseFxRate, final MajorCurrencyRanking ranking, final int bidRounding, final int askRounding,
            CurrencyProvider currencyProvider, final String xCcy, final String fx1Ccy2, final String fx2Ccy2) {
        final boolean inverse = targetPair.getCcy1().equals(fx2Ccy2) || targetPair.getCcy1().equals(fx1Ccy2);
        LOG.debug("CALC {} x {}", fx1, fx2);
        BigDecimal bid = null;
        BigDecimal ask = null;
        if (inverse) {
            ask = BigDecimalUtil.setScale(
                    BigDecimalUtil.inverse(BigDecimalUtil.multiply(fx1.getBid(), fx2.getBid()), precisionForInverseFxRate, bidRounding), precision);
            bid = BigDecimalUtil.setScale(
                    BigDecimalUtil.inverse(BigDecimalUtil.multiply(fx1.getAsk(), fx2.getAsk()), precisionForInverseFxRate, askRounding), precision);
        } else {
            bid = BigDecimalUtil.setScale(BigDecimalUtil.multiply(fx1.getBid(), fx2.getBid()), precision);
            ask = BigDecimalUtil.setScale(BigDecimalUtil.multiply(fx1.getAsk(), fx2.getAsk()), precision);
        }
        return new FxRateImpl(targetPair, xCcy, ranking.isMarketConvention(targetPair), bid, ask, currencyProvider);
    }
}
