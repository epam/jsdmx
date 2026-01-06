package com.epam.jsdmx.infomodel.sdmx30;

/**
 * A maintained collection of Data Providers.
 */
public interface DataProviderScheme extends OrganisationScheme<DataProvider> {
    @Override
    DataProviderScheme toStub();

    @Override
    DataProviderScheme toCompleteStub();
}
