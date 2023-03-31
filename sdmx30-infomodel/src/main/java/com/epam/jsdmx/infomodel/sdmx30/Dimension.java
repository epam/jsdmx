package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * A metadata concept used (most probably together with other metadata concepts) to
 * classify a statistical series, e.g., a statistical concept indicating a certain
 * economic activity or a geographical reference area.
 */
public interface Dimension extends DimensionComponent {

    /**
     * @return Collection of associations to the Concept that specifies the role that the
     * Dimension plays in the Data Structure Definition
     */
    List<ArtefactReference> getConceptRoles();

    @Override
    default boolean isTimeDimension() {
        return false;
    }
}
