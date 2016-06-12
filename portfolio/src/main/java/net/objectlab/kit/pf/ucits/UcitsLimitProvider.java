package net.objectlab.kit.pf.ucits;

@FunctionalInterface
public interface UcitsLimitProvider {
    /**
     * 5% in most cases but it could be larger
     * @param assetCode asset we are interested in
     * @return the limit
     */
    UcitsLimits getMediumLimit(String assetCode);
}
