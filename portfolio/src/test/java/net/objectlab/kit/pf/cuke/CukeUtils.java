package net.objectlab.kit.pf.cuke;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.objectlab.kit.util.StringUtil;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.junit.ComparisonFailure;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NullValueInNestedPathException;

import cucumber.api.DataTable;

public class CukeUtils {
    public static final Map<String, Object> VALUEHOLDER = new HashMap<>();

    public static <T> T copyFieldValues(final List<String> fieldsToCopy, final T source, final Class<T> typeOfT) {
        try {
            final BeanWrapper src = new BeanWrapperImpl(source);
            final BeanWrapper target = new BeanWrapperImpl(typeOfT.newInstance());
            fieldsToCopy.forEach(t -> {
                try {
                    Object propertyValue = src.getPropertyValue(t);
                    if (propertyValue instanceof String) {
                        propertyValue = ((String) propertyValue).trim();
                    }
                    target.setPropertyValue(t, propertyValue);
                } catch (final NullValueInNestedPathException ignore) {
                }
            });
            return (T) target.getWrappedInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    public static <T> void compareResults(final Class<T> classType, final List<T> actual, final DataTable expected) {
        final List<String> fieldsToCompare = expected.topCells();

        final T[] expectedEntities = convertDataTableToExpected(classType, expected, fieldsToCompare);
        final List<T> actualEntities = actual.stream().map(sea -> copyFieldValues(fieldsToCompare, sea, classType)).collect(Collectors.toList());

        try {
            assertThat(actualEntities).usingElementComparator(comparator(buildExclusionFields(classType, fieldsToCompare))).containsOnly(
                    expectedEntities);
        } catch (final java.lang.AssertionError e) {
            final String actualDataAsStr = convertToString(actual, fieldsToCompare);
            throw new ComparisonFailure("Table comparison for " + classType.getSimpleName() + " does not match\n", expected.toString(),
                    actualDataAsStr);
        }
    }

    private static <T> String convertToString(final List<T> actualRowValues, final List<String> propertiesToCompare) {
        final List<List<Object>> rawRows = new ArrayList<>();
        rawRows.add(propertiesToCompare.stream().collect(Collectors.toList()));

        for (final T actualRow : actualRowValues) {
            final BeanWrapper src = new BeanWrapperImpl(actualRow);
            rawRows.add(propertiesToCompare.stream().map(p -> {
                final Object propertyValue = src.getPropertyValue(p);
                if (propertyValue == null) {
                    return "";
                } else if (src.getPropertyTypeDescriptor(p).getObjectType().isAssignableFrom(BigDecimal.class)) {
                    return ((BigDecimal) propertyValue).stripTrailingZeros().toPlainString();
                }
                return propertyValue;
            }).collect(Collectors.toList()));
        }
        return DataTable.create(rawRows).toString();
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] convertDataTableToExpected(final Class<T> typeOfT, final DataTable expectedValues, final List<String> propertiesToCompare) {
        return (T[]) convertDataTable(expectedValues, typeOfT, propertiesToCompare).toArray();
    }

    public static <T> List<T> convertDataTable(final DataTable table, final Class<T> typeOfT, final List<String> propertiesToCopy) {
        return table.asList(typeOfT).stream().map(t -> copyFields(t, typeOfT, propertiesToCopy)).collect(Collectors.toList());
    }

    @SuppressWarnings({ "unchecked" })
    public static <T> T copyFields(final T source, final Class<T> typeOfT, final List<String> propertiesToCopy) {
        try {
            final BeanWrapper src = new BeanWrapperImpl(source);
            final BeanWrapper target = new BeanWrapperImpl(typeOfT.newInstance());
            Arrays.stream(src.getPropertyDescriptors()).forEach(
                    property -> {
                        final String propertyName = property.getName();
                        if (propertiesToCopy.contains(propertyName)) {
                            final Object propertyValue = String.class.isAssignableFrom(property.getPropertyType()) ? StringUtil
                                    .nullIfEmpty((String) src.getPropertyValue(propertyName)) : src.getPropertyValue(propertyName);
                            target.setPropertyValue(propertyName, propertyValue);
                        }
                    });
            return (T) target.getWrappedInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    public static <T> Comparator<T> comparator(final List<String> excludes) {
        return (o1, o2) -> CompareToBuilder.reflectionCompare(o1, o2, excludes);
    }

    public static List<String> buildExclusionFields(final Class<?> clazz, final List<String> includes) {
        final List<String> excludes = new ArrayList<>();
        Class<?> claz = clazz;
        do {
            excludes.addAll(Arrays.stream(claz.getDeclaredFields()).map(f -> f.getName()).filter(name -> !includes.contains(name))
                    .collect(Collectors.toList()));
            claz = claz.getSuperclass();
        } while (claz != null);

        return excludes;
    }

}
