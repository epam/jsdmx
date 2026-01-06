package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Links the Data Provider to the relevant Structure Usage (i.e., the Dataflow) for which the provider
 * supplies data. The agreement may constrain the scope of the data that can be provided, by means of a DataConstraint.
 */
public interface ProvisionAgreement extends MaintainableArtefact, ConstrainableArtefact {
    /**
     * Reference to the structure usage that is controlled by this provision agreement.
     */
    ArtefactReference getControlledStructureUsage();

    /**
     * Reference to the provider of the data.
     */
    ArtefactReference getDataProvider();

    /**
     * Association to data source, which can process a data query.
     */
    String getDataSource();
}
