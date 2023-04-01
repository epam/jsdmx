package com.epam.jsdmx.infomodel.sdmx30;

/**
 * A property that contains the possible values for a {@link MemberValue} to indicate if the child
 * Codes of the selected Code shall be included in the selection. It is also possible to
 * include children and exclude the Code by using the 'excluderoot' value.
 */
public enum CascadeValue {
    FALSE,
    TRUE,
    EXCLUDE_ROOT
}
