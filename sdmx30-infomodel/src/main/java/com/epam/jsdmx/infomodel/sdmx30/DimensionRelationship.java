package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * The Data Attribute is related to a set of Dimensions
 */
public interface DimensionRelationship extends AttributeRelationship {

    /**
     * @return Collection of associations to the set of {@link Dimension Dimensions} to which the Data Attribute is related.
     */
    List<String> getDimensions();

    /**
     * @return Collection of association to the {@link GroupDimensionDescriptor} which specifies
     * the set of {@link Dimension Dimensions} to which the Data Attribute is attached.
     */
    List<String> getGroupKeys();
}
