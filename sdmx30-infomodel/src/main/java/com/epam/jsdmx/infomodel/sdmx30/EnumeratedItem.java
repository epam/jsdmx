package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Base abstraction for {@link ValueItem}
 */
public interface EnumeratedItem extends AnnotableArtefact {

    /**
     * @return The ID of a ValueItem
     */
    String getId();

    /**
     * @return Association to InternationalString, which represents the concept name
     */
    InternationalString getName();

    /**
     * @return Association to InternationalString, which represents the concept description
     */
    InternationalString getDescription();
}
