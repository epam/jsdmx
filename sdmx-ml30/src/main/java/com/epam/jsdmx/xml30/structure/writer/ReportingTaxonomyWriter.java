package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.writer.XmlWriterUtils.writeCharacters;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ReportingCategory;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomy;

import org.apache.commons.collections.CollectionUtils;

public class ReportingTaxonomyWriter extends XmlWriter<ReportingTaxonomy> {

    public ReportingTaxonomyWriter(NameableWriter nameableWriter,
                                   AnnotableWriter annotableWriter,
                                   CommonAttributesWriter commonAttributesWriter,
                                   LinksWriter linksWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
    }

    @Override
    protected void writeAttributes(ReportingTaxonomy reportingTaxonomy, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(reportingTaxonomy, writer);
        writer.writeAttribute(XmlConstants.IS_PARTIAL, String.valueOf(reportingTaxonomy.isPartial()));
    }

    @Override
    protected void writeCustomAttributeElements(ReportingTaxonomy reportingTaxonomy, XMLStreamWriter writer) throws XMLStreamException {
        List<? extends ReportingCategory> reportingTaxonomyItems = reportingTaxonomy.getItems();
        writeReportingCategory(writer, reportingTaxonomyItems);

    }

    private void writeReportingCategory(XMLStreamWriter writer, List<? extends ReportingCategory> reportingTaxonomyItems) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(reportingTaxonomyItems)) {
            for (ReportingCategory reportingCategory : reportingTaxonomyItems) {
                if (reportingCategory != null) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.REPORTING_CATEGORY);
                    XmlWriterUtils.writeIdUriAttributes(writer, reportingCategory.getId(), reportingCategory.getUri());
                    if (reportingCategory.getContainer() != null) {
                        writer.writeAttribute(XmlConstants.URN, reportingCategory.getUrn());
                    }
                    annotableWriter.write(reportingCategory, writer);
                    nameableWriter.write(reportingCategory, writer);

                    List<ArtefactReference> flows = reportingCategory.getFlows();
                    writeArtefactReferences(flows, XmlConstants.STRUCTURE + XmlConstants.PROVISIONING_METADATA, writer);

                    List<ArtefactReference> structures = reportingCategory.getStructures();
                    writeArtefactReferences(structures, XmlConstants.STRUCTURE + XmlConstants.STRUCTURAL_METADATA, writer);

                    List<ReportingCategory> hierarchy = (List<ReportingCategory>) reportingCategory.getHierarchy();
                    writeReportingCategory(writer, hierarchy);

                    writer.writeEndElement();
                }
            }
        }
    }

    private void writeArtefactReferences(List<ArtefactReference> artefactReferences, String referenceName, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(artefactReferences)) {
            for (ArtefactReference artefactReference : artefactReferences) {
                if (artefactReference != null) {
                    writer.writeStartElement(referenceName);
                    writeCharacters(artefactReference.getUrn(), writer);
                    writer.writeEndElement();
                }
            }
        }
    }

    @Override
    protected String getName() {
        return XmlConstants.REPORTING_TAXONOMY;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.REPORTING_TAXONOMIES;
    }

    @Override
    protected Set<ReportingTaxonomy> extractArtefacts(Artefacts artefacts) {
        return artefacts.getReportingTaxonomies();
    }
}
