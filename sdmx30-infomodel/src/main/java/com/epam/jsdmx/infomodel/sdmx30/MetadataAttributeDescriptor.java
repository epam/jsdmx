package com.epam.jsdmx.infomodel.sdmx30;

/**
 * The MetadataAttributeDescriptor comprises a set of MetadataAttributes – these
 * can be defined as a hierarchy. Each {@link MetadataAttribute} identifies a Concept that is
 * reported or disseminated in a MetadataSet that uses this {@link MetadataStructureDefinition}.
 */
public interface MetadataAttributeDescriptor extends ComponentList<MetadataAttribute> {
}
