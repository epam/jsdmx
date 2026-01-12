package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.collections.CollectionUtils;

public class CategorySchemeMapReader extends MaintainableReader<CategorySchemeMapImpl> implements ItemMapReader {

    public CategorySchemeMapReader(VersionableReader versionableArtefact) {
        super(versionableArtefact);
    }

    @Override
    protected CategorySchemeMapImpl createMaintainableArtefact() {
        return new CategorySchemeMapImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, CategorySchemeMapImpl categorySchemeMap) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.TARGET:
                getTargetSource(parser).ifPresent(categorySchemeMap::setTarget);
                break;
            case StructureUtils.SOURCE:
                getTargetSource(parser).ifPresent(categorySchemeMap::setSource);
                break;
            case StructureUtils.ITEM_MAPS:
                List<ItemMap> itemMaps = ReaderUtils.getArray(parser, (this::getItemMap));
                if (CollectionUtils.isNotEmpty(itemMaps)) {
                    categorySchemeMap.setItemMaps(itemMaps);
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "CategorySchemeMap: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.CATEGORY_SCHEME_MAPS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<CategorySchemeMapImpl> artefacts) {
        artefact.getCategorySchemeMaps().addAll(artefacts);
    }
}
