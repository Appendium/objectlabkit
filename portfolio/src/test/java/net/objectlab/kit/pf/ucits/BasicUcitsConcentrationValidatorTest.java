package net.objectlab.kit.pf.ucits;

import static net.objectlab.kit.util.BigDecimalUtil.bd;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import net.objectlab.kit.pf.AssetDetailsProvider;
import net.objectlab.kit.pf.AssetEligibilityProvider;
import net.objectlab.kit.pf.BasicAsset;
import net.objectlab.kit.pf.BasicLine;
import net.objectlab.kit.pf.BasicPortfolio;
import net.objectlab.kit.pf.ExistingPortfolioLine;
import net.objectlab.kit.pf.ValidationResults;
import net.objectlab.kit.pf.ucits.BasicUcitsConcentrationValidator.Builder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BasicUcitsConcentrationValidatorTest {
    private BasicUcitsConcentrationValidator validator;
    @Mock
    private AssetDetailsProvider assetDetailsProvider;
    @Mock
    private AssetEligibilityProvider assetEligibilityProvider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Builder builder = new BasicUcitsConcentrationValidator.Builder().assetDetailsProvider(assetDetailsProvider).assetEligibilityProvider(
                assetEligibilityProvider);
        validator = new BasicUcitsConcentrationValidator(builder);
        given(assetEligibilityProvider.isEligible(Matchers.anyString())).willReturn(true);
        given(assetDetailsProvider.getDetails(Matchers.eq("IBM"))).willReturn(new BasicAsset("IBM", "IBM", "VGRQXHF3J8VDLUA7XE92"));
    }

    @Test
    public void testValidateEmpty() {
        final BasicPortfolio bp = new BasicPortfolio();
        final ValidationResults res = validator.validate(bp);
        assertThat(res).isNotNull();
        assertThat(res.getLines()).isEmpty();
    }

    @Test
    public void testValidate1Line() {
        final BasicPortfolio bp = new BasicPortfolio();
        bp.setPortfolioCcy("USD");
        bp.setPortfolioValue(bd("8800"));
        final List<ExistingPortfolioLine> lines = new ArrayList<>();
        lines.add(new BasicLine("IBM", "International Business Machine", bd("88"), bd("100"), bd("8800")));
        bp.setLines(lines);

        final ValidationResults res = validator.validate(bp);
        assertThat(res).isNotNull();
        assertThat(res.getLines()).hasSize(1);
        System.out.println(res);
    }
}
