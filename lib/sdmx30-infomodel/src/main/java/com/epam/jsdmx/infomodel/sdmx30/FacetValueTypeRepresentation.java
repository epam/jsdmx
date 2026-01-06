package com.epam.jsdmx.infomodel.sdmx30;

/**
 * FacetValueTypeRepresentation is a type of representation which is defined by primitive type from the {@link FacetValueType} enumeration
 */
public interface FacetValueTypeRepresentation extends ValueRepresentation {

    /**
     * @return {@link FacetValueType}
     */
    FacetValueType getType();

    @Override
    FacetValueTypeRepresentation clone();
}
