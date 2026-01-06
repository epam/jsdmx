package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnit;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnitScheme;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnitSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;

import com.fasterxml.jackson.core.JsonGenerator;

public class OrganisationUnitSchemeWriter extends MaintainableWriter<OrganisationUnitScheme> {

    private final OrganisationWriter organisationWriter;

    public OrganisationUnitSchemeWriter(VersionableWriter versionableWriter,
                                        LinksWriter linksWriter,
                                        OrganisationWriter organisationWriter) {
        super(versionableWriter, linksWriter);
        this.organisationWriter = organisationWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, OrganisationUnitScheme organisationUnitScheme) throws IOException {
        OrganisationUnitSchemeImpl organisationUnitSchemeImpl = (OrganisationUnitSchemeImpl) organisationUnitScheme;
        organisationUnitSchemeImpl.setVersion(Version.createFromString(StructureUtils.DEFAULT_VERSION));
        super.writeFields(jsonGenerator, organisationUnitSchemeImpl);
        jsonGenerator.writeBooleanField(StructureUtils.IS_PARTIAL, organisationUnitSchemeImpl.isPartial());
        List<OrganisationUnit> items = organisationUnitSchemeImpl.getItems();
        organisationWriter.writeOrganisation(jsonGenerator, items, StructureUtils.ORGANISATION_UNITS);
    }

    @Override
    protected Set<OrganisationUnitScheme> extractArtefacts(Artefacts artefacts) {
        return artefacts.getOrganisationUnitSchemes();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.ORGANISATION_UNIT_SCHEMES;
    }
}
