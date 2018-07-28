package net.objectlab.kit.pf.ucits;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.objectlab.kit.pf.AssetDetailsProvider;
import net.objectlab.kit.pf.AssetEligibilityProvider;
import net.objectlab.kit.pf.ExistingPortfolio;
import net.objectlab.kit.pf.RuleNames;
import net.objectlab.kit.pf.Severity;
import net.objectlab.kit.pf.ValidatedPortfolioLine;
import net.objectlab.kit.pf.ValidationEngine;
import net.objectlab.kit.pf.ValidationResults;
import net.objectlab.kit.pf.validator.Results;
import net.objectlab.kit.util.BigDecimalUtil;
import net.objectlab.kit.util.Total;

/**
 * Default is the usual 5-10-40 rule.
 * http://www.alfi.lu/investor-centre/investor-protection/how-ucits-funds-protect-investors#Diversification
 */
public class BasicUcitsConcentrationValidator implements ValidationEngine {
    private final BigDecimal maxConcentrationPerIssuer;
    private final BigDecimal mediumConcentrationPerIssuer;
    private final BigDecimal maxForMediumConcentration;
    private final AssetDetailsProvider assetDetailsProvider;
    private final AssetEligibilityProvider assetEligibilityProvider;
    private final UcitsLimitProvider ucitsLimitProvider;

    public static class Builder {
        private BigDecimal maxConcentrationPerIssuer = new BigDecimal("0.1");
        private BigDecimal mediumConcentrationPerIssuer = new BigDecimal("0.05");
        private BigDecimal maxForMediumConcentration = new BigDecimal("0.4");
        private AssetDetailsProvider assetDetailsProvider;
        private AssetEligibilityProvider assetEligibilityProvider;
        private UcitsLimitProvider ucitsLimitProvider;

        public Builder maxConcentrationPerIssuer(final BigDecimal maxConcentrationPerIssuer) {
            this.maxConcentrationPerIssuer = maxConcentrationPerIssuer;
            return this;
        }

        public Builder mediumConcentrationPerIssuer(final BigDecimal mediumConcentrationPerIssuer) {
            this.mediumConcentrationPerIssuer = mediumConcentrationPerIssuer;
            return this;
        }

        public Builder maxForMediumConcentration(final BigDecimal maxForMediumConcentration) {
            this.maxForMediumConcentration = maxForMediumConcentration;
            return this;
        }

        public Builder assetDetailsProvider(final AssetDetailsProvider assetDetailsProvider) {
            this.assetDetailsProvider = assetDetailsProvider;
            return this;
        }

        public Builder assetEligibilityProvider(final AssetEligibilityProvider assetEligibilityProvider) {
            this.assetEligibilityProvider = assetEligibilityProvider;
            return this;
        }

        public Builder ucitsLimitProvider(final UcitsLimitProvider ucitsLimitProvider) {
            this.ucitsLimitProvider = ucitsLimitProvider;
            return this;
        }
    }

    public BasicUcitsConcentrationValidator(final Builder builder) {
        this.maxConcentrationPerIssuer = builder.maxConcentrationPerIssuer;
        this.maxForMediumConcentration = builder.maxForMediumConcentration;
        this.mediumConcentrationPerIssuer = builder.mediumConcentrationPerIssuer;
        this.assetDetailsProvider = builder.assetDetailsProvider;
        this.assetEligibilityProvider = builder.assetEligibilityProvider;
        this.ucitsLimitProvider = builder.ucitsLimitProvider;
    }

    private static final class TotalPerIssuer {
        private final String issuer;
        private final Total total = new Total();
        private final List<ValidatedPortfolioLine> lines = new ArrayList<>();

        public TotalPerIssuer(String issuer) {
            this.issuer = issuer;
        }

        public String getIssuer() {
            return issuer;
        }

        public void add(final ValidatedPortfolioLine l) {
            lines.add(l);
            total.add(l.getAllocationWeight());
        }

        public BigDecimal getTotalWeight() {
            return total.getTotal();
        }

        public List<ValidatedPortfolioLine> getLines() {
            return lines;
        }
    }

    @Override
    public ValidationResults validate(final ExistingPortfolio portfolio) {
        // for each line:
        final Results results = new Results(portfolio);

        // get the value of the portfolio
        final BigDecimal porfolioValue = portfolio.getPortfolioValue();

        // calculate the value per Issuer (using AssetDetails)
        // check if asset is eligible, if not -> Breach
        final Map<String, TotalPerIssuer> totalPerIssuer = new HashMap<>();
        results.getLines().forEach(l -> {
            if (!assetEligibilityProvider.isEligible(l.getAssetCode())) {
                l.addIssue(Severity.MANDATORY, RuleNames.ELIGIBILITY, "Asset not eligible.");
            }
            l.setAllocationWeight(BigDecimalUtil.divide(8, l.getValueInPortfolioCcy(), porfolioValue, BigDecimal.ROUND_HALF_UP));
            // calculate the weight for each issuer
            totalPerIssuer.computeIfAbsent(assetDetailsProvider.getDetails(l.getAssetCode()).getUltimateIssuerCode(), TotalPerIssuer::new).add(l);

        });

        final Total totalMediumConcentration = new Total();
        final List<ValidatedPortfolioLine> mediumLines = new ArrayList<>();
        totalPerIssuer.values().forEach(issuer -> {
            final BigDecimal totalWeight = issuer.getTotalWeight();
            if (BigDecimalUtil.compareTo(totalWeight, maxConcentrationPerIssuer) > 0) {
                // if weight > maxConcentrationPerIssuer (e.g. 10%) -> Breach
                issuer.lines.forEach(line -> line.addIssue(Severity.MANDATORY, RuleNames.ISSUER_MAX_CONCENTRATION,
                        "Concentration above " + BigDecimalUtil.movePoint(maxConcentrationPerIssuer, 2) + "% for " + issuer.getIssuer() + " ["
                                + BigDecimalUtil.movePoint(totalWeight, 2) + "]"));
            } else if (BigDecimalUtil.compareTo(totalWeight, mediumConcentrationPerIssuer) > 0) {
                // if weight > mediumConcentrationPerIssuer (e.g. 5%) -> sum them
                totalMediumConcentration.add(totalWeight);
                mediumLines.addAll(issuer.getLines());
            }
        });

        // if sum of those > maxForMediumConcentration -> Breach
        if (BigDecimalUtil.compareTo(totalMediumConcentration.getTotal(), maxForMediumConcentration) > 0) {
            mediumLines.forEach(line -> line.addIssue(Severity.MANDATORY, RuleNames.ISSUER_MEDIUM_CONCENTRATION,
                    "Total medium concentration is above " + BigDecimalUtil.movePoint(maxForMediumConcentration, 2) + "% ["
                            + BigDecimalUtil.movePoint(totalMediumConcentration.getTotal(), 2) + "]"));

        }
        return results;
    }
}
