package net.objectlab.kit.util.predicate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Predicate;

import org.junit.Test;

import net.objectlab.kit.util.StringUtil;

public class PrintablePredicateTest {
    private static class Asset {
        private String assetClass;
        private String assetType;
        private String state;

        public Asset(String assetClass, String assetType, String state) {
            super();
            this.assetClass = assetClass;
            this.assetType = assetType;
            this.state = state;
        }

        public String getAssetClass() {
            return assetClass;
        }

        public String getAssetType() {
            return assetType;
        }

        public String getState() {
            return state;
        }
    }

    public static Predicate<Asset> hasAssetClass(String... assetClass) {
        return new PrintablePredicate<>("AssetClass", t -> StringUtil.equalsAnyIgnoreCase(t.getAssetClass(), assetClass), assetClass);
    }

    public static Predicate<Asset> hasAssetType(String... assetType) {
        return new PrintablePredicate<>("AssetType", t -> StringUtil.equalsAnyIgnoreCase(t.getAssetType(), assetType), assetType);
    }

    public static Predicate<Asset> hasState(String... state) {
        return new PrintablePredicate<>("State", t -> StringUtil.equalsAnyIgnoreCase(t.getState(), state), state);
    }

    @Test
    public void testAnd() {
        // now Combine them:
        final Predicate<Asset> activeBondOrEquity = hasAssetClass("Equity", "Bond").and(hasState("Active"));
        assertThat(activeBondOrEquity.toString()).isEqualToIgnoringCase("AssetClass in (Equity, Bond) AND State=Active");
        // does it work?
        assertThat(activeBondOrEquity.test(new Asset("Equity", "Stock", "Active"))).isTrue();
        assertThat(activeBondOrEquity.test(new Asset("Bond", "Corporate Bond", "Active"))).isTrue();
        assertThat(activeBondOrEquity.test(new Asset("Bond", "Government Bond", "Matured"))).isFalse();

        // let's try negate
        final Predicate<Asset> notActiveBondOrEquity = activeBondOrEquity.negate();
        assertThat(notActiveBondOrEquity.toString()).isEqualToIgnoringCase("NOT (AssetClass in (Equity, Bond) AND State=Active)");
    }

    @Test
    public void testOr() {
        // now Combine them:
        final Predicate<Asset> equityOrGovB = hasAssetClass("Equity").or(hasAssetType("Government Bond")).and(hasState("Active"));
        assertThat(equityOrGovB.toString()).isEqualToIgnoringCase("(AssetClass=Equity OR AssetType=Government Bond) AND State=Active");
        // does it work?
        assertThat(equityOrGovB.test(new Asset("Equity", "Stock", "Active"))).isTrue();
        assertThat(equityOrGovB.test(new Asset("Bond", "Government Bond", "Active"))).isTrue();
        assertThat(equityOrGovB.test(new Asset("Bond", "Government Bond", "Matured"))).isFalse();
        assertThat(equityOrGovB.test(new Asset("Bond", "Corporate Bond", "Active"))).isFalse();

        // let's try negate
        final Predicate<Asset> notEquityNorGovB = equityOrGovB.negate();
        assertThat(notEquityNorGovB.toString()).isEqualToIgnoringCase("NOT ((AssetClass=Equity OR AssetType=Government Bond) AND State=Active)");
    }

}
