package com.epam.jsdmx.infomodel.sdmx30;

/**
 * A maintained collection of Metadata Providers.
 */
public interface MetadataProviderScheme extends OrganisationScheme<MetadataProvider> {
    @Override
    MetadataProviderScheme toStub();

    @Override
    MetadataProviderScheme toCompleteStub();
}
