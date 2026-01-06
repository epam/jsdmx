package com.epam.jsdmx.infomodel.sdmx30;

/**
 * The value of the Time Dimension component.
 */
public interface TimeDimensionValue extends Copyable {
    /**
     * Indicates whether the specified value represents and exact time or time period,
     * or whether the value should be handled as a range.
     */
    String getOperator();

    /**
     * The value of the time period.
     */
    String getTimeValue();

    @Override
    TimeDimensionValue clone();
}
