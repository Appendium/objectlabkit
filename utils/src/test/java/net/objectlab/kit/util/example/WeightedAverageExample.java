package net.objectlab.kit.util.example;

import java.math.BigDecimal;

import net.objectlab.kit.util.WeightedAverage;

public class WeightedAverageExample {
    public static void main(final String[] args) {
        // Credit card weight
        // Credit Card 1: Rate 20% Balance $6,413
        // Credit Card 2: Rate 15% Balance $3,500
        // Credit Card 3: Rate 5% Balance $11,400
        final WeightedAverage avg = new WeightedAverage(false); // exclude zeros
        avg.add(new BigDecimal(0.20), new BigDecimal(6413));
        avg.add(new BigDecimal(0.15), new BigDecimal(3500));
        avg.add(new BigDecimal(0.05), new BigDecimal(11400));

        System.out.println("Number of positions (3): " + avg.getCount());
        System.out.println("Total Balance $21,313: " + avg.getTotal());
        System.out.println("Weighted Average Rate (~11.15%): " + avg.getWeightedAverage());
    }
}
