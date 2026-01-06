package com.epam.jsdmx.infomodel.sdmx30;

/**
 * When the source frequency is lower than the target frequency additional information
 * can be provided for resolve to start of period, end of period, or mid-period
 */
public enum ResolvePeriod {
    START_OF_PERIOD,
    END_OF_PERIOD,
    MID_PERIOD
}
