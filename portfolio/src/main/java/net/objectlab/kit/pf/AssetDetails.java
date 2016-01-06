package net.objectlab.kit.pf;

public interface AssetDetails {
    String getAssetCode();

    String getAssetName();

    /** Typically a LEI */
    String getUltimateIssuerCode();
}
