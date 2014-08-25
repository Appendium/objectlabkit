package net.objectlab.kit.fxcalc;

import java.math.BigDecimal;
import java.util.Optional;

import net.objectlab.kit.util.BigDecimalUtil;

/**
 * Represents an FxRate.
 * @author Benoit Xhenseval
 */
public class FxRateImpl implements FxRate {
    private final CurrencyPair currencyPair;
    private final Optional<String> crossCcy;
    private final boolean marketConvention;
    private BigDecimal bid;
    private BigDecimal ask;

    public void setBid(final BigDecimal bid) {
        this.bid = bid;
    }

    public void setAsk(final BigDecimal ask) {
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
        b.append(currencyPair).append(" Mkt Convention:").append(marketConvention).append(System.getProperty("line.separator"));
        b.append("Quoter buys  ").append(currencyPair.getCcy1()).append(" and sells ").append(currencyPair.getCcy2()).append(" at ").append(bid)
                .append(System.getProperty("line.separator"));
        b.append("Quoter sells ").append(currencyPair.getCcy1()).append(" and buys  ").append(currencyPair.getCcy2()).append(" at ").append(ask)
                .append(System.getProperty("line.separator"));

        return b.toString();
    }

    @Override
    public FxRate createInverse() {
        final FxRateImpl f = new FxRateImpl(currencyPair.createInverse(), crossCcy.isPresent() ? crossCcy.get() : null, !marketConvention);
        f.setAsk(BigDecimalUtil.inverse(bid));
        f.setBid(BigDecimalUtil.inverse(ask));
        return f;
    }

    @Override
    public FxRate createInverse(int precision) {
        final FxRateImpl f = new FxRateImpl(currencyPair.createInverse(), crossCcy.isPresent() ? crossCcy.get() : null, !marketConvention);
        f.setAsk(BigDecimalUtil.setScale(BigDecimalUtil.inverse(bid), precision));
        f.setBid(BigDecimalUtil.setScale(BigDecimalUtil.inverse(ask), precision));
        return f;
    }

    public FxRateImpl(final CurrencyPair currencyPair, final String crossCcy, final boolean marketConvention) {
        super();
        this.currencyPair = currencyPair;
        this.crossCcy = Optional.ofNullable(crossCcy);
        this.marketConvention = marketConvention;
    }

    public FxRateImpl(final CurrencyPair currencyPair, final String crossCcy, final boolean marketConvention, final BigDecimal bid,
            final BigDecimal ask) {
        this(currencyPair, crossCcy, marketConvention);
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
        return crossCcy;
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
