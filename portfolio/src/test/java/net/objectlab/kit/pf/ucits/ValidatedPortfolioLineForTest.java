package net.objectlab.kit.pf.ucits;

import java.math.BigDecimal;
import java.util.List;

import net.objectlab.kit.pf.RuleIssue;
import net.objectlab.kit.pf.Severity;
import net.objectlab.kit.pf.ValidatedPortfolioLine;

public class ValidatedPortfolioLineForTest implements ValidatedPortfolioLine {
    private String assetCode;
    private String assetName;
    private BigDecimal quantity;
    private BigDecimal priceInPortfolioCcy;
    private BigDecimal valueInPortfolioCcy;
    private BigDecimal allocationWeight;
    private boolean valid;
    private List<RuleIssue> issues;

    public ValidatedPortfolioLineForTest() {
    }

    public ValidatedPortfolioLineForTest(final ValidatedPortfolioLine t) {
        this.assetCode = t.getAssetCode();
        this.assetName = t.getAssetName();
        this.quantity = t.getQuantity();
        this.priceInPortfolioCcy = t.getPriceInPortfolioCcy();
        this.allocationWeight = t.getAllocationWeight();
        this.valueInPortfolioCcy = t.getValueInPortfolioCcy();
        this.valid = t.isValid();
        this.issues = t.getIssues();
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    public void setValid(final boolean valid) {
        this.valid = valid;
    }

    @Override
    public List<RuleIssue> getIssues() {
        return issues;
    }

    public void setIssues(final List<RuleIssue> issues) {
        this.issues = issues;
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
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(final BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public BigDecimal getPriceInPortfolioCcy() {
        return priceInPortfolioCcy;
    }

    public void setPriceInPortfolioCcy(final BigDecimal priceInPortfolioCcy) {
        this.priceInPortfolioCcy = priceInPortfolioCcy;
    }

    @Override
    public BigDecimal getValueInPortfolioCcy() {
        return valueInPortfolioCcy;
    }

    public void setValueInPortfolioCcy(final BigDecimal valueInPortfolioCcy) {
        this.valueInPortfolioCcy = valueInPortfolioCcy;
    }

    @Override
    public BigDecimal getAllocationWeight() {
        return allocationWeight;
    }

    @Override
    public void setAllocationWeight(final BigDecimal allocationWeight) {
        this.allocationWeight = allocationWeight;
    }

    @Override
    public void addIssue(Severity sev, String ruleName, String message) {
        // TODO Auto-generated method stub

    }
}
