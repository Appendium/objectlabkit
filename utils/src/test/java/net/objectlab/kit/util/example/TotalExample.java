package net.objectlab.kit.util.example;

import java.math.BigDecimal;

import net.objectlab.kit.util.Total;

public class TotalExample {
    public static void main(final String[] args) {
        final Total total = new Total();
        total.add(new BigDecimal(1));
        total.add(new BigDecimal(21));
        total.add(new BigDecimal(30));

        System.out.println("Number of data Points (3): " + total.getCount());
        System.out.println("Total (52): " + total.getTotal());
    }
    /*
     * Output
    Number of data Points (3): 3
    Total (52): 52
     */
}
