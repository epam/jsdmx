package com.epam.jsdmx.infomodel.sdmx30;


import java.util.List;

/**
 * An organisation that produces data.
 */
public interface DataProvider extends Organisation<DataProvider> {
    /**
     * Reference to a datasource containing the data
     */
    String getDataSource();

    /**
     * According to the IM model, DataProvider does not have a hierarchy.
     *
     * @return empty list
     */
    @Override
    default List<? extends DataProvider> getHierarchy() {
        return List.of();
    }
}
