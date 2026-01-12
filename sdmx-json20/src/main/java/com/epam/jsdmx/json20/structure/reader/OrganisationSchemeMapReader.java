package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationSchemeMapImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.collections.CollectionUtils;

public class OrganisationSchemeMapReader extends MaintainableReader<OrganisationSchemeMapImpl> implements ItemMapReader {

    public OrganisationSchemeMapReader(VersionableReader versionableArtefact) {
        super(versionableArtefact);
    }

    @Override
    protected OrganisationSchemeMapImpl createMaintainableArtefact() {
        return new OrganisationSchemeMapImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, OrganisationSchemeMapImpl organisationSchemeMap) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.TARGET:
                getTargetSource(parser).ifPresent(organisationSchemeMap::setTarget);
                break;
            case StructureUtils.SOURCE:
                getTargetSource(parser).ifPresent(organisationSchemeMap::setSource);
                break;
            case StructureUtils.ITEM_MAPS:
                List<ItemMap> itemMaps = ReaderUtils.getArray(parser, (this::getItemMap));
                if (CollectionUtils.isNotEmpty(itemMaps)) {
                    organisationSchemeMap.setItemMaps(itemMaps);
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "OrganisationSchemeMap: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.ORGANISATION_SCHEME_MAPS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<OrganisationSchemeMapImpl> artefacts) {
        artefact.getOrganisationSchemeMaps().addAll(artefacts);
    }
}
