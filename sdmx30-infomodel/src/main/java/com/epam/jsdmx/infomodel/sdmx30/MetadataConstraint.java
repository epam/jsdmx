package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Defines a Constraint in terms of the content that can be found in metadata sources linked
 * to the Constrainable Artefact to which this constraint is associated.
 */
public interface MetadataConstraint extends Constraint {

    /**
     * Defines dates on which the constrained metadata is to be made available.
     */
    ReleaseCalendar getReleaseCalendar();

    /**
     * A set of Components and their values that defines a subset or "slice" of the total range of possible content of a metadata structure
     * to which the Constrainable Artefact is linked.
     */
    List<MetadataTargetRegion> getMetadataTargetRegions();
}
