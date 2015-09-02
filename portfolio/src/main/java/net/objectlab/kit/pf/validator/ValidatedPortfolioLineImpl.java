package net.objectlab.kit.pf.validator;

import java.math.BigDecimal;
import java.text.NumberFormat;

import net.objectlab.kit.pf.ExistingPortfolioLine;
import net.objectlab.kit.pf.Severity;
import net.objectlab.kit.pf.ValidatedPortfolioLine;
import net.objectlab.kit.pf.Validation;
import net.objectlab.kit.util.BigDecimalUtil;
import net.objectlab.kit.util.StringUtil;

import org.apache.commons.lang.StringUtils;

public class ValidatedPortfolioLineImpl implements ValidatedPortfolioLine {
    private final ExistingPortfolioLine line;
    private final ValidationImpl validation = new ValidationImpl();
    private BigDecimal allocationWeight;

    public ValidatedPortfolioLineImpl(final ExistingPortfolioLine line) {
        this.line = line;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        NumberFormat defaultFormat = NumberFormat.getPercentInstance();

        b.append(StringUtils.leftPad(StringUtils.abbreviate(getAssetCode(), 20), 20));
        b.append(StringUtils.leftPad(StringUtils.abbreviate(getAssetName(), 30), 31));
        b.append(StringUtils.leftPad(StringUtils.abbreviate(BigDecimalUtil.format(getQuantity()), 10), 11));
        b.append(StringUtils.leftPad(StringUtils.abbreviate(BigDecimalUtil.format(getValueInPortfolioCcy()), 10), 11));
        b.append(StringUtils.leftPad(StringUtils.abbreviate(defaultFormat.format(getAllocationWeight()), 10), 11));
        b.append(StringUtils.leftPad(StringUtils.abbreviate(getValidation().toString(), 250), 10));
        b.append(StringUtil.NEW_LINE);
        return b.toString();
    }

    @Override
    public String getAssetCode() {
        return line.getAssetCode();
    }

    @Override
    public String getAssetName() {
        return line.getAssetName();
    }

    @Override
    public BigDecimal getQuantity() {
        return line.getQuantity();
    }

    @Override
    public BigDecimal getPriceInPortfolioCcy() {
        return line.getPriceInPortfolioCcy();
    }

    @Override
    public BigDecimal getValueInPortfolioCcy() {
        return line.getValueInPortfolioCcy();
    }

    @Override
    public BigDecimal getAllocationWeight() {
        return allocationWeight;
    }

    @Override
    public Validation getValidation() {
        return validation;
    }

    public void setAllocationWeight(final BigDecimal allocationWeight) {
        this.allocationWeight = allocationWeight;
    }

    public void addIssue(final Severity sev, final String ruleName, final String message) {
        validation.addIssue(sev, ruleName, message);
    }
}
