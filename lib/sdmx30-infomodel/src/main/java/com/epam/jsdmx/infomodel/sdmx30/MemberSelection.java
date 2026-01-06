package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * A single value of the set of values for the Member Selection.
 */
public interface MemberSelection extends Copyable {

    /**
     * Indicates whether the Member Selection is included in the constraint definition or excluded from the constraint definition.
     */
    boolean isIncluded();

    /**
     * Indicates whether the Codes should keep or not the prefix, as defined in the extension of Codelist.
     */
    boolean isRemovePrefix();

    /**
     * A collection of values for the Member Selections that, combined with other Member Selections,
     * comprise the value content of the Cube Region.
     */
    List<SelectionValue> getSelectionValues();

    /**
     * Association to the Component in the Structure to which the Constrainable Artefact is linked,
     * which defines the valid Representation for the Member Values
     */
    String getComponentId();
}
