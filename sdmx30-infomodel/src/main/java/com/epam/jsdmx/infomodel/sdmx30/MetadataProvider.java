package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * An organisation that produces reference metadata.
 */
public interface MetadataProvider extends Organisation<MetadataProvider>, ConstrainableArtefact {

    /**
     * According to the IM model, MetadataProvider does not have a hierarchy.
     *
     * @return empty list
     */
    @Override
    default List<? extends MetadataProvider> getHierarchy() {
        return List.of();
    }

    /**
     * Reference to a datasource containing the metadata
     */
    String getDataSource();
}
