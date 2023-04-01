package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * The Item is an item of content in an Item Scheme.
 * This may be a node in a taxonomy or ontology, a code in a code list etc.
 */
public interface Item extends NameableArtefact, Copyable {
    /**
     * This allows an Item optionally to have one or more child Items.
     */
    List<? extends Item> getHierarchy();
}
