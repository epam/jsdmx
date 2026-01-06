package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.collections.CollectionUtils;

public class ConceptSchemeMapReader extends MaintainableReader<ConceptSchemeMapImpl> implements ItemMapReader {

    public ConceptSchemeMapReader(VersionableReader versionableArtefact) {
        super(versionableArtefact);
    }

    @Override
    protected ConceptSchemeMapImpl createMaintainableArtefact() {
        return new ConceptSchemeMapImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, ConceptSchemeMapImpl conceptSchemeMap) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.TARGET:
                getTargetSource(parser).ifPresent(conceptSchemeMap::setTarget);
                break;
            case StructureUtils.SOURCE:
                getTargetSource(parser).ifPresent(conceptSchemeMap::setSource);
                break;
            case StructureUtils.ITEM_MAPS:
                List<ItemMap> itemMaps = ReaderUtils.getArray(parser, (this::getItemMap));
                if (CollectionUtils.isNotEmpty(itemMaps)) {
                    conceptSchemeMap.setItemMaps(itemMaps);
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "ConceptSchemeMap: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.CONCEPT_SCHEME_MAPS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<ConceptSchemeMapImpl> artefacts) {
        artefact.getConceptSchemeMaps().addAll(artefacts);
    }
}
