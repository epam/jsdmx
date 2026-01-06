package com.epam.jsdmx.infomodel.sdmx30;

/**
 * A maintained collection of Maintenance Agencies.
 */
public interface AgencyScheme extends OrganisationScheme<Agency> {
    @Override
    AgencyScheme toStub();

    @Override
    AgencyScheme toCompleteStub();
}
