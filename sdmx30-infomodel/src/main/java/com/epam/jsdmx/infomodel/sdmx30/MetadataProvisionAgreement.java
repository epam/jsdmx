package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Links the Metadata Provider to the relevant Structure Usage (i.e., the Metadataflow) for which
 * the provider supplies metadata. The agreement may constrain the scope of the metadata that can
 * be provided, by means of a MetadataConstraint.
 */
public interface MetadataProvisionAgreement extends MaintainableArtefact, ConstrainableArtefact {
    /**
     * Reference to the structure usage that is controlled by this provision agreement.
     */
    ArtefactReference getControlledStructureUsage();

    /**
     * Reference to the provider of the metadata.
     */
    ArtefactReference getMetadataProvider();

    /**
     * Association to a metadata source, which can process a metadata query.
     */
    String getDataSource();

}
