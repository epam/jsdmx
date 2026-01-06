package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Subcomponent of the {@link DataStructureDefinition DataStructureDefinition}
 * AttributeDescriptor may contain one or more {@link DataAttribute} definition and
 * (or) {@link MetadataAttribute} reference.
 */
public interface AttributeDescriptor extends ComponentList<DataAttribute> {
    /**
     * @return List of {@link MetadataAttributeRef} - reference to an attribute
     */
    List<MetadataAttributeRef> getMetadataAttributes();
}
