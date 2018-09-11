package io.github.eventawareness.commons.statistic;

import io.github.eventawareness.utils.StatisticUtils;

import java.util.List;

/**
 * Get the min value of a field in the stream.
 */
final class FieldMinStatistic extends FieldStatistic<Number> {

    FieldMinStatistic(String numField) {
        super(numField);
    }

    @Override
    protected Number processNumList(List<Number> numList) {
        return StatisticUtils.min(numList);
    }
}
