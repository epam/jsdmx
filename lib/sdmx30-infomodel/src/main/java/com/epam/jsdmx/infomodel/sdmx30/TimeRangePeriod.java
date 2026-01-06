package com.epam.jsdmx.infomodel.sdmx30;

/**
 * The period before or after which the constrained selection is valid.
 */
public interface TimeRangePeriod extends TimeRangeValue {

    /**
     * Indication of whether the date is inclusive in the period.
     */
    boolean isInclusive();

    /**
     * The time period which acts as the latest or earliest possible reported period.
     */
    String getPeriod();
}
