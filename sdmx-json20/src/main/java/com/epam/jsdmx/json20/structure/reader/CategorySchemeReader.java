package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getBooleanJsonField;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Category;
import com.epam.jsdmx.infomodel.sdmx30.CategoryImpl;
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class CategorySchemeReader extends MaintainableReader<CategorySchemeImpl> {

    private final NameableReader nameableArtefact;

    public CategorySchemeReader(VersionableReader versionableArtefact, NameableReader nameableArtefact) {
        super(versionableArtefact);
        this.nameableArtefact = nameableArtefact;
    }

    @Override
    protected CategorySchemeImpl createMaintainableArtefact() {
        return new CategorySchemeImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, CategorySchemeImpl categoryScheme) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.IS_PARTIAL:
                categoryScheme.setPartial(getBooleanJsonField(parser));
                break;
            case StructureUtils.CATEGORIES:
                List<Category> categories = ReaderUtils.getArray(parser, (this::getCategory));
                if (CollectionUtils.isNotEmpty(categories)) {
                    categoryScheme.setItems(categories);
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "CategoryScheme: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.CATEGORY_SCHEMES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<CategorySchemeImpl> artefacts) {
        artefact.getCategorySchemes().addAll(artefacts);
    }

    private Category getCategory(JsonParser parser) {
        try {
            CategoryImpl category = new CategoryImpl();
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                if (StructureUtils.CATEGORIES.equals(fieldName)) {
                    parser.nextToken();
                    category.setHierarchy(ReaderUtils.getArray(parser, (this::getCategory)));
                } else {
                    nameableArtefact.read(category, parser);
                }
            }
            return category;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }
}
