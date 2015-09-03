package net.objectlab.kit.pf.ucits;

import java.util.ArrayList;
import java.util.List;

import net.objectlab.kit.pf.BasicLine;
import net.objectlab.kit.pf.BasicPortfolio;
import net.objectlab.kit.pf.ExistingPortfolioLine;
import net.objectlab.kit.pf.ValidatedPortfolioLine;
import net.objectlab.kit.pf.ValidationResults;
import net.objectlab.kit.pf.ucits.BasicUcitsConcentrationValidator.Builder;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class BasicUcitsSteps {
    private BasicPortfolio portfolio;

    @Then("^the UCITS validation lines look like$")
    public void ucitsValidationLines(final DataTable table) {
        final Builder builder = new BasicUcitsConcentrationValidator.Builder()
                .assetDetailsProvider(BasicReferenceDataSteps.getAssetDetailsProvider()).assetEligibilityProvider(
                        BasicReferenceDataSteps.getAssetEligibilityProvider());
        BasicUcitsConcentrationValidator validator = new BasicUcitsConcentrationValidator(builder);
        final ValidationResults results = validator.validate(portfolio);

        final List<? extends ValidatedPortfolioLine> lines = results.getLines();
        final List<ValidatedPortfolioLineForTest> asList = table.asList(ValidatedPortfolioLineForTest.class);
    }

    @Given("^an existing portfolio for affiliate \"(.*?)\" and partyCode \"(.*?)\" like$")
    public void existingPortfolioImport(String affiliateCode, String partyCode, final DataTable dataTable) throws Throwable {
        portfolio = new BasicPortfolio();
        portfolio.setAffiliateCode(affiliateCode);
        portfolio.setPartyCode(partyCode);

        final List<BasicLine> details = dataTable.asList(BasicLine.class);
        List<ExistingPortfolioLine> lines = new ArrayList<ExistingPortfolioLine>();
        lines.addAll(details);
        portfolio.setLines(lines);
    }

}
