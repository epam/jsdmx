package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setCommonAttributes;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomyMapImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class ReportingTaxonomyMapReader extends XmlReader<ReportingTaxonomyMapImpl> implements ItemSchemeMapReader {

    private final List<ItemMap> itemMaps = new ArrayList<>();

    public ReportingTaxonomyMapReader(AnnotableReader annotableReader,
                                      NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected ReportingTaxonomyMapImpl createMaintainableArtefact() {
        return new ReportingTaxonomyMapImpl();
    }

    @Override
    protected ReportingTaxonomyMapImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        ReportingTaxonomyMapImpl reportingTaxonomyMap = super.read(reader);
        reportingTaxonomyMap.setItemMaps(new ArrayList<>(itemMaps));
        return reportingTaxonomyMap;
    }

    @Override
    protected void read(XMLStreamReader reader, ReportingTaxonomyMapImpl reportingTaxonomyMap) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.SOURCE:
                Optional.ofNullable(reader.getElementText())
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(reportingTaxonomyMap::setSource);
                break;
            case XmlConstants.TARGET:
                Optional.ofNullable(reader.getElementText())
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(reportingTaxonomyMap::setTarget);
                break;
            case XmlConstants.ITEM_MAP:
                itemMaps.add(getItemMap(reader, annotableReader));
                break;
            default:
                throw new IllegalArgumentException("ReportingTaxonomyMap " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, ReportingTaxonomyMapImpl maintainableArtefact) throws XMLStreamException {
        setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.REPORTING_TAXONOMY_MAP;
    }

    @Override
    protected String getNames() {
        return XmlConstants.REPORTING_TAXONOMY_MAPS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<ReportingTaxonomyMapImpl> artefacts) {
        artefact.getReportingTaxonomyMaps().addAll(artefacts);
    }

    @Override
    protected void clean() {
        itemMaps.clear();
    }
}
