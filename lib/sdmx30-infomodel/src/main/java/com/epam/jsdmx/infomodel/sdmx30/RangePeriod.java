package com.epam.jsdmx.infomodel.sdmx30;

/**
 * The start and end periods in a date range.
 */
public interface RangePeriod extends TimeRangeValue {

    /**
     * Association to the Start Period.
     */
    TimeRangePeriod getStartPeriod();

    /**
     * Association to the End Period.
     */
    TimeRangePeriod getEndPeriod();
}
