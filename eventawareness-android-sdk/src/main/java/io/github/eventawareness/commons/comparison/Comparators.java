package io.github.eventawareness.commons.comparison;

import java.util.List;

import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.utils.annotations.PSOperatorWrapper;

/**
 * A helper class to access comparison functions
 */
@PSOperatorWrapper
public class Comparators {

    public static final String GTE = "gte";
    public static final String LTE = "lte";
    public static final String GT = "gt";
    public static final String LT = "lt";
    public static final String EQ = "eq";
    public static final String NEQ = "neq";

    public static final String IN = "in";
    public static final String OUT = "out";
    public static final String CROSSES = "crosses";
    public static final String UPDATED = "updated";


    /**
     * Check if the value of a field equals to a given value.
     *
     * @param field the name of the field to compare
     * @param valueToCompare the value to compare with
     * @param <TValue> the type of value
     * @return the function
     */
    public static <TValue> Function<Item, Boolean> eq(final String field, final TValue valueToCompare) {
        return new FieldEqualOperator<>(FieldEqualOperator.OPERATOR_EQ, field, valueToCompare);
    }

    /**
     * Check if the value of a field is in a given list.
     *
     * @param field the name of the field to compare
     * @param valueToCompare the value of a list to compare with
     * @param <TValue> the type of value
     * @return the function
     */
    public static <TValue> Function<Item, Boolean> eqList(final String field, final List<TValue> valueToCompare) {
        return new FieldInListOperator<>(FieldInListOperator.OPERATOR_EQ, field, valueToCompare);
    }

    /**
     * Check if the value of a field is not equal to a given value.
     *
     * @param field the name of the field to compare
     * @param valueToCompare the value to compare with
     * @param <TValue> the type of value
     * @return the function
     */
    public static <TValue> Function<Item, Boolean> ne(final String field, final TValue valueToCompare) {
        return new FieldEqualOperator<>(FieldEqualOperator.OPERATOR_NE, field, valueToCompare);
    }

    /**
     * Check if the value of a field is greater than a given value.
     *
     * @param field the name of the field to compare
     * @param valueToCompare the value to compare with
     * @return the function
     */
    public static Function<Item, Boolean> gt(final String field, final Number valueToCompare) {
        return new FieldCompareOperator(FieldCompareOperator.OPERATOR_GT, field, valueToCompare);
    }

    /**
     * Check if the value of a field is less than a given value.
     *
     * @param field the name of the field to compare
     * @param valueToCompare the value to compare with
     * @return the function
     */
    public static Function<Item, Boolean> lt(final String field, final Number valueToCompare) {
        return new FieldCompareOperator(FieldCompareOperator.OPERATOR_LT, field, valueToCompare);
    }

    /**
     * Check if the value of a field is greater than or equal to a given value.
     *
     * @param field the name of the field to compare
     * @param valueToCompare the value to compare with
     * @return the function
     */
    public static Function<Item, Boolean> gte(final String field, final Number valueToCompare) {
        return new FieldCompareOperator(FieldCompareOperator.OPERATOR_GTE, field, valueToCompare);
    }

    /**
     * Check if the value of a field is less than or equal to a given value.
     *
     * @param field the name of the field to compare
     * @param valueToCompare the value to compare with
     * @return the function
     */
    public static Function<Item, Boolean> lte(final String field, final Number valueToCompare) {
        return new FieldCompareOperator(FieldCompareOperator.OPERATOR_LTE, field, valueToCompare);
    }
}
