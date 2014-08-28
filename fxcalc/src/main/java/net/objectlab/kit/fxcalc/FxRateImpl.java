package net.objectlab.kit.fxcalc;

import static net.objectlab.kit.util.BigDecimalUtil.divide;
import static net.objectlab.kit.util.BigDecimalUtil.inverse;
import static net.objectlab.kit.util.BigDecimalUtil.isNullOrZero;
import static net.objectlab.kit.util.BigDecimalUtil.multiply;
import static net.objectlab.kit.util.BigDecimalUtil.setScale;

import java.math.BigDecimal;
import java.util.Optional;

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

    public FxRateImpl(final CurrencyPair currencyPair, final String crossCcy, final boolean marketConvention, final BigDecimal bid,
            final BigDecimal ask) {
        this.currencyPair = currencyPair;
        this.crossCcy = crossCcy;
        this.marketConvention = marketConvention;
        this.bid = bid;
        this.ask = ask;
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
        return new FxRateImpl(currencyPair.createInverse(), crossCcy, !marketConvention, inverse(ask), inverse(bid));
    }

    @Override
    public FxRate createInverse(final int precision) {
        return new FxRateImpl(currencyPair.createInverse(), crossCcy, !marketConvention, setScale(inverse(ask), precision), setScale(inverse(bid),
                precision));
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
    public MonetaryAmount convertAmountUsingMid(final MonetaryAmount originalAmount) {
        if (!currencyPair.containsCcy(originalAmount.getCurrency())) {
            throw new IllegalArgumentException("The original ccy [" + originalAmount.getCurrency() + "] must be one of the pair's " + currencyPair);
        }
        return currencyPair.getCcy1().equals(originalAmount.getCurrency()) ? new Money(currencyPair.getCcy2(), setScale(
                multiply(originalAmount.getAmount(), getMid()), DEC_PLACE_FOR_MONEY)) : new Money(currencyPair.getCcy1(), setScale(
                divide(setScale(originalAmount.getAmount(), PRECISION_FOR_INVERSE), getMid(), BigDecimal.ROUND_HALF_UP), DEC_PLACE_FOR_MONEY));
    }

    @Override
    public MonetaryAmount convertAmountUsingBidOrAsk(final MonetaryAmount originalAmount) {
        if (!currencyPair.containsCcy(originalAmount.getCurrency())) {
            throw new IllegalArgumentException("The original ccy [" + originalAmount.getCurrency() + "] must be one of the pair's " + currencyPair);
        }
        return currencyPair.getCcy1().equals(originalAmount.getCurrency()) ? new Money(currencyPair.getCcy2(), setScale(
                multiply(originalAmount.getAmount(), bid), DEC_PLACE_FOR_MONEY)) : new Money(currencyPair.getCcy1(), setScale(
                divide(setScale(originalAmount.getAmount(), PRECISION_FOR_INVERSE), ask, BigDecimal.ROUND_HALF_UP), DEC_PLACE_FOR_MONEY));
    }
}
