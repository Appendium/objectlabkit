package net.objectlab.kit.pf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BasicPortfolio implements ExistingPortfolio {
    private String id;
    private String affiliateCode;
    private String partyCode;
    private String portfolioCcy;
    private BigDecimal portfolioValue;
    private List<ExistingPortfolioLine> lines = new ArrayList<>();

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getAffiliateCode() {
        return affiliateCode;
    }

    public void setAffiliateCode(String affiliateCode) {
        this.affiliateCode = affiliateCode;
    }

    @Override
    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    @Override
    public String getPortfolioCcy() {
        return portfolioCcy;
    }

    public void setPortfolioCcy(String portfolioCcy) {
        this.portfolioCcy = portfolioCcy;
    }

    @Override
    public BigDecimal getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(BigDecimal portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    @Override
    public List<ExistingPortfolioLine> getLines() {
        return lines;
    }

    public void setLines(List<ExistingPortfolioLine> lines) {
        this.lines = lines;
    }

}
