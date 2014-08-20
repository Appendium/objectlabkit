package net.objectlab.kit.fxcalc;

import java.math.BigDecimal;
import java.util.Optional;

import net.objectlab.kit.util.BigDecimalUtil;

public class FxRateImpl implements FxRate {
    private final CurrencyPair currencyPair;
    private final Optional<String> crossCcy;
    private final boolean marketConvention;
    private BigDecimal bid;
    private BigDecimal ask;

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    @Override
    public String getDescription() {
        StringBuilder b = new StringBuilder();
        b.append(currencyPair).append(" Mkt Convention:").append(marketConvention).append(System.getProperty("line.separator"));
        b.append("Bank buys  ").append(currencyPair.getCcy1()).append(" and sells ").append(currencyPair.getCcy2()).append(" at ").append(bid)
                .append(System.getProperty("line.separator"));
        b.append("Bank sells ").append(currencyPair.getCcy1()).append(" and buys  ").append(currencyPair.getCcy2()).append(" at ").append(ask)
                .append(System.getProperty("line.separator"));

        return b.toString();
    }

    @Override
    public FxRate createInverse() {
        FxRateImpl f = new FxRateImpl(currencyPair.createInverse(), crossCcy.isPresent() ? crossCcy.get() : null, !marketConvention);
        f.setAsk(BigDecimalUtil.inverse(bid));
        f.setBid(BigDecimalUtil.inverse(ask));
        return f;
    }

    public FxRateImpl(final CurrencyPair currencyPair, final String crossCcy, final boolean marketConvention) {
        super();
        this.currencyPair = currencyPair;
        this.crossCcy = Optional.ofNullable(crossCcy);
        this.marketConvention = marketConvention;
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
}
