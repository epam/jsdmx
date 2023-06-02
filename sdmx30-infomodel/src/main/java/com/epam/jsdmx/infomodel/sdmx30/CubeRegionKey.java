package com.epam.jsdmx.infomodel.sdmx30;

import java.time.Instant;
import java.util.List;

/**
 * CubeRegionKeyType is a type for providing a set of values
 * for a dimension for the purpose of defining a data cube region
 */
public interface CubeRegionKey extends Copyable {

    /**
     * Indicates whether the Cube region key is included in the constraint definition or excluded from the constraint definition.
     */
    boolean isIncluded();

    /**
     * Indicates whether the Codes should keep or not the prefix, as defined in the extension of Codelist.
     */
    boolean isRemovePrefix();

    /**
     * Date from which the CubeRegionKey is valid.
     */
    Instant getValidFrom();

    /**
     * Date from which the CubeRegionKey is superseded.
     */
    Instant getValidTo();

    /**
     * A collection of values for a component
     */
    List<SelectionValue> getSelectionValues();

    /**
     * Provides the identifier for the component for which values are being provided
     */
    String getComponentId();
}
