package net.objectlab.kit.pf.ucits;

import java.util.HashMap;
import java.util.Map;

import net.objectlab.kit.pf.AssetDetails;
import net.objectlab.kit.pf.AssetDetailsProvider;

public class MapAssetDetailsProvider implements AssetDetailsProvider {
    private Map<String, AssetDetails> map = new HashMap<>();

    @Override
    public AssetDetails getDetails(final String assetCode) {
        return map.get(assetCode);
    }

    public void setMap(final Map<String, AssetDetails> map) {
        this.map = map;
    }

}
