package com.epam.jsdmx.infomodel.sdmx30;

/**
 * The FrequencyFormatMapping can be used to describe the relationship between the
 * frequency ID and the output date format, e.g., A01=YYYY.
 */
public interface FrequencyFormatMapping extends IdentifiableArtefact {

    /**
     * @return The output date pattern for that frequency
     */
    String getDatePattern();

    /**
     * @return The string used to describe the frequency
     */
    String getFrequencyCode();
}
