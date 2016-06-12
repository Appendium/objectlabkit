package net.objectlab.kit.pf;

public interface AssetDetails {
    String getAssetCode();

    String getAssetName();

    /** @return Typically a LEI */
    String getUltimateIssuerCode();
}
