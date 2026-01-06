package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * MetadataStructureDefinition (MSD) defines the {@link MetadataAttribute MetadataAttributes}, within an {@link MetadataAttributeDescriptor},
 * that can be associated with the objects identified in the {@link Dataflow Dataflows} and
 * {@link com.epam.jsdmx.infomodel.sdmx30.MetadataProvisionAgreement} that refer to the MSD.
 */
public interface MetadataStructureDefinition extends Structure {

    @Override
    MetadataStructureDefinition toStub();

    @Override
    MetadataStructureDefinition toCompleteStub();

    /**
     * @return {@link MetadataAttributeDescriptor}
     */
    MetadataAttributeDescriptor getAttributeDescriptor();

    /**
     * @return Collection of {@link MetadataAttribute}
     */
    List<MetadataAttribute> getComponents();

    /**
     * @return {@link MetadataAttribute}
     */
    MetadataAttribute getComponent(String id);
}
