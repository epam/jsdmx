package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Set of Dimensions specified by a GroupKey. The Data Attribute is related to a Group Dimension Descriptor construct.
 */
public interface GroupRelationship extends AttributeRelationship {

    /**
     * @return An association to the Group Dimension Descriptor
     */
    String getGroupKey();
}
