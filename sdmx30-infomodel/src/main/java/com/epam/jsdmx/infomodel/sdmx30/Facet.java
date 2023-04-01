package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Defines the format for the content of the Component when reported in a data or metadata set.
 */
public interface Facet extends Copyable {
    /**
     * A specific content type.
     */
    FacetType getType();

    /**
     * Value of the facet.
     */
    String getValue();

    /**
     * The format of the value of a {@link Component} when reported in a data or metadata set.
     * This is constrained by the {@link FacetValueType} enumeration
     */
    FacetValueType getValueType();

    /**
     * List of sentinel values for the facet - values that have a special meaning within the text format
     * representation of the Component. Applicable only when {@link Facet#getType()} is {@link FacetType#SENTINEL_VALUES}.
     *
     * @return sentinel values
     */
    List<SentinelValue> getSentinelValues();

    @Override
    Facet clone();
}
