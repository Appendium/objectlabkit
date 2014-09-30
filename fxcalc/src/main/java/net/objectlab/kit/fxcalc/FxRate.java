package net.objectlab.kit.fxcalc;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Represents a read-only view on an FxRate.
 * @author Benoit Xhenseval
 */
public interface FxRate {
    /**
     * The CurrencyPair as in Ccy1/Ccy2 where the Bid will be the price for selling ccy1 and buying ccy2.
     */
    CurrencyPair getCurrencyPair();

    /**
     * This FxRate might come from a combination of 2 rates through a cross currency, if it is the case,
     * return the cross currency.
     */
    Optional<String> getCrossCcy();

    /**
     * true if the CurrencyPair follows market convention, e.g. EUR/USD and not USD/EUR (but it gets more complicated for
     * other cross-currencies, eg CHF/JPY?)
     */
    boolean isMarketConvention();

    /**
     * The price at which the 'quoter' is willing to sell ccy1 and buy ccy2.
     */
    BigDecimal getBid();

    /**
     * Mid = (bid + ask) / 2
     */
    BigDecimal getMid();

    /**
     * The price at which the 'quoter' is willing to buy ccy1 and sell ccy2.
     */
    BigDecimal getAsk();

    /**
     * Useful for human readable display, returns the same value as Bid if FxRate is in market convention otherwise 1/ask.
     */
    BigDecimal getBidInMarketConvention();

    /**
     * Mid = (bid + ask) / 2
     */
    BigDecimal getMidInMarketConvention();

    /**
     * Useful for human readable display, returns the same value as Ask if FxRate is in market convention otherwise 1/bid.
     */
    BigDecimal getAskInMarketConvention();

    /**
     * Create the FX Rate for ccy2 / ccy1, return a new FxRate; the inverse of bid / ask are swapped around.
     */
    FxRate createInverse();

    /**
     * Create the FX Rate for ccy2 / ccy1, return a new FxRate with given precision; the inverse of bid / ask are swapped around.
     */
    FxRate createInverse(int precision);

    /**
     * Nice human readable description of the FX Rate, useful reminder.
     */
    String getDescription();

    /**
     * Given a monetary amount in the original currency, calculate the resulting amount in the other currency, using MID rate.
     * @param originalAmount the monetary amount (currency + amount)
     * @return depending on the originalAmount.currency, it will be amount / mid or amount * mid, rounded to 2 DP.
     * @throws IllegalArgumentException if the originalAmount.currency is not one of the currency pair.
     */
    CurrencyAmount convertAmountUsingMid(CurrencyAmount originalAmount);

    /**
     * Given a monetary amount in the original currency, calculate the resulting amount in the other currency, using BID or ASK
     * depending on the originalAmount.currency.
     * @param originalAmount the monetary amount (currency + amount)
     * @return depending on the originalAmount.currency, it will be amount / bid|ask or amount * bid|ask, rounded to 2 DP.
     * @throws IllegalArgumentException if the originalAmount.currency is not one of the currency pair.
     */
    CurrencyAmount convertAmountUsingBidOrAsk(CurrencyAmount originalAmount);

    /**
     * Calculates the amount to pay given an amount to buy, e.g. how much should you pay in Y if you are buying n X?
     * More explicitly, how many EURos should you pay to buy 1,000 USD?
     * Given EUR/USD rate, this would be 1,000 / bid.
     * @param amountToBuy amount you want to buy.
     * @return amount you have to pay in the other currency.
     */
    CurrencyAmount getPaymentAmountForBuying(CurrencyAmount amountToBuy);

    /**
     * Calculates the amount the seller would receive given an amount to sell, e.g. how many Y will you get if you are selling n X?
     * More explicitly, how many EURos should you get to sell 1,000 USD?
     * Given EUR/USD rate, this would be 1,000 / ask.
     * @param amountToSell amount you want to sell.
     * @return amount you will receive in the other currency.
     */
    CurrencyAmount getReceiptAmountForSelling(CurrencyAmount amountToSell);
}
