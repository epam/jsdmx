package com.epam.jsdmx.serializer.sdmx30.common;

import com.epam.jsdmx.infomodel.sdmx30.ConstrainableArtefact;

/**
 * A ProvisionAgreement links the artefact that defines
 * how data are structured and classified (StructureUsage) to the DataProvider.
 * The agreement may constrain the scope of the data that can be provided, by means
 * of a DataConstraint.
 */
public interface ProvisionAgreement extends ConstrainableArtefact {
}
