package net.objectlab.kit.pf.validator;

import java.util.ArrayList;
import java.util.List;

import net.objectlab.kit.pf.ExistingPortfolio;
import net.objectlab.kit.pf.Validation;
import net.objectlab.kit.pf.ValidationResults;
import net.objectlab.kit.util.StringUtil;

import org.apache.commons.lang.StringUtils;

public class Results implements ValidationResults {
    private final ValidationImpl portfolioValidation = new ValidationImpl();
    private final List<ValidatedPortfolioLineImpl> lines = new ArrayList<>();

    public Results(final ExistingPortfolio p) {
        p.getLines().forEach(t -> lines.add(new ValidatedPortfolioLineImpl(t)));
    }

    @Override
    public Validation getPortfolioValidation() {
        return portfolioValidation;
    }

    @Override
    public List<ValidatedPortfolioLineImpl> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(StringUtils.leftPad(StringUtils.abbreviate("Code", 20), 20));
        b.append(StringUtils.leftPad(StringUtils.abbreviate("Asset name", 30), 31));
        b.append(StringUtils.leftPad(StringUtils.abbreviate("Quantity", 10), 11));
        b.append(StringUtils.leftPad(StringUtils.abbreviate("Value", 10), 11));
        b.append(StringUtils.leftPad(StringUtils.abbreviate("Weight", 10), 11));
        b.append(StringUtils.leftPad(StringUtils.abbreviate(" Validation Issues", 250), 12));
        b.append(StringUtil.NEW_LINE);

        lines.forEach(t -> b.append(t.toString()).append(StringUtil.NEW_LINE));

        return b.toString();
    }
}
