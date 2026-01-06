package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Defines a characteristic of data that are collected or disseminated and is
 * grouped in the {@link DataStructureDefinition} by a single {@link AttributeDescriptor}
 */
public interface DataAttribute extends AttributeComponent {
    /**
     * @return value that indicates whether the DataAttribute may need to report
     * more than one values, i.e., an array of values
     */
    int getMaxOccurs();

    /**
     * @return minimum number of array values to be included when the {@link DataAttribute} is reported.
     */
    int getMinOccurs();

    /**
     * @return AttributeRelationship that defines the constructs to which
     * the AttributeComponent is to be reported within a DataSet
     */
    AttributeRelationship getAttributeRelationship();

    /**
     * @return {@link MeasureRelationship} which relates a DataAttribute to one or more Measures
     */
    MeasureRelationship getMeasureRelationship();

    /**
     * Association with a {@link Concept} that identifies its role in the {@link DataStructureDefinition}
     *
     * @return List of ArtefactReference
     */
    List<ArtefactReference> getConceptRoles();

    /**
     * @return the value that can be indicated if {@link DataAttribute} must be reported or not
     */
    boolean isMandatory();
}
