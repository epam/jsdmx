package com.epam.jsdmx.json10.structure.writer;


import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.AgencyScheme;
import com.epam.jsdmx.infomodel.sdmx30.AgencySchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;

import com.fasterxml.jackson.core.JsonGenerator;

public class AgencySchemeWriter extends MaintainableWriter<AgencyScheme> {

    private final OrganisationWriter organisationWriter;

    public AgencySchemeWriter(VersionableWriter versionableWriter,
                              LinksWriter linksWriter,
                              OrganisationWriter organisationWriter) {
        super(versionableWriter, linksWriter);
        this.organisationWriter = organisationWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, AgencyScheme artefact) throws IOException {
        AgencySchemeImpl agencyScheme = (AgencySchemeImpl) artefact;
        agencyScheme.setId(StructureUtils.AGENCIES_ID);
        super.writeFields(jsonGenerator, agencyScheme);
        jsonGenerator.writeBooleanField(StructureUtils.IS_PARTIAL, agencyScheme.isPartial());
        organisationWriter.writeOrganisation(jsonGenerator, artefact.getItems(), StructureUtils.AGENCIES);
    }

    @Override
    protected Set<AgencyScheme> extractArtefacts(Artefacts artefacts) {
        return artefacts.getAgencySchemes();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.AGENCY_SCHEMES;
    }
}
