package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * A set of metadata concepts that define a partial key derived from the Dimension Descriptor
 * in a Data Structure Definition
 */
public interface GroupDimensionDescriptor extends IdentifiableArtefact {
    /**
     * The list of dimension references that define the group dimension descriptor
     */
    List<String> getDimensions();
}
