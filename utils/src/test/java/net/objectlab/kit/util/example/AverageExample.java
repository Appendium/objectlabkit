package net.objectlab.kit.util.example;

import java.math.BigDecimal;

import net.objectlab.kit.util.Average;

public class AverageExample {
    public static void main(final String[] args) {
        final Average avg = new Average(3); // scale for the result, otherwise it uses the scale of the first datapoint!
        avg.add(new BigDecimal(1));
        avg.add(new BigDecimal(2));

        System.out.println("Number of data Points (2): " + avg.getDataPoints());
        System.out.println("Average upto 3 dec. pt (1.500): " + avg.getAverage());
    }
}
