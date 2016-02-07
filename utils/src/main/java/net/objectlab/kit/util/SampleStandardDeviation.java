package net.objectlab.kit.util;

public class SampleStandardDeviation extends PopulationStandardDeviation {
    @Override
    protected long getDataPointsForCalc() {
        return super.getDataPointsForCalc() - 1;
    }
}
