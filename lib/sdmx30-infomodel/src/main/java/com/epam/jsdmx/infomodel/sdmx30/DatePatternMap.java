package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Described a source date based on a string pattern, and how it
 * maps to the target date.
 */
public interface DatePatternMap extends DateMap {

    /**
     * @return The locale on which the input will be parsed according to the pattern.
     */
    String getLocale();

    /**
     * @return The source date using conventions for describing years, months, days, etc.
     */
    String getSourcePattern();
}
