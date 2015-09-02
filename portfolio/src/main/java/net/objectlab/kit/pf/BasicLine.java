package net.objectlab.kit.pf;

import java.math.BigDecimal;

public class BasicLine implements ExistingPortfolioLine {
    private String assetCode;
    private String assetName;
    private BigDecimal priceInPortfolioCcy;
    private BigDecimal quantity;
    private BigDecimal valueInPortfolioCcy;

    public BasicLine() {
        super();
    }

    public BasicLine(final String assetCode, final String assetName, final BigDecimal priceInPortfolioCcy, final BigDecimal quantity,
            final BigDecimal valueInPortfolioCcy) {
        super();
        this.assetCode = assetCode;
        this.assetName = assetName;
        this.priceInPortfolioCcy = priceInPortfolioCcy;
        this.quantity = quantity;
        this.valueInPortfolioCcy = valueInPortfolioCcy;
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
    public BigDecimal getPriceInPortfolioCcy() {
        return priceInPortfolioCcy;
    }

    public void setPriceInPortfolioCcy(final BigDecimal priceInPortfolioCcy) {
        this.priceInPortfolioCcy = priceInPortfolioCcy;
    }

    @Override
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(final BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public BigDecimal getValueInPortfolioCcy() {
        return valueInPortfolioCcy;
    }

    public void setValueInPortfolioCcy(final BigDecimal valueInPortfolioCcy) {
        this.valueInPortfolioCcy = valueInPortfolioCcy;
    }
}
