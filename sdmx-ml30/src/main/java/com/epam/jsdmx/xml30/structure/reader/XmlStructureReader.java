package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactImpl;
import com.epam.jsdmx.serializer.sdmx30.common.DataLocation;
import com.epam.jsdmx.serializer.sdmx30.common.Header;
import com.epam.jsdmx.serializer.sdmx30.structure.StructureReader;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import lombok.SneakyThrows;

/**
 * Streaming deserialization for xml (3.0)
 */
public class XmlStructureReader implements StructureReader {

    private static final Set<String> SUPPORTED_STRUCTURES = Set.of(
        XmlConstants.CODELISTS,
        XmlConstants.HIERARCHIES,
        XmlConstants.CONCEPT_SCHEMES,
        XmlConstants.DATA_STRUCTURES,
        XmlConstants.DATAFLOWS,
        XmlConstants.METADATA_STRUCTURES,
        XmlConstants.METADATA_FLOWS,
        XmlConstants.REPRESENTATION_MAPS,
        XmlConstants.STRUCTURE_MAPS,
        XmlConstants.CATEGORISATIONS,
        XmlConstants.CATEGORY_SCHEMES,
        XmlConstants.AGENCY_SCHEMES,
        XmlConstants.CATEGORY_SCHEME_MAPS,
        XmlConstants.CONCEPT_SCHEME_MAPS,
        XmlConstants.DATA_CONSUMER_SCHEMES,
        XmlConstants.DATA_PROVIDER_SCHEMES,
        XmlConstants.DATA_CONSTRAINTS,
        XmlConstants.METADATA_PROVIDER_SCHEMES,
        XmlConstants.HIERARCHY_ASSOCIATIONS,
        XmlConstants.METADATA_CONSTRAINTS,
        XmlConstants.METADATA_PROVISION_AGREEMENTS,
        XmlConstants.ORGANISATION_SCHEME_MAPS,
        XmlConstants.ORGANISATION_UNIT_SCHEMES,
        XmlConstants.PROCESSES,
        XmlConstants.PROVISION_AGREEMENTS,
        XmlConstants.REPORTING_TAXONOMIES,
        XmlConstants.REPORTING_TAXONOMY_MAPS,
        XmlConstants.VALUE_LISTS,
        XmlConstants.GEO_CODELISTS,
        XmlConstants.GEOGRID_CODELISTS
    );

    private final HeaderReader headerReader;

    private final List<? extends XmlReader<? extends MaintainableArtefact>> readers;

    public XmlStructureReader(HeaderReader headerReader, List<? extends XmlReader<? extends MaintainableArtefact>> readers) {
        this.headerReader = headerReader;
        this.readers = List.copyOf(readers);
    }

    public Artefacts read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        try {
            moveToNextTag(reader);
            Artefacts artefacts = new ArtefactsImpl();
            while (isNotEndingTag(reader, XmlConstants.MES_STRUCTURE)) {
                moveToNextTag(reader);
                String localName = reader.getLocalName();
                switch (localName) {
                    case XmlConstants.HEADER:
                        getHeader(reader);
                        break;
                    case XmlConstants.MES_STRUCTURES:
                        readStructures(artefacts, reader);
                        moveToNextTag(reader);
                        break;
                    default:
                        throw new IllegalArgumentException("Structure does not support element: " + localName);
                }
            }
            return artefacts;
        } finally {
            reader.close();
        }
    }

    @Override
    @SneakyThrows
    public Artefacts read(DataLocation location) {
        final InputStream inputStream = location.inputStream();
        XMLInputFactory xmlInFact = XMLInputFactory.newInstance();
        xmlInFact.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        XMLStreamReader reader = xmlInFact.createXMLStreamReader(inputStream);
        return read(reader);
    }

    private Header getHeader(XMLStreamReader reader) throws XMLStreamException {
        return headerReader.read(reader);
    }

    private void readStructures(Artefacts artefacts, XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.MES_STRUCTURES)) {
            String localName = reader.getLocalName();

            if (!SUPPORTED_STRUCTURES.contains(localName)) {
                throw new IllegalStateException("Unknown structure type: " + localName);
            }

            Optional<? extends XmlReader<? extends MaintainableArtefact>> xmlReader = readers.stream()
                .filter(read -> localName.equals(read.getNames()))
                .findAny();

            if (xmlReader.isPresent()) {
                readStructures(reader, artefacts, xmlReader.get());
                moveToNextTag(reader);
            }
        }
    }

    private <T extends MaintainableArtefactImpl> void readStructures(XMLStreamReader reader,
                                                                     Artefacts artefacts,
                                                                     XmlReader<T> xmlReader) throws XMLStreamException, URISyntaxException {
        Set<T> maintainableArtefacts = new HashSet<>();
        while (isNotEndingTag(reader, xmlReader.getNames())) {
            moveToNextTag(reader);
            if (reader.getLocalName().equals(xmlReader.getName())) {
                maintainableArtefacts.add(xmlReader.read(reader));
                xmlReader.clean();
            }
        }
        xmlReader.setArtefacts(artefacts, maintainableArtefacts);
    }
}
