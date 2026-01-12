package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEmptyOrNullElementText;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setCommonAttributes;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.ReportingCategory;
import com.epam.jsdmx.infomodel.sdmx30.ReportingCategoryImpl;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomyImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class ReportingTaxonomyReader extends XmlReader<ReportingTaxonomyImpl> {

    private final List<ReportingCategory> reportingCategories = new ArrayList<>();

    public ReportingTaxonomyReader(AnnotableReader annotableReader,
                                   NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected ReportingTaxonomyImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        ReportingTaxonomyImpl reportingTaxonomy = super.read(reader);
        reportingTaxonomy.setItems(new ArrayList<>(reportingCategories));
        return reportingTaxonomy;
    }

    @Override
    protected void read(XMLStreamReader reader, ReportingTaxonomyImpl maintainableArtefact) throws URISyntaxException, XMLStreamException {
        readReportingCategories(reader);
    }

    @Override
    protected ReportingTaxonomyImpl createMaintainableArtefact() {
        return new ReportingTaxonomyImpl();
    }

    private void readReportingCategories(XMLStreamReader reader) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        if (!XmlConstants.REPORTING_CATEGORY.equals(localName)) {
            throw new IllegalArgumentException("ReportingTaxonomy " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
        reportingCategories.add(getReportingCategory(reader));
    }

    private ReportingCategory getReportingCategory(XMLStreamReader reader) throws URISyntaxException, XMLStreamException {
        var reportingCategory = new ReportingCategoryImpl();
        Map<String, String> names = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();

        List<ArtefactReference> flows = new ArrayList<>();
        List<ArtefactReference> structures = new ArrayList<>();

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(reportingCategory::setId);

        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            reportingCategory.setUri(new URI(uri));
        }
        List<ReportingCategory> hierarchy = new ArrayList<>();

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.REPORTING_CATEGORY)) {
            String name = reader.getLocalName();
            switch (name) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, reportingCategory);
                    break;
                case XmlConstants.NAME:
                    nameableReader.setNameable(reader, names);
                    break;
                case XmlConstants.DESCRIPTION:
                    nameableReader.setNameable(reader, descriptions);
                    break;
                case XmlConstants.PROVISIONING_METADATA:
                    String flow = reader.getElementText();
                    if (isNotEmptyOrNullElementText(flow)) {
                        flows.add(new MaintainableArtefactReference(flow));
                    }
                    break;
                case XmlConstants.STRUCTURAL_METADATA:
                    String structure = reader.getElementText();
                    if (isNotEmptyOrNullElementText(structure)) {
                        structures.add(new MaintainableArtefactReference(structure));
                    }
                    break;
                case XmlConstants.REPORTING_CATEGORY:
                    hierarchy.add(getReportingCategory(reader));
                    break;
                default:
                    throw new IllegalArgumentException("ReportingCategory " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
            }
            moveToNextTag(reader);
        }
        reportingCategory.setFlows(flows);
        reportingCategory.setStructures(structures);
        reportingCategory.setName(new InternationalString(names));
        reportingCategory.setDescription(new InternationalString(descriptions));
        reportingCategory.setHierarchy(hierarchy);
        return reportingCategory;
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, ReportingTaxonomyImpl reportingTaxonomy) throws XMLStreamException {
        setCommonAttributes(reader, reportingTaxonomy);
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_PARTIAL))
            .map(Boolean::parseBoolean)
            .ifPresent(reportingTaxonomy::setPartial);
    }

    @Override
    protected String getName() {
        return XmlConstants.REPORTING_TAXONOMY;
    }

    @Override
    protected String getNames() {
        return XmlConstants.REPORTING_TAXONOMIES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<ReportingTaxonomyImpl> artefacts) {
        artefact.getReportingTaxonomies().addAll(artefacts);
    }

    @Override
    protected void clean() {
        reportingCategories.clear();
    }
}
