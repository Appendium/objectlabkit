package net.objectlab.kit.pf.ucits;


public interface UcitsLimitProvider {
    /**
     * 5% in most cases but it could be larger
     * @param assetCode
     * @return
     */
    UcitsLimits getMediumLimit(String assetCode);
}
