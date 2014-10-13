package net.objectlab.kit.fxcalc;

import static net.objectlab.kit.util.BigDecimalUtil.bd;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import net.objectlab.kit.util.BigDecimalUtil;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrossRateCalculatorTest {
    private static final Logger LOG = LoggerFactory.getLogger(CrossRateCalculatorTest.class);

    @Test(expected = IllegalArgumentException.class)
    public void tesCrossWithoutCrossCcy() {
        LOG.debug("badCross --------------------------------");
        final CurrencyPair tgt = new CurrencyPair("EUR", "CHF");
        final FxRateImpl fx1 = new FxRateImpl(new CurrencyPair("GBP", "CHF"), null, true, bd("2.1702"), bd("2.1707"), new JdkCurrencyProvider());
        final FxRateImpl fx2 = new FxRateImpl(new CurrencyPair("EUR", "AED"), null, true, bd("0.7374"), bd("0.7379"), new JdkCurrencyProvider());
        CrossRateCalculator.calculateCross(tgt, fx1, fx2, 4, 4, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
    }

    @Test(expected = IllegalArgumentException.class)
    public void tesCrossWithCrossInTarget() {
        LOG.debug("badCross --------------------------------");
        final CurrencyPair tgt = new CurrencyPair("EUR", "CHF");
        final FxRateImpl fx1 = new FxRateImpl(new CurrencyPair("EUR", "JPY"), null, true, bd("2.1702"), bd("2.1707"), new JdkCurrencyProvider());
        final FxRateImpl fx2 = new FxRateImpl(new CurrencyPair("EUR", "AED"), null, true, bd("0.7374"), bd("0.7379"), new JdkCurrencyProvider());
        CrossRateCalculator.calculateCross(tgt, fx1, fx2, 4, 4, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
    }

    @Test
    public void crossWithNonUsdCross2() {
        LOG.debug("crossWithNonUsdCross2 --------------------------------");
        final CurrencyPair tgt = new CurrencyPair("EUR", "CHF");
        final FxRateImpl fx1 = new FxRateImpl(new CurrencyPair("GBP", "CHF"), null, true, bd("2.1702"), bd("2.1707"), new JdkCurrencyProvider());
        final FxRateImpl fx2 = new FxRateImpl(new CurrencyPair("EUR", "GBP"), null, true, bd("0.7374"), bd("0.7379"), new JdkCurrencyProvider());
        FxRate fxRate = CrossRateCalculator.calculateCross(tgt, fx1, fx2, 4, 4, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgt);
        assertThat(fxRate.getBid()).describedAs("Bid non USD Cross 2 {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.6003"));
        assertThat(fxRate.getAsk()).describedAs("Ask non USD Cross 2 {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.6018"));
        fxRate = CrossRateCalculator.calculateCross(tgt, fx2, fx1, 4, 4, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgt);
        assertThat(fxRate.getBid()).describedAs("Bid non USD Cross 2 {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.6003"));
        assertThat(fxRate.getAsk()).describedAs("Ask non USD Cross 2 {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.6018"));
        CurrencyPair tgtInverse = tgt.createInverse();
        fxRate = CrossRateCalculator.calculateCross(tgtInverse, fx1, fx2, 6, 6, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgtInverse);
        assertThat(fxRate.getBid()).describedAs("Bid non USD Cross 2 {}", tgtInverse).isEqualByComparingTo(BigDecimalUtil.bd("0.624313"));
        assertThat(fxRate.getAsk()).describedAs("Ask non USD Cross 2 {}", tgtInverse).isEqualByComparingTo(BigDecimalUtil.bd("0.624881"));
        fxRate = CrossRateCalculator.calculateCross(tgtInverse, fx2, fx1, 6, 6, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgtInverse);
        assertThat(fxRate.getBid()).describedAs("Bid non USD Cross 2 {}", tgtInverse).isEqualByComparingTo(BigDecimalUtil.bd("0.624313"));
        assertThat(fxRate.getAsk()).describedAs("Ask non USD Cross 2 {}", tgtInverse).isEqualByComparingTo(BigDecimalUtil.bd("0.624881"));
    }

    @Test
    public void crossWithNonUsdCross() {
        LOG.debug("crossWithNonUsdCross --------------------------------");
        final CurrencyPair tgt = new CurrencyPair("CHF", "JPY");
        final FxRateImpl fx1 = new FxRateImpl(new CurrencyPair("GBP", "CHF"), null, true, bd("2.1702"), bd("2.1707"), new JdkCurrencyProvider());
        final FxRateImpl fx2 = new FxRateImpl(new CurrencyPair("GBP", "JPY"), null, true, bd("192.70"), bd("193"), new JdkCurrencyProvider());
        FxRate fxRate = CrossRateCalculator.calculateCross(tgt, fx1, fx2, 4, 4, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgt);
        assertThat(fxRate.getBid()).describedAs("Bid Non-USD Cross {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("88.7732"));
        assertThat(fxRate.getAsk()).describedAs("Ask Non-USD Cross {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("88.9319"));
        fxRate = CrossRateCalculator.calculateCross(tgt, fx2, fx1, 4, 4, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgt);
        assertThat(fxRate.getBid()).describedAs("Bid Non-USD Cross {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("88.7732"));
        assertThat(fxRate.getAsk()).describedAs("Ask Non-USD Cross {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("88.9319"));
        CurrencyPair tgtInverse = tgt.createInverse();
        fxRate = CrossRateCalculator.calculateCross(tgtInverse, fx1, fx2, 6, 6, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgtInverse);
        assertThat(fxRate.getBid()).describedAs("Bid Non-USD Cross {}", tgtInverse).isEqualByComparingTo(BigDecimalUtil.bd("0.011245"));
        assertThat(fxRate.getAsk()).describedAs("Ask Non-USD Cross {}", tgtInverse).isEqualByComparingTo(BigDecimalUtil.bd("0.011265"));
        fxRate = CrossRateCalculator.calculateCross(tgtInverse, fx2, fx1, 6, 6, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgtInverse);
        assertThat(fxRate.getBid()).describedAs("Bid Non-USD Cross {}", tgtInverse).isEqualByComparingTo(BigDecimalUtil.bd("0.011245"));
        assertThat(fxRate.getAsk()).describedAs("Ask Non-USD Cross {}", tgtInverse).isEqualByComparingTo(BigDecimalUtil.bd("0.011265"));
    }

    @Test
    public void crossWithMixedUsd() {
        LOG.debug("crossWithMixedUsd --------------------------------");
        final CurrencyPair tgt = new CurrencyPair("EUR", "SGD");
        final FxRateImpl fx1 = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true, bd("1.2166"), bd("1.2171"), new JdkCurrencyProvider());
        final FxRateImpl fx2 = new FxRateImpl(new CurrencyPair("USD", "SGD"), null, true, bd("1.6782"), bd("1.6792"), new JdkCurrencyProvider());
        FxRate fxRate = CrossRateCalculator.calculateCross(tgt, fx1, fx2, 4, 4, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgt);
        assertThat(fxRate.getBid()).describedAs("Bid Mixed USD {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("2.0417"));
        assertThat(fxRate.getAsk()).describedAs("Ask Mixed USD {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("2.0438"));
        fxRate = CrossRateCalculator.calculateCross(tgt, fx2, fx1, 4, 4, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgt);
        assertThat(fxRate.getBid()).describedAs("Bid Mixed USD {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("2.0417"));
        assertThat(fxRate.getAsk()).describedAs("Ask Mixed USD {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("2.0438"));
    }

    @Test
    public void crossWithUsdMinor() {
        LOG.debug("crossWithUsdMinor --------------------------------");
        final CurrencyPair tgt = new CurrencyPair("EUR", "AUD");
        final FxRateImpl fx1 = new FxRateImpl(new CurrencyPair("EUR", "USD"), null, true, bd("1.2166"), bd("1.2171"), new JdkCurrencyProvider());
        final FxRateImpl fx2 = new FxRateImpl(new CurrencyPair("AUD", "USD"), null, true, bd("0.6834"), bd("0.6839"), new JdkCurrencyProvider());
        FxRate fxRate = CrossRateCalculator.calculateCross(tgt, fx1, fx2, 4, 4, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgt);
        assertThat(fxRate.getBid()).describedAs("Bid USD Minor {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.7789"));
        assertThat(fxRate.getAsk()).describedAs("Ask USD Minor {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.7809"));
        fxRate = CrossRateCalculator.calculateCross(tgt, fx2, fx1, 4, 4, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgt);
        assertThat(fxRate.getBid()).describedAs("Bid USD Minor {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.7789"));
        assertThat(fxRate.getAsk()).describedAs("Ask USD Minor {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.7809"));
    }

    @Test
    public void crossWithUsdMajorInversed() {
        LOG.debug("crossWithUsdMajorInversed --------------------------------");
        final CurrencyPair tgt = new CurrencyPair("CAD", "SGD");
        final FxRateImpl fx1 = new FxRateImpl(new CurrencyPair("USD", "CAD"), null, true, bd("1.4874"), bd("1.4879"), new JdkCurrencyProvider());
        final FxRateImpl fx2 = new FxRateImpl(new CurrencyPair("USD", "SGD"), null, true, bd("1.6782"), bd("1.6792"), new JdkCurrencyProvider());
        FxRate fxRate = CrossRateCalculator.calculateCross(tgt, fx1.createInverse(), fx2, 4, 4, StandardMajorCurrencyRanking.getDefault(),
                BigDecimal.ROUND_HALF_UP, BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgt);
        assertThat(fxRate.getBid()).describedAs("Bid USD Major Inverted {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.1279"));
        assertThat(fxRate.getAsk()).describedAs("Ask USD Major Inverted {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.1289"));
        fxRate = CrossRateCalculator.calculateCross(tgt, fx1.createInverse(), fx2.createInverse(), 4, 4, StandardMajorCurrencyRanking.getDefault(),
                BigDecimal.ROUND_HALF_UP, BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgt);
        assertThat(fxRate.getBid()).describedAs("Bid USD Major Inverted {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.1279"));
        assertThat(fxRate.getAsk()).describedAs("Ask USD Major Inverted {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.1289"));
        CurrencyPair tgtInverted = tgt.createInverse();
        fxRate = CrossRateCalculator.calculateCross(tgtInverted, fx1.createInverse(), fx2.createInverse(), 4, 4,
                StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP, BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgtInverted);
        assertThat(fxRate.getBid()).describedAs("Bid USD Major Inverted {}", tgtInverted).isEqualByComparingTo(BigDecimalUtil.bd("0.8857"));
        assertThat(fxRate.getAsk()).describedAs("Ask USD Major Inverted {}", tgtInverted).isEqualByComparingTo(BigDecimalUtil.bd("0.8866"));
    }

    @Test
    public void testWithUsdMajor() {
        final CurrencyPair tgt = new CurrencyPair("CAD", "SGD");
        final FxRateImpl fx1 = new FxRateImpl(new CurrencyPair("USD", "CAD"), null, true, bd("1.4874"), bd("1.4879"), new JdkCurrencyProvider());
        final FxRateImpl fx2 = new FxRateImpl(new CurrencyPair("USD", "SGD"), null, true, bd("1.6782"), bd("1.6792"), new JdkCurrencyProvider());
        FxRate fxRate = CrossRateCalculator.calculateCross(tgt, fx1, fx2, 4, 4, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgt);
        assertThat(fxRate.getBid()).describedAs("Bid USD Major {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.1279"));
        assertThat(fxRate.getAsk()).describedAs("Ask USD Major {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.1289"));

        fxRate = CrossRateCalculator.calculateCross(tgt, fx2, fx1, 4, 4, StandardMajorCurrencyRanking.getDefault(), BigDecimal.ROUND_HALF_UP,
                BigDecimal.ROUND_HALF_UP, new JdkCurrencyProvider());
        assertThat(fxRate.getCurrencyPair()).isEqualTo(tgt);
        assertThat(fxRate.getBid()).describedAs("Bid USD Major {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.1279"));
        assertThat(fxRate.getAsk()).describedAs("Ask USD Major {}", tgt).isEqualByComparingTo(BigDecimalUtil.bd("1.1289"));
    }
}
