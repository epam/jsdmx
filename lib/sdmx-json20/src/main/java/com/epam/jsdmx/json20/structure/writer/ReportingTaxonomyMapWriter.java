package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomyMap;

import com.fasterxml.jackson.core.JsonGenerator;

public class ReportingTaxonomyMapWriter extends MaintainableWriter<ReportingTaxonomyMap> {

    private final AnnotableWriter annotableWriter;
    private final ItemMapWriter itemMapWriter;

    public ReportingTaxonomyMapWriter(VersionableWriter versionableWriter,
                                      LinksWriter linksWriter,
                                      AnnotableWriter annotableWriter,
                                      ItemMapWriter itemMapWriter) {
        super(versionableWriter, linksWriter);
        this.annotableWriter = annotableWriter;
        this.itemMapWriter = itemMapWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, ReportingTaxonomyMap reportingTaxonomyMap) throws IOException {
        super.writeFields(jsonGenerator, reportingTaxonomyMap);
        List<ItemMap> itemMaps = reportingTaxonomyMap.getItemMaps();
        itemMapWriter.writeItemMaps(jsonGenerator, itemMaps, annotableWriter);
        itemMapWriter.writeSource(jsonGenerator, reportingTaxonomyMap.getSource());
        itemMapWriter.writeTarget(jsonGenerator, reportingTaxonomyMap.getTarget());
    }

    @Override
    protected Set<ReportingTaxonomyMap> extractArtefacts(Artefacts artefacts) {
        return artefacts.getReportingTaxonomyMaps();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.REPORTING_TAXONOMY_MAPS;
    }
}