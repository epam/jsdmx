package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * A parent class for {@link DatePatternMap} and {@link EpochMap}. Provide the ability to map source to target date formats.
 */
public interface DateMap extends IdentifiableArtefact {
    /**
     * @return The Dimension or Attribute of the target Data Structure Definition
     * which will hold the frequency information for date conversion. Mutually exclusive
     * with targetFrequencyId.
     */
    String getFrequencyDimension();

    /**
     * @return The frequency to convert the input date into.
     * Mutually exclusive with freqDimension.
     */
    String getTargetFrequencyId();

    /**
     * @return The date of the start of the year, enabling mapping from high
     * frequency to lower frequency formats.
     */
    YearStart getYearStart();

    /**
     * @return Which point in time to resolve to when mapping from low
     * frequency to high frequency periods.
     */
    ResolvePeriod getResolvePeriod();

    /**
     * @return Collection of references to a map of frequency id to date pattern for output.
     */
    List<FrequencyFormatMapping> getMappedFrequencies();
}
