package com.epam.jsdmx.serializer.sdmx30.common;

import static org.apache.commons.lang3.EnumUtils.getEnumIgnoreCase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * An optional parameter which allows the user to specify the value of
 * the DataSetAction generated in the validation report.
 * If this parameter is not specified, the default value will be used.
 */
@RequiredArgsConstructor
@Getter
public enum ActionType {
    /**
     * Information - Data is for information purposes. If such data messages
     * are loaded into an SDMX database, the action "A" (Append) is assumed.
     */
    INFORMATION("Information"),

    /**
     * Append - Data is for an incremental update of existing observations or
     * partial-key attributes or for the provision of new data formerly absent.
     * This means that only the information provided explicitly in the message should be altered.
     */
    APPEND("Append"),

    /**
     * Replace - Data is for replacement. Existing observations are to be fully replaced.
     * Existing attribute values are to be replaced.
     * Observations or attribute values formally absent will be appended.
     */
    REPLACE("Replace"),

    /**
     * Data is to be deleted. 'Delete' is assumed to be an incremental deletion.
     */
    DELETE("Delete");

    private final String value;

    public static ActionType valueOfIgnoreCase(String value) {
        return getEnumIgnoreCase(ActionType.class, value);
    }
}
