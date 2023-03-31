package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Defines a collection of full or partial data keys (dimension values).
 */
public interface DataKeySet extends Copyable {
    /**
     * Indicates whether the Data Key Set is included in the constraint definition or excluded from the constraint definition.
     */
    boolean isIncluded();

    /**
     * Contains a set of dimension values which identify a full set of data.
     */
    List<DataKey> getKeys();

    @Override
    DataKeySet clone();
}
