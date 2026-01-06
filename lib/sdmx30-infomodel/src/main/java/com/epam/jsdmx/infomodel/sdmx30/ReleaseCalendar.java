package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Describes information about the timing of releases of the constrained data. All of these values use the standard "P7D" - style format.
 */
public interface ReleaseCalendar {
    /**
     * Interval between January first and the first release of data within the year.
     */
    String getOffset();

    /**
     * Period between releases of the data set.
     */
    String getPeriodicity();

    /**
     * Period after which the release of data may be deemed late.
     */
    String getTolerance();
}
