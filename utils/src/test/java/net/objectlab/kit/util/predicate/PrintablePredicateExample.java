package net.objectlab.kit.util.predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.objectlab.kit.util.StringUtil;

/**
 * Example for the PrintablePredicate.
 * Note that the example uses Lombok (highly recommended).
 * @author Benoit Xhenseval
 */
public class PrintablePredicateExample {
    @Data
    @AllArgsConstructor
    private static class Asset { // Using Lombok, replaces Get/Set and Constructor
        private String assetClass;
        private String assetType;
        private boolean active;
    }

    /**
     * Predicate that tests for values Asset Class.
     */
    public static Predicate<Asset> hasAssetClass(String... assetClass) {
        return new PrintablePredicate<>("AssetClass", t -> StringUtil.equalsAnyIgnoreCase(t.getAssetClass(), assetClass), assetClass);
    }

    /**
     * Predicate that tests for values Asset Type.
     */
    public static Predicate<Asset> hasAssetType(String... assetType) {
        return new PrintablePredicate<>("AssetType", t -> StringUtil.equalsAnyIgnoreCase(t.getAssetType(), assetType), assetType);
    }

    /**
     * Predicate that tests if asset is active.
     */
    public static Predicate<Asset> isActive() {
        return new PrintablePredicate<>("Active", t -> t.isActive());
    }

    private static List<Asset> buildUniverse() {
        List<Asset> assets = new ArrayList<>();
        assets.add(new Asset("Equity", "Stock", true));
        assets.add(new Asset("Bond", "Government Bond", true));
        assets.add(new Asset("Bond", "Corporate Bond", true));
        assets.add(new Asset("Commodities", "Corn", true));
        assets.add(new Asset("Commodities", "Oil", false));
        return assets;
    }

    public static void main(String[] args) {
        List<Asset> assets = buildUniverse();

        final Predicate<Asset> activeBond = hasAssetClass("Bond", "Commodities").and(isActive());
        final long count = assets.stream().filter(activeBond).count(); // 3
        System.out.println(activeBond + " Returns " + count); // Prints "AssetClass in (Bond, Commodities) AND Active Returns 3"
    }

}
