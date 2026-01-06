package com.epam.jsdmx.infomodel.sdmx30;

import java.time.Instant;

/**
 * Provides the ability to map source to target date formats. The EpochMap describes
 * the source date as the number of epochs since a point in time, where the duration of each
 * epoch is defined, e.g., number of milliseconds since 1970.
 */
public interface EpochMap extends DateMap {

    /**
     * @return The Instant - Epoch zero starts on this period.
     */
    Instant getBasePeriod();

    /**
     * @return The period of time that each epoch represents
     */
    EpochPeriodType getEpochPeriod();

    /**
     * @return The frequency to convert the input date into.
     */
    String getTargetFrequencyId();
}
