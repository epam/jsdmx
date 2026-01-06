package com.epam.jsdmx.infomodel.sdmx30;

/**
 * A single localized value of the set of values for the Member Selection.
 */
public interface LocalisedMemberValue extends SelectionValue {
    /**
     * Value of the member.
     */
    String getValue();

    /**
     * Locale of the member
     */
    String getLocale();
}
