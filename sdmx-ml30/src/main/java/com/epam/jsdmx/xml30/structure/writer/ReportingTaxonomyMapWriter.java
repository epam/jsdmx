package com.epam.jsdmx.xml30.structure.writer;

import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomyMap;

public class ReportingTaxonomyMapWriter extends XmlWriter<ReportingTaxonomyMap> {

    private final ItemSchemeMapWriter itemSchemeMapWriter;

    public ReportingTaxonomyMapWriter(NameableWriter nameableWriter,
                                      AnnotableWriter annotableWriter,
                                      CommonAttributesWriter commonAttributesWriter,
                                      LinksWriter linksWriter,
                                      ItemSchemeMapWriter itemSchemeMapWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.itemSchemeMapWriter = itemSchemeMapWriter;
    }

    @Override
    protected void writeAttributes(ReportingTaxonomyMap reportingTaxonomyMap, XMLStreamWriter writer) throws XMLStreamException {
        commonAttributesWriter.writeAttributes(reportingTaxonomyMap, writer);
    }

    @Override
    protected void writeCustomAttributeElements(ReportingTaxonomyMap reportingTaxonomyMap, XMLStreamWriter writer) throws XMLStreamException {
        itemSchemeMapWriter.writeSource(writer, reportingTaxonomyMap.getSource(), XmlConstants.STRUCTURE + XmlConstants.SOURCE);
        itemSchemeMapWriter.writeTarget(writer, reportingTaxonomyMap.getTarget(), XmlConstants.STRUCTURE + XmlConstants.TARGET);
        itemSchemeMapWriter.writeItems(writer, reportingTaxonomyMap.getItemMaps(), annotableWriter);
    }

    @Override
    protected String getName() {
        return XmlConstants.REPORTING_TAXONOMY_MAP;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.REPORTING_TAXONOMY_MAPS;
    }

    @Override
    protected Set<ReportingTaxonomyMap> extractArtefacts(Artefacts artefacts) {
        return artefacts.getReportingTaxonomyMaps();
    }
}
