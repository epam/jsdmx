package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * The descriptive information for an arrangement or division of objects
 * into groups based on characteristics, which the objects have in common.
 */
public interface ItemScheme<T extends Item> extends MaintainableArtefact {

    /**
     * The list of items contained in the item scheme.
     */
    List<? extends T> getItems();

    /*
     * Denotes whether the Item Scheme contains a subset of the full set of Items in the maintained scheme.
     */
    boolean isPartial();
}
