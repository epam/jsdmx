package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Defines a subset or "slice" of the total range of possible content of a data structure to
 * which the Constrainable Artefact is linked.
 */
public interface CubeRegion extends Copyable {

    /**
     * Indicates whether the Cube Region is included in the constraint definition or excluded from the constraint definition.
     */
    boolean isIncluded();

    /**
     * A set of permissible values for one component of the axis
     */
    List<MemberSelection> getMemberSelections();

    @Override
    CubeRegion clone();
}
