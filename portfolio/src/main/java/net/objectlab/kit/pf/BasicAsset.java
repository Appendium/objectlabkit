package net.objectlab.kit.pf;

public class BasicAsset implements AssetDetails {
    private String assetCode;
    private String assetName;
    private String ultimateIssuerCode;

    public BasicAsset(final String assetCode, final String assetName, final String ultimateIssuerCode) {
        super();
        this.assetCode = assetCode;
        this.assetName = assetName;
        this.ultimateIssuerCode = ultimateIssuerCode;
    }

    public BasicAsset() {
        super();
    }

    @Override
    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(final String assetCode) {
        this.assetCode = assetCode;
    }

    @Override
    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(final String assetName) {
        this.assetName = assetName;
    }

    @Override
    public String getUltimateIssuerCode() {
        return ultimateIssuerCode;
    }

    public void setUltimateIssuerCode(final String ultimateIssuerCode) {
        this.ultimateIssuerCode = ultimateIssuerCode;
    }
}
