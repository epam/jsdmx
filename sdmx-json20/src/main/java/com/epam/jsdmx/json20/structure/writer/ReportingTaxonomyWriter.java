package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Item;
import com.epam.jsdmx.infomodel.sdmx30.ReportingCategory;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomy;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections.CollectionUtils;

public class ReportingTaxonomyWriter extends MaintainableWriter<ReportingTaxonomy> {

    private final NameableWriter nameableWriter;
    private final ReferenceAdapter referenceAdapter;

    public ReportingTaxonomyWriter(VersionableWriter versionableWriter,
                                   LinksWriter linksWriter,
                                   NameableWriter nameableWriter,
                                   ReferenceAdapter referenceAdapter) {
        super(versionableWriter, linksWriter);
        this.nameableWriter = nameableWriter;
        this.referenceAdapter = referenceAdapter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, ReportingTaxonomy reportingTaxonomy) throws IOException {
        super.writeFields(jsonGenerator, reportingTaxonomy);
        jsonGenerator.writeBooleanField(StructureUtils.IS_PARTIAL, reportingTaxonomy.isPartial());
        List<? extends ReportingCategory> items = reportingTaxonomy.getItems();
        writeReportingCategories(jsonGenerator, items);
    }

    @Override
    protected Set<ReportingTaxonomy> extractArtefacts(Artefacts artefacts) {
        return artefacts.getReportingTaxonomies();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.REPORTING_TAXONOMIES;
    }

    private void writeReportingCategories(JsonGenerator jsonGenerator, List<? extends ReportingCategory> items) throws IOException {
        if (CollectionUtils.isNotEmpty(items)) {
            jsonGenerator.writeFieldName(StructureUtils.REPORTING_CATEGORIES);
            jsonGenerator.writeStartArray();
            for (ReportingCategory reportingCategory : items) {
                if (reportingCategory != null) {
                    jsonGenerator.writeStartObject();

                    nameableWriter.write(jsonGenerator, reportingCategory);

                    List<ArtefactReference> structures = reportingCategory.getStructures();
                    StructureUtils.writeArtefactReferences(jsonGenerator, structures, StructureUtils.STRUCTURAL_METADATA, referenceAdapter);

                    List<ArtefactReference> flows = reportingCategory.getFlows();
                    StructureUtils.writeArtefactReferences(jsonGenerator, flows, StructureUtils.PROVISIONING_METADATA, referenceAdapter);

                    List<? extends Item> hierarchy = reportingCategory.getHierarchy();
                    writeReportingCategories(jsonGenerator, (List<ReportingCategory>) hierarchy);
                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }
}
