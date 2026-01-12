package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeMap;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;

import com.fasterxml.jackson.core.JsonGenerator;

public class ConceptSchemeMapWriter extends MaintainableWriter<ConceptSchemeMap> {

    private final AnnotableWriter annotableWriter;
    private final ItemMapWriter itemMapWriter;

    public ConceptSchemeMapWriter(VersionableWriter versionableWriter,
                                  LinksWriter linksWriter,
                                  AnnotableWriter annotableWriter,
                                  ItemMapWriter itemMapWriter) {
        super(versionableWriter, linksWriter);
        this.annotableWriter = annotableWriter;
        this.itemMapWriter = itemMapWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, ConceptSchemeMap conceptSchemeMap) throws IOException {
        super.writeFields(jsonGenerator, conceptSchemeMap);
        List<ItemMap> itemMaps = conceptSchemeMap.getItemMaps();
        itemMapWriter.writeItemMaps(jsonGenerator, itemMaps, annotableWriter);
        itemMapWriter.writeSource(jsonGenerator, conceptSchemeMap.getSource());
        itemMapWriter.writeTarget(jsonGenerator, conceptSchemeMap.getTarget());
    }

    @Override
    protected Set<ConceptSchemeMap> extractArtefacts(Artefacts artefacts) {
        return artefacts.getConceptSchemeMaps();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.CONCEPT_SCHEME_MAPS;
    }
}
