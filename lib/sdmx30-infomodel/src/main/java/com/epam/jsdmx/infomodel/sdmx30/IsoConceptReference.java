package com.epam.jsdmx.infomodel.sdmx30;

/**
 * The ISOConceptReference identifies the concept and concept scheme in which it is contained.
 */
public interface IsoConceptReference {

    /**
     * @return The maintenance agency of the concept scheme containing the concept.
     */
    String getAgency();

    /**
     * @return The identifier of the concept scheme.
     */
    String getSchemeId();

    /**
     * @return The identifier of the concept.
     */
    String getConceptId();
}
