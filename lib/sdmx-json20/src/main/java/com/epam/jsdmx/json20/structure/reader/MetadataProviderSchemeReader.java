package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getBooleanJsonField;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProvider;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProviderImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProviderSchemeImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.collections.CollectionUtils;

public class MetadataProviderSchemeReader extends MaintainableReader<MetadataProviderSchemeImpl> {

    private final OrganisationReader organisationReader;

    public MetadataProviderSchemeReader(VersionableReader versionableArtefact,
                                        OrganisationReader organisationReader) {
        super(versionableArtefact);
        this.organisationReader = organisationReader;
    }

    @Override
    protected MetadataProviderSchemeImpl createMaintainableArtefact() {
        return new MetadataProviderSchemeImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, MetadataProviderSchemeImpl metadataProviderScheme) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.IS_PARTIAL:
                metadataProviderScheme.setPartial(getBooleanJsonField(parser));
                break;
            case StructureUtils.METADATA_PROVIDERS:
                List<MetadataProvider> dataProviders = ReaderUtils.getArray(parser, (this::getMetadataProvider));
                if (CollectionUtils.isNotEmpty(dataProviders)) {
                    metadataProviderScheme.setItems(dataProviders);
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "MetadataProviderScheme: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.METADATA_PROVIDER_SCHEMES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<MetadataProviderSchemeImpl> artefacts) {
        artefact.getMetadataProviderSchemes().addAll(artefacts);
    }

    private MetadataProvider getMetadataProvider(JsonParser parser) {
        return organisationReader.getOrganisation(parser, new MetadataProviderImpl(), null);
    }
}
