package net.objectlab.kit.pf.ucits;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.objectlab.kit.pf.BasicLine;
import net.objectlab.kit.pf.BasicPortfolio;
import net.objectlab.kit.pf.ExistingPortfolioLine;
import net.objectlab.kit.pf.RuleIssue;
import net.objectlab.kit.pf.ValidatedPortfolioLine;
import net.objectlab.kit.pf.ValidationResults;
import net.objectlab.kit.pf.cuke.CukeUtils;
import net.objectlab.kit.pf.ucits.BasicUcitsConcentrationValidator.Builder;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BasicUcitsSteps {
    private BasicPortfolio portfolio;

    @Given("^system is clean$")
    public void system_database_is_clean() throws Throwable {
        CukeUtils.VALUEHOLDER.clear();
    }

    @Then("^the UCITS validation lines for \"(.*?)\" look like$")
    public void ucitsValidationLines(final String resultKey, final DataTable table) {
        final ValidationResults results = (ValidationResults) CukeUtils.VALUEHOLDER.get(resultKey);

        final List<? extends ValidatedPortfolioLine> lines = results.getLines();
        CukeUtils.compareResults(ValidatedPortfolioLineForTest.class,
                lines.stream().map(t -> new ValidatedPortfolioLineForTest(t)).collect(Collectors.toList()), table);
    }

    @Then("^the UCITS validation issues for \"(.*?)\" look like$")
    public void ucitsValidationIssues(final String resultKey, final DataTable table) {
        final ValidationResults results = (ValidationResults) CukeUtils.VALUEHOLDER.get(resultKey);

        final List<? extends RuleIssue> issues = results.getIssues();
        CukeUtils.compareResults(RuleIssueForTest.class, issues.stream().map(t -> new RuleIssueForTest(t)).collect(Collectors.toList()), table);
    }

    @Given("^an existing portfolio for affiliate \"(.*?)\" and partyCode \"(.*?)\" and currency \"(.*?)\" like$")
    public void existingPortfolioImport(final String affiliateCode, final String partyCode, final String ccy, final DataTable dataTable)
            throws Throwable {
        portfolio = new BasicPortfolio();
        portfolio.setAffiliateCode(affiliateCode);
        portfolio.setPartyCode(partyCode);
        portfolio.setPortfolioCcy(ccy);

        final List<BasicLine> details = dataTable.asList(BasicLine.class);
        final List<ExistingPortfolioLine> lines = new ArrayList<>();
        lines.addAll(details);
        portfolio.setLines(lines);
        portfolio.setPortfolioValue(lines.stream().map(t -> t.getValueInPortfolioCcy()).reduce(BigDecimal.ZERO, (a, b) -> b != null ? a.add(b) : a));
    }

    @When("^I run basic UCITS validation in valueholder \"(.*?)\"$")
    public void calculateValidation(final String key) throws Throwable {
        final Builder builder = new BasicUcitsConcentrationValidator.Builder()
        .assetDetailsProvider(BasicReferenceDataSteps.getAssetDetailsProvider()).assetEligibilityProvider(
                BasicReferenceDataSteps.getAssetEligibilityProvider());
        final BasicUcitsConcentrationValidator validator = new BasicUcitsConcentrationValidator(builder);
        final ValidationResults results = validator.validate(portfolio);
        CukeUtils.VALUEHOLDER.put(key, results);
    }

}
