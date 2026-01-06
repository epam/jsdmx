package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationSchemeMap;

import com.fasterxml.jackson.core.JsonGenerator;

public class OrganisationSchemeMapWriter extends MaintainableWriter<OrganisationSchemeMap> {

    private final AnnotableWriter annotableWriter;
    private final ItemMapWriter itemMapWriter;

    public OrganisationSchemeMapWriter(VersionableWriter versionableWriter,
                                       LinksWriter linksWriter,
                                       AnnotableWriter annotableWriter,
                                       ItemMapWriter itemMapWriter) {
        super(versionableWriter, linksWriter);
        this.annotableWriter = annotableWriter;
        this.itemMapWriter = itemMapWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, OrganisationSchemeMap organisationSchemeMap) throws IOException {
        super.writeFields(jsonGenerator, organisationSchemeMap);
        List<ItemMap> itemMaps = organisationSchemeMap.getItemMaps();
        itemMapWriter.writeItemMaps(jsonGenerator, itemMaps, annotableWriter);
        itemMapWriter.writeSource(jsonGenerator, organisationSchemeMap.getSource());
        itemMapWriter.writeTarget(jsonGenerator, organisationSchemeMap.getTarget());
    }

    @Override
    protected Set<OrganisationSchemeMap> extractArtefacts(Artefacts artefacts) {
        return artefacts.getOrganisationSchemeMaps();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.ORGANISATION_SCHEME_MAPS;
    }
}
