package net.objectlab.kit.pf;

@FunctionalInterface
public interface AssetDetailsProvider {
    AssetDetails getDetails(String assetCode);
}
