package com.epam.jsdmx.infomodel.sdmx30;

/**
 * A maintained collection of Organisation Units
 */
public interface OrganisationUnitScheme extends OrganisationScheme<OrganisationUnit> {
    @Override
    OrganisationUnitScheme toStub();

    @Override
    OrganisationUnitScheme toCompleteStub();
}
