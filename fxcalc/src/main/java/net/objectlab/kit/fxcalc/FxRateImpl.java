package net.objectlab.kit.fxcalc;

import static net.objectlab.kit.util.BigDecimalUtil.divide;
import static net.objectlab.kit.util.BigDecimalUtil.inverse;
import static net.objectlab.kit.util.BigDecimalUtil.isNullOrZero;
import static net.objectlab.kit.util.BigDecimalUtil.multiply;
import static net.objectlab.kit.util.BigDecimalUtil.setScale;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import net.objectlab.kit.util.BigDecimalUtil;

/**
 * Represents an immutable FxRate.
 * @author Benoit Xhenseval
 */
public class FxRateImpl implements FxRate {
    private static final int DEC_PLACE_FOR_MONEY = 2;
    private static final int PRECISION_FOR_INVERSE = 10;
    private static final BigDecimal TWO = BigDecimal.valueOf(2L);
    private final CurrencyPair currencyPair;
    private final String crossCcy;
    private final boolean marketConvention;
    private final BigDecimal bid;
    private final BigDecimal ask;
    private final CurrencyProvider currencyProvider;

    public FxRateImpl(final CurrencyPair currencyPair, final String crossCcy, final boolean marketConvention, final BigDecimal bid,
            final BigDecimal ask, CurrencyProvider currencyProvider) {
        this.currencyPair = currencyPair;
        this.crossCcy = crossCcy;
        this.marketConvention = marketConvention;
        this.bid = bid;
        this.ask = ask;
        this.currencyProvider = currencyProvider;
    }

    /**
     * Nice human readable description of the FX Rate, useful reminder.
     * <pre>
     * CAD.SGD Mkt Convention:true
     * Quoter buys  CAD and sells SGD at 1.1279
     * Quoter sells CAD and buys  SGD at 1.1289
     * </pre>
     */
    @Override
    public String getDescription() {
        final StringBuilder b = new StringBuilder();
        b.append(currencyPair).append(" Mkt Convention:").append(marketConvention);
        if (crossCcy != null) {
            b.append(" Cross Ccy:").append(crossCcy);
        }
        final String cr = System.getProperty("line.separator");
        b.append(cr);
        b.append("Quoter buys  ").append(currencyPair.getCcy1()).append(" and sells ").append(currencyPair.getCcy2()).append(" at ").append(bid)
                .append(cr);
        b.append("Quoter sells ").append(currencyPair.getCcy1()).append(" and buys  ").append(currencyPair.getCcy2()).append(" at ").append(ask);

        return b.toString();
    }

    @Override
    public FxRate createInverse() {
        return new FxRateImpl(currencyPair.createInverse(), crossCcy, !marketConvention, inverse(ask), inverse(bid), currencyProvider);
    }

    @Override
    public FxRate createInverse(final int precision) {
        return new FxRateImpl(currencyPair.createInverse(), crossCcy, !marketConvention, setScale(inverse(ask), precision), setScale(inverse(bid),
                precision), currencyProvider);
    }

    @Override
    public BigDecimal getBid() {
        return bid;
    }

    @Override
    public BigDecimal getAsk() {
        return ask;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    @Override
    public Optional<String> getCrossCcy() {
        return Optional.ofNullable(crossCcy);
    }

    @Override
    public boolean isMarketConvention() {
        return marketConvention;
    }

    @Override
    public BigDecimal getMid() {
        BigDecimal mid;
        if (isNullOrZero(ask)) {
            mid = bid;
        } else if (isNullOrZero(bid)) {
            mid = ask;
        } else {
            mid = divide(PRECISION_FOR_INVERSE, bid.add(ask), TWO, BigDecimal.ROUND_HALF_UP);
        }
        return mid;
    }

    @Override
    public BigDecimal getBidInMarketConvention() {
        return marketConvention ? bid : inverse(ask);
    }

    @Override
    public BigDecimal getMidInMarketConvention() {
        return marketConvention ? getMid() : inverse(getMid());
    }

    @Override
    public BigDecimal getAskInMarketConvention() {
        return marketConvention ? ask : inverse(bid);
    }

    @Override
    public String toString() {
        return getCurrencyPair() + " B:" + getBid() + " A:" + getAsk();
    }

    @Override
    public CurrencyAmount convertAmountUsingMid(final CurrencyAmount originalAmount) {
        if (!currencyPair.containsCcy(originalAmount.getCurrency())) {
            throw new IllegalArgumentException("The original ccy [" + originalAmount.getCurrency() + "] must be one of the pair's " + currencyPair);
        }
        final boolean ccy1IsOriginal = currencyPair.getCcy1().equals(originalAmount.getCurrency());
        final int decPlace = currencyProvider.getFractionDigits(ccy1IsOriginal ? currencyPair.getCcy2() : currencyPair.getCcy1());
        final int rounding = currencyProvider.getRounding(ccy1IsOriginal ? currencyPair.getCcy2() : currencyPair.getCcy1());

        return ccy1IsOriginal ? new Cash(currencyPair.getCcy2(), setScale(multiply(originalAmount.getAmount(), getMid()), decPlace, rounding))
                : new Cash(currencyPair.getCcy1(), setScale(
                        divide(setScale(originalAmount.getAmount(), PRECISION_FOR_INVERSE), getMid(), BigDecimal.ROUND_HALF_UP), decPlace, rounding));
    }

    @Override
    public CurrencyAmount convertAmountUsingBidOrAsk(final CurrencyAmount originalAmount) {
        if (!currencyPair.containsCcy(originalAmount.getCurrency())) {
            throw new IllegalArgumentException("The original ccy [" + originalAmount.getCurrency() + "] must be one of the pair's " + currencyPair);
        }
        final boolean ccy1IsOriginal = currencyPair.getCcy1().equals(originalAmount.getCurrency());
        final int decPlace = currencyProvider.getFractionDigits(ccy1IsOriginal ? currencyPair.getCcy2() : currencyPair.getCcy1());
        final int rounding = currencyProvider.getRounding(ccy1IsOriginal ? currencyPair.getCcy2() : currencyPair.getCcy1());

        return ccy1IsOriginal ? new Cash(currencyPair.getCcy2(), setScale(multiply(originalAmount.getAmount(), bid), decPlace, rounding)) : new Cash(
                currencyPair.getCcy1(), setScale(divide(setScale(originalAmount.getAmount(), PRECISION_FOR_INVERSE), ask, BigDecimal.ROUND_HALF_UP),
                        decPlace, rounding));
    }

    /**
     * Calculates the amount to pay given an amount to buy, e.g. how much should you pay in Y if you are buying n X?
     * More explicitly, how many EURos should you pay to buy 1,000 USD?
     * Given EUR/USD rate, this would be 1,000 / bid.
     * @param amountToBuy amount you want to buy.
     * @return amount you have to pay in the other ccy.
     */
    @Override
    public CurrencyAmount getPaymentAmountForBuying(final CurrencyAmount amountToBuy) {
        boolean inverse = amountToBuy.getCurrency().equals(currencyPair.getCcy2());
        final String targetCcy = inverse ? currencyPair.getCcy1() : currencyPair.getCcy2();

        int decPlace = DEC_PLACE_FOR_MONEY;

        try {
            decPlace = Currency.getInstance(targetCcy).getDefaultFractionDigits();
        } catch (IllegalArgumentException iae) {
            // do nothing
        }

        return inverse ? new Cash(targetCcy, setScale(
                BigDecimalUtil.divide(PRECISION_FOR_INVERSE, amountToBuy.getAmount(), bid, BigDecimal.ROUND_HALF_UP), decPlace)) : new Cash(
                targetCcy, setScale(amountToBuy.getAmount().multiply(ask), decPlace));
    }

    /**
     * Calculates the amount the seller would receive given an amount to sell, e.g. how many Y will you get if you are selling n X?
     * More explicitly, how many EURos should you get to sell 1,000 USD?
     * Given EUR/USD rate, this would be 1,000 / ask.
     * @param amountToSell amount you want to sell.
     * @return amount you will receive in the other ccy.
     */
    @Override
    public CurrencyAmount getReceiptAmountForSelling(final CurrencyAmount amountToSell) {
        boolean inverse = amountToSell.getCurrency().equals(currencyPair.getCcy2());
        final String targetCcy = inverse ? currencyPair.getCcy1() : currencyPair.getCcy2();
        int decPlace = DEC_PLACE_FOR_MONEY;

        try {
            decPlace = Currency.getInstance(targetCcy).getDefaultFractionDigits();
        } catch (IllegalArgumentException iae) {
            // do nothing
        }
        return inverse ? //
        new Cash(targetCcy, setScale(BigDecimalUtil.divide(PRECISION_FOR_INVERSE, amountToSell.getAmount(), ask, BigDecimal.ROUND_HALF_UP), decPlace)) //
                : //
                new Cash(targetCcy, setScale(amountToSell.getAmount().multiply(bid), decPlace)) //
        ;
    }
}
