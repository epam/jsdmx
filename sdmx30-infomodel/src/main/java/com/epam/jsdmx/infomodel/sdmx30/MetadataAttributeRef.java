package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Reference to a MetadataAttribute with the relationship
 */
public interface MetadataAttributeRef extends AttributeComponent {

    /**
     * @return MetadataAttribute ID from the referenced {@link DataStructureDefinition}
     */
    String getId();

    /**
     * @return Relationship of metadataAttribute
     */
    AttributeRelationship getMetadataRelationship();
}
