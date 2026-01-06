package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Defines a subset or "slice" of the total range of possible content of a data structure to
 * which the Constrainable Artefact is linked.
 */
public interface CubeRegion extends AnnotableArtefact, Copyable {

    /**
     * Indicates whether the Cube Region is included in the constraint definition or excluded from the constraint definition.
     */
    boolean isIncluded();

    /**
     * A set of permissible values for one component of the axis
     */
    List<MemberSelection> getMemberSelections();

    /**
     * Contains a reference to a component which disambiguates the data (i.e. a dimension)
     * and provides a collection of values for the component.
     */
    List<CubeRegionKey> getCubeRegionKeys();

    @Override
    CubeRegion clone();
}
