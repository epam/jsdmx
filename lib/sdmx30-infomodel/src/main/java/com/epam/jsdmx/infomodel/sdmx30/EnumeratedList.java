package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Base abstraction for ValueList
 *
 * @param <T> stands for ValueItems
 */
public interface EnumeratedList<T extends EnumeratedItem> extends MaintainableArtefact {

    /**
     * @return The Valuelist can have one or more {@link ValueItem ValueItems}
     */
    List<T> getItems();
}
