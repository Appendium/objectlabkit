package net.objectlab.kit.pf.ucits;

import java.util.HashMap;
import java.util.Map;

import net.objectlab.kit.pf.AssetDetails;
import net.objectlab.kit.pf.AssetDetailsProvider;
import net.objectlab.kit.pf.AssetEligibilityProvider;
import net.objectlab.kit.pf.BasicAsset;
import cucumber.api.java.en.Given;

public class BasicReferenceDataSteps {
    private static final MapAssetDetailsProvider PROV = new MapAssetDetailsProvider();
    private static final MapAssetEligibilityProvider ELIGIBILITY = new MapAssetEligibilityProvider();

    public static AssetDetailsProvider getAssetDetailsProvider() {
        return PROV;
    }

    public static AssetEligibilityProvider getAssetEligibilityProvider() {
        return ELIGIBILITY;
    }

    @Given("^basic reference data")
    public void usualRefData() throws Throwable {
        final Map<String, AssetDetails> map = new HashMap<>();
        for (int i = 1; i <= 25; i++) {
            final String assetCode = "code-" + i;
            map.put(assetCode, new BasicAsset(assetCode, "asset-" + 1, "issuer-" + i));
        }
        for (int i = 1; i <= 10; i++) {
            final String assetCode = "code-a" + i;
            map.put(assetCode, new BasicAsset(assetCode, "asset-a" + 1, "issuer-a"));
        }
        PROV.setMap(map);
    }
}
