package net.objectlab.kit.util.predicate;

import java.util.function.Predicate;

/**
 * Class to represent a Predicate that can be displayed for human consumption.
 *
 * Often, if you model some predicates for a data set, it would be great to print a predicate so that a user
 * could understand it.  You then get just a java useful string like "x.y.x.Predicate@0x1231abc".
 *
 * Wouldn't it be better to get:
 * <pre>
 * AssetClass in (Equity, Bond) and State=Active
 * </pre>
 * 
 * This achieved easily, assume Asset has assetClass and State, you can write 2 flexible predicates:
 * <pre>
 * public static Predicate&lt;Asset&gt; hasAssetClass(String... assetClass) {
 *  return new PrintablePredicat&lt;&gt;("AssetClass",  t -&gt; StringUtil.equalsAnyIgnoreCase(t.getAssetClass(), assetClass), assetClass);
 * }
 * public static Predicate&lt;Asset&gt; hasState(String... state) {
 *  return new PrintablePredicate("State",  t -&gt; StringUtil.equalsAnyIgnoreCase(t.getState(), state), state);
 * }
 * // now Combine them:
 * Predicate&lt;Asset&gt; predicate = hasAssetClass("Equity","Bond").and(hasState("Active"));
 * System.out.println(predicate); // AssetClass in (Equity, Bond) and State=Active
 * </pre>
 *
 * @author Benoit Xhenseval
 *
 * @param <T> Can be used for any class.
 */
public class PrintablePredicate<T> implements Predicate<T> {
    private PrintablePredicateOperand operand;
    private String name;
    private Predicate<T> predicate;
    private Predicate<? super T> left;
    private Predicate<? super T> right;
    private Object[] values;

    public PrintablePredicate(final String name, final Predicate<T> predicate, final Object... values) {
        this.name = name;
        this.predicate = predicate;
        this.values = values;
    }

    public PrintablePredicate(final PrintablePredicateOperand operand, final Predicate<T> predicate, final Object... values) {
        this.operand = operand;
        this.predicate = predicate;
        this.values = values;
    }

    public PrintablePredicate(final PrintablePredicateOperand operand, final Predicate<? super T> left, final Predicate<? super T> right) {
        if (operand == PrintablePredicateOperand.NOT) {
            throw new IllegalArgumentException("You cannot use NOT with 2 predicates");
        }
        this.operand = operand;
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean test(final T t) {
        if (operand == PrintablePredicateOperand.OR) {
            return left.test(t) || right.test(t);
        } else if (operand == PrintablePredicateOperand.AND) {
            return left.test(t) && right.test(t);
        } else if (operand == PrintablePredicateOperand.NOT) {
            return !predicate.test(t);
        }
        return predicate.test(t);
    }

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder();

        if (operand == PrintablePredicateOperand.OR) {
            b.append("(").append(left.toString());
            // b.append(System.lineSeparator());
            b.append(" OR ").append(right.toString()).append(")");
        } else if (operand == PrintablePredicateOperand.AND) {
            b.append(left.toString()).append(" AND ").append(right.toString());
        } else {
            printPredicate(b);
            printValues(b);
        }

        return b.toString();
    }

    private void printPredicate(final StringBuilder b) {
        if (operand == PrintablePredicateOperand.NOT) {
            b.append("NOT ");
        } else {
            b.append(name);
        }
        if (predicate instanceof PrintablePredicate) {
            if (operand == PrintablePredicateOperand.NOT) {
                b.append("(");
            }
            b.append(predicate.toString());
            if (operand == PrintablePredicateOperand.NOT) {
                b.append(")");
            }
        }
    }

    private void printValues(final StringBuilder b) {
        if (values != null && values.length > 0) {
            if (values.length == 1) {
                b.append("=");
            } else {
                b.append(" in (");
            }
            for (int i = 0; i < values.length; i++) {
                if (i > 0) {
                    b.append(", ");
                }
                b.append(values[i]);
            }
            if (values.length > 1) {
                b.append(")");
            }
        }
    }

    @Override
    public PrintablePredicate<T> or(final Predicate<? super T> other) {
        return new PrintablePredicate<>(PrintablePredicateOperand.OR, this, other);
    }

    @Override
    public PrintablePredicate<T> and(final Predicate<? super T> other) {
        return new PrintablePredicate<>(PrintablePredicateOperand.AND, this, other);
    }

    @Override
    public PrintablePredicate<T> negate() {
        return new PrintablePredicate<>(PrintablePredicateOperand.NOT, this);
    }

}
