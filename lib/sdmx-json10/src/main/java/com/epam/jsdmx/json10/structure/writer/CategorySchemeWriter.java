package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Category;
import com.epam.jsdmx.infomodel.sdmx30.CategoryScheme;

import com.fasterxml.jackson.core.JsonGenerator;

public class CategorySchemeWriter extends MaintainableWriter<CategoryScheme> {

    private final NameableWriter nameableWriter;

    public CategorySchemeWriter(VersionableWriter versionableWriter, LinksWriter linksWriter, NameableWriter nameableWriter) {
        super(versionableWriter, linksWriter);
        this.nameableWriter = nameableWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, CategoryScheme categoryScheme) throws IOException {
        super.writeFields(jsonGenerator, categoryScheme);
        jsonGenerator.writeBooleanField(StructureUtils.IS_PARTIAL, categoryScheme.isPartial());
        writeCategoriesList(jsonGenerator, categoryScheme.getItems());
    }

    @Override
    protected Set<CategoryScheme> extractArtefacts(Artefacts artefacts) {
        return artefacts.getCategorySchemes();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.CATEGORY_SCHEMES;
    }

    private void writeCategoriesList(JsonGenerator jsonGenerator, List<? extends Category> categoryScheme) throws IOException {
        if (categoryScheme != null) {
            jsonGenerator.writeFieldName(StructureUtils.CATEGORIES);
            jsonGenerator.writeStartArray();
            if (!categoryScheme.isEmpty()) {
                for (Category category : categoryScheme) {
                    jsonGenerator.writeStartObject();
                    nameableWriter.write(jsonGenerator, category);
                    if (!category.getHierarchy().isEmpty()) {
                        writeCategoriesList(jsonGenerator, category.getHierarchy());
                    }
                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }
}
