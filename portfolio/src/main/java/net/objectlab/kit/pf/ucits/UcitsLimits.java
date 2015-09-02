package net.objectlab.kit.pf.ucits;

import java.math.BigDecimal;

public class UcitsLimits {
    private final BigDecimal maxConcentrationPerIssuer;
    private final BigDecimal mediumConcentrationPerIssuer;
    private final BigDecimal maxForMediumConcentration;
    private final boolean fund;
    private final boolean ucitsFund;

    public UcitsLimits(final BigDecimal maxConcentrationPerIssuer, final BigDecimal mediumConcentrationPerIssuer,
            final BigDecimal maxForMediumConcentration, final boolean fund, final boolean ucitsFund) {
        super();
        this.maxConcentrationPerIssuer = maxConcentrationPerIssuer;
        this.mediumConcentrationPerIssuer = mediumConcentrationPerIssuer;
        this.maxForMediumConcentration = maxForMediumConcentration;
        this.fund = fund;
        this.ucitsFund = ucitsFund;
    }

    public boolean isFund() {
        return fund;
    }

    public boolean isUcitsFund() {
        return ucitsFund;
    }

    public BigDecimal getMaxConcentrationPerIssuer() {
        return maxConcentrationPerIssuer;
    }

    public BigDecimal getMediumConcentrationPerIssuer() {
        return mediumConcentrationPerIssuer;
    }

    public BigDecimal getMaxForMediumConcentration() {
        return maxForMediumConcentration;
    }

}
