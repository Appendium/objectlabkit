package net.objectlab.kit.pf;

@FunctionalInterface
public interface AssetEligibilityProvider {
    boolean isEligible(String assetCode);
}
