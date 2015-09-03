package net.objectlab.kit.pf.ucits;

import java.math.BigDecimal;

import net.objectlab.kit.pf.ValidatedPortfolioLine;
import net.objectlab.kit.pf.Validation;

public class ValidatedPortfolioLineForTest implements ValidatedPortfolioLine {
    private String assetCode;
    private String assetName;
    private BigDecimal quantity;
    private BigDecimal priceInPortfolioCcy;
    private BigDecimal valueInPortfolioCcy;
    private BigDecimal allocationWeight;
    private Validation validation;

    @Override
    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    @Override
    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    @Override
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public BigDecimal getPriceInPortfolioCcy() {
        return priceInPortfolioCcy;
    }

    public void setPriceInPortfolioCcy(BigDecimal priceInPortfolioCcy) {
        this.priceInPortfolioCcy = priceInPortfolioCcy;
    }

    @Override
    public BigDecimal getValueInPortfolioCcy() {
        return valueInPortfolioCcy;
    }

    public void setValueInPortfolioCcy(BigDecimal valueInPortfolioCcy) {
        this.valueInPortfolioCcy = valueInPortfolioCcy;
    }

    @Override
    public BigDecimal getAllocationWeight() {
        return allocationWeight;
    }

    public void setAllocationWeight(BigDecimal allocationWeight) {
        this.allocationWeight = allocationWeight;
    }

    @Override
    public Validation getValidation() {
        return validation;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }
}
