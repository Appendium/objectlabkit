package net.objectlab.kit.pf.ucits;

import java.util.HashMap;
import java.util.Map;

import net.objectlab.kit.pf.AssetEligibilityProvider;

public class MapAssetEligibilityProvider implements AssetEligibilityProvider {
    private Map<String, Boolean> map = new HashMap<>();

    @Override
    public boolean isEligible(String assetCode) {
        return map.getOrDefault(assetCode, true);
    }

}
