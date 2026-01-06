package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Defines a Constraint in terms of the content that can be found in data sources linked to the Constrainable Artefact to which this constraint is associated.
 */
public interface DataConstraint extends Constraint {
    /**
     * Describes a set of dimension values which define a region and attributes
     * which relate to the region for the purpose of describing a constraint
     */
    List<CubeRegion> getCubeRegions();

    /**
     * Defines a collection of full or partial data keys
     */
    List<DataKeySet> getDataContentKeys();

    /**
     * Defines dates on which the constrained data is to be made available.
     */
    ReleaseCalendar getReleaseCalendar();
}
