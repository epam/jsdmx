package com.epam.jsdmx.infomodel.sdmx30;

/**
 * A single value of the set of values for the Member Selection.
 */
public interface MemberValue extends SelectionValue {

    /**
     * @return A value of the member.
     */
    String getValue();

    /**
     * @return Indicates that the child nodes of the member are included in the
     * Member Selection (e.g., child codes)
     */
    CascadeValue getCascadeValue();
}
