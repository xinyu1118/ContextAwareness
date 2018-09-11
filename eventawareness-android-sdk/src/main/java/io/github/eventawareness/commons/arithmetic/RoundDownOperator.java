package io.github.eventawareness.commons.arithmetic;

import io.github.eventawareness.utils.Assertions;

/**
 * Round the number specified by a field.
 */
class RoundDownOperator extends ArithmeticOperator<Double> {

    private final Number valueToRound;

    RoundDownOperator(String numField, Number valueToRound) {
        super(numField);
        this.valueToRound = Assertions.notNull("valueToRound", valueToRound);
        this.addParameters(valueToRound);
    }

    @Override
    protected Double processNum(Number number) {
        return Math.floor(number.doubleValue()/valueToRound.doubleValue()) * valueToRound.doubleValue();
    }

}
