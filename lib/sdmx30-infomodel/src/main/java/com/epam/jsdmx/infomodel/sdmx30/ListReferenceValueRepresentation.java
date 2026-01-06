package com.epam.jsdmx.infomodel.sdmx30;

/**
 * ListReferenceValueRepresentation is a type of representation which is defined by {@link Codelist}
 */
public interface ListReferenceValueRepresentation extends ValueRepresentation {
    /**
     * @return The reference to a {@link Codelist}
     */
    ArtefactReference getListReference();
}
