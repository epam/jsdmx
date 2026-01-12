package com.epam.jsdmx.json20.structure.reader;


import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getBooleanJsonField;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Agency;
import com.epam.jsdmx.infomodel.sdmx30.AgencyImpl;
import com.epam.jsdmx.infomodel.sdmx30.AgencySchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.collections.CollectionUtils;

public class AgencySchemeReader extends MaintainableReader<AgencySchemeImpl> {

    private final OrganisationReader organisationReader;

    public AgencySchemeReader(VersionableReader versionableArtefact,
                              OrganisationReader organisationReader) {
        super(versionableArtefact);
        this.organisationReader = organisationReader;
    }

    @Override
    protected AgencySchemeImpl createMaintainableArtefact() {
        return new AgencySchemeImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, AgencySchemeImpl agencyScheme) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.IS_PARTIAL:
                agencyScheme.setPartial(getBooleanJsonField(parser));
                break;
            case StructureUtils.AGENCIES:
                List<Agency> agencies = ReaderUtils.getArray(parser, (this::getAgency));
                if (CollectionUtils.isNotEmpty(agencies)) {
                    agencyScheme.setItems(agencies);
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "AgencyScheme: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.AGENCY_SCHEMES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<AgencySchemeImpl> artefacts) {
        artefact.getAgencySchemes().addAll(artefacts);
    }

    private Agency getAgency(JsonParser parser) {
        return organisationReader.getOrganisation(parser, new AgencyImpl(), null);
    }
}
