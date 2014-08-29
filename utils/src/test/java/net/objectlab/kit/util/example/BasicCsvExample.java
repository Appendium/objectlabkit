package net.objectlab.kit.util.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.objectlab.kit.util.Util;

/**
 * This is a VERY basic parsing/creation of CSV, if you seen anything superior then use FlatPack @see http://flatpack.sf.net
 */
public class BasicCsvExample {
    public static void main(final String[] args) {
        List<Object> list = new ArrayList<>();
        list.add("Hello");
        list.add(10);
        list.add(new BigDecimal("1254"));

        final String csvString = Util.listToCSVString(list);
        System.out.println(csvString);

        final List<String> list2 = Util.listify(csvString, ",");
        System.out.println(list2);
    }
    /* OUTPUT:
    Hello,10,1254
    [Hello, 10, 1254]
     */
}
