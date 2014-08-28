package net.objectlab.kit.fxcalc;

import java.math.BigDecimal;
import java.util.Optional;

import net.objectlab.kit.util.BigDecimalUtil;

/**
 * Represents an immutable FxRate.
 * @author Benoit Xhenseval
 */
public class FxRateImpl implements FxRate {
    private final CurrencyPair currencyPair;
    private final String crossCcy;
    private final boolean marketConvention;
    private final BigDecimal bid;
    private final BigDecimal ask;

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
        b.append(System.getProperty("line.separator"));
        b.append("Quoter buys  ").append(currencyPair.getCcy1()).append(" and sells ").append(currencyPair.getCcy2()).append(" at ").append(bid)
                .append(System.getProperty("line.separator"));
        b.append("Quoter sells ").append(currencyPair.getCcy1()).append(" and buys  ").append(currencyPair.getCcy2()).append(" at ").append(ask);

        return b.toString();
    }

    @Override
    public FxRate createInverse() {
        return new FxRateImpl(currencyPair.createInverse(), crossCcy, !marketConvention, BigDecimalUtil.inverse(ask), BigDecimalUtil.inverse(bid));
    }

    @Override
    public FxRate createInverse(int precision) {
        return new FxRateImpl(currencyPair.createInverse(), crossCcy, !marketConvention, BigDecimalUtil.setScale(BigDecimalUtil.inverse(ask),
                precision), BigDecimalUtil.setScale(BigDecimalUtil.inverse(bid), precision));
    }

    public FxRateImpl(final CurrencyPair currencyPair, final String crossCcy, final boolean marketConvention, final BigDecimal bid,
            final BigDecimal ask) {
        this.currencyPair = currencyPair;
        this.crossCcy = crossCcy;
        this.marketConvention = marketConvention;
        this.bid = bid;
        this.ask = ask;
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
        if (BigDecimalUtil.isNullOrZero(ask)) {
            mid = bid;
        } else if (BigDecimalUtil.isNullOrZero(bid)) {
            mid = ask;
        } else {
            mid = BigDecimalUtil.divide(10, bid.add(ask), BigDecimal.valueOf(2L), BigDecimal.ROUND_HALF_UP);
        }
        return mid;
    }

    @Override
    public BigDecimal getBidInMarketConvention() {
        return marketConvention ? bid : BigDecimalUtil.inverse(ask);
    }

    @Override
    public BigDecimal getMidInMarketConvention() {
        return marketConvention ? getMid() : BigDecimalUtil.inverse(getMid());
    }

    @Override
    public BigDecimal getAskInMarketConvention() {
        return marketConvention ? ask : BigDecimalUtil.inverse(bid);
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
        return currencyPair.getCcy1().equals(originalAmount.getCurrency()) ? //
        new Money(currencyPair.getCcy2(), BigDecimalUtil.setScale(BigDecimalUtil.multiply(originalAmount.getAmount(), getMid()), 2))
                : new Money(currencyPair.getCcy1(), BigDecimalUtil.setScale(
                        BigDecimalUtil.divide(BigDecimalUtil.setScale(originalAmount.getAmount(), 10), getMid(), BigDecimal.ROUND_HALF_UP), 2)) //
        ;
    }

    @Override
    public MonetaryAmount convertAmountUsingBidOrAsk(final MonetaryAmount originalAmount) {
        if (!currencyPair.containsCcy(originalAmount.getCurrency())) {
            throw new IllegalArgumentException("The original ccy [" + originalAmount.getCurrency() + "] must be one of the pair's " + currencyPair);
        }
        return currencyPair.getCcy1().equals(originalAmount.getCurrency()) ? //
        new Money(currencyPair.getCcy2(), BigDecimalUtil.setScale(BigDecimalUtil.multiply(originalAmount.getAmount(), bid), 2))
                : new Money(currencyPair.getCcy1(), BigDecimalUtil.setScale(
                        BigDecimalUtil.divide(BigDecimalUtil.setScale(originalAmount.getAmount(), 10), ask, BigDecimal.ROUND_HALF_UP), 2)) //
        ;
    }
}
