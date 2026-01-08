package com.epam.jsdmx.xml21.structure.writer;

import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Annotation;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CodingFormat;
import com.epam.jsdmx.infomodel.sdmx30.CrossReference;
import com.epam.jsdmx.infomodel.sdmx30.DeepEqualsExclusion;
import com.epam.jsdmx.infomodel.sdmx30.Facet;
import com.epam.jsdmx.infomodel.sdmx30.HierarchicalCode;
import com.epam.jsdmx.infomodel.sdmx30.Hierarchy;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.Level;
import com.epam.jsdmx.infomodel.sdmx30.Link;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;
import com.epam.jsdmx.infomodel.sdmx30.Version;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;


public class HierarchicalCodelistWriter extends XmlWriter<Hierarchy> {

    private final ReferenceWriter referenceWriter;

    public HierarchicalCodelistWriter(NameableWriter nameableWriter,
                                      AnnotableWriter annotableWriter,
                                      CommonAttributesWriter commonAttributesWriter,
                                      ReferenceWriter referenceWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter);
        this.referenceWriter = referenceWriter;
    }

    @Override
    protected void writeAttributes(Hierarchy hierarchy, XMLStreamWriter writer) throws XMLStreamException {
        commonAttributesWriter.writeAttributes(new HierarchyUrnAdapterWrapper(hierarchy), writer);
    }

    @Override
    protected void writeCustomAttributeElements(Hierarchy hierarchy, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(hierarchy.getCodes()) || hierarchy.getLevel() != null) {
            writer.writeStartElement(XmlConstants.STR + XmlConstants.HIERARCHY);
            writer.writeAttribute(XmlConstants.ID, XmlConstants.HIERARCHY);
            XmlWriterUtils.writeInternationalString(hierarchy.getName(), writer, XmlConstants.COMMON + XmlConstants.NAME);
            writeHierarchicalCode(hierarchy.getCodes(), writer);
            writeLevel(hierarchy.getLevel(), writer);
            writer.writeEndElement();
        }
    }

    @Override
    protected String getName(Hierarchy unused) {
        return XmlConstants.HIERARCHAL_CODELIST;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.HIERARCHICAL_CODELISTS;
    }

    @Override
    protected Set<Hierarchy> extractArtefacts(Artefacts artefacts) {
        return artefacts.getHierarchies();
    }

    private void writeHierarchicalCode(List<HierarchicalCode> codes, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(codes)) {
            for (HierarchicalCode code : codes) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.HIERARCHICAL_CODE);
                writeHierarchicalCode(writer, code);
                writer.writeEndElement();
            }
        }
    }

    private void writeHierarchicalCode(XMLStreamWriter writer, HierarchicalCode code) throws XMLStreamException {

        XmlWriterUtils.writeUri(code.getUri(), writer);

        if (code.getContainer() != null) {
            XmlWriterUtils.writeUrn(code.getUrn(), writer);
        }

        XmlWriterUtils.writeInstant(code.getValidTo(), writer, XmlConstants.VALID_TO);
        XmlWriterUtils.writeInstant(code.getValidFrom(), writer, XmlConstants.VALID_FROM);

        XmlWriterUtils.writeMandatoryAttribute(code.getId(), writer, XmlConstants.ID);

        annotableWriter.write(code, writer);
        ArtefactReference codeRef = code.getCode();
        if (codeRef != null) {
            writer.writeStartElement(XmlConstants.STR + XmlConstants.CODE);
            referenceWriter.writeCodeReference(writer, codeRef);
            writer.writeEndElement();
        }

        String levelId = code.getLevelId();
        if (levelId != null) {
            writer.writeStartElement(XmlConstants.STR + XmlConstants.LEVEL);
            referenceWriter.writeLocalReference(writer, levelId);
            writer.writeEndElement();
        }

        List<HierarchicalCode> hierarchicalCodes = code.getHierarchicalCodes();
        if (CollectionUtils.isNotEmpty(hierarchicalCodes)) {
            writeHierarchicalCode(hierarchicalCodes, writer);
        }
    }

    private void writeLevel(Level level, XMLStreamWriter writer) throws XMLStreamException {
        if (level != null) {
            writer.writeStartElement(XmlConstants.STR + XmlConstants.LEVEL);
            writer.writeAttribute(XmlConstants.ID, level.getId());

            if (level.getContainer() != null) {
                writer.writeAttribute(XmlConstants.URN, level.getUrn());
            }

            URI uri = level.getUri();
            if (uri != null) {
                writer.writeAttribute(XmlConstants.URI, uri.toString());
            }
            annotableWriter.write(level, writer);
            nameableWriter.write(level, writer);
            List<CodingFormat> codeFormat = level.getCodeFormat();
            if (CollectionUtils.isNotEmpty(codeFormat)) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.CODING_FORMAT);
                for (CodingFormat codingFormat : codeFormat) {
                    Facet facet = codingFormat.getCodingFormat();
                    if (facet != null && facet.getType() != null) {
                        String value = XmlConstants.MAP_FACET_TYPE.get(facet.getType().name());
                        writer.writeAttribute(value, facet.getValue());
                    }
                }
                writer.writeEndElement();
            }
            Level child = level.getChild();
            if (child != null && child.getId() != null) {
                writeLevel(child, writer);
            }
            writer.writeEndElement();
        }
    }

    @RequiredArgsConstructor
    private static class HierarchyUrnAdapterWrapper implements Hierarchy {

        private final Hierarchy adapted;

        @Override
        public String getUrn() {
            return "urn:sdmx:org.sdmx.infomodel.codelist.HierarchicalCodelist="
                + adapted.getOrganizationId()
                + ":" + adapted.getId()
                + "(" + adapted.getVersion() + ")";
        }


        // unsupported
        @Override
        public boolean deepEquals(Object o) {
            throw new UnsupportedOperationException("Unexpected usage of this class");
        }

        @Override
        public boolean deepEquals(Object o, Set<DeepEqualsExclusion> set) {
            throw new UnsupportedOperationException("Unexpected usage of this class");
        }

        // just delegate the rest of the methods to the adapted object
        @Override
        public boolean isExternalReference() {
            return adapted.isExternalReference();
        }

        @Override
        public URL getServiceUrl() {
            return adapted.getServiceUrl();
        }

        @Override
        public URL getStructureUrl() {
            return adapted.getStructureUrl();
        }

        @Override
        public Set<CrossReference> getReferencedArtefacts() {
            return adapted.getReferencedArtefacts();
        }

        @Override
        public ArtefactReference getMaintainer() {
            return adapted.getMaintainer();
        }

        @Override
        public String getOrganizationId() {
            return adapted.getOrganizationId();
        }

        @Override
        public Hierarchy toStub() {
            return adapted.toStub();
        }

        @Override
        public Hierarchy toCompleteStub() {
            return adapted.toCompleteStub();
        }

        @Override
        public ArtefactReference toReference() {
            return adapted.toReference();
        }

        @Override
        public boolean isStub() {
            return adapted.isStub();
        }

        @Override
        public List<Link> getLinks() {
            return adapted.getLinks();
        }

        @Override
        public boolean isHasFormalLevels() {
            return adapted.isHasFormalLevels();
        }

        @Override
        public Level getLevel() {
            return adapted.getLevel();
        }

        @Override
        public List<HierarchicalCode> getCodes() {
            return adapted.getCodes();
        }

        @Override
        public Version getVersion() {
            return adapted.getVersion();
        }

        @Override
        public Instant getValidFrom() {
            return adapted.getValidFrom();
        }

        @Override
        public Instant getValidTo() {
            return adapted.getValidTo();
        }

        @Override
        public String getValidFromString() {
            return adapted.getValidFromString();
        }

        @Override
        public String getValidToString() {
            return adapted.getValidToString();
        }

        @Override
        public InternationalString getName() {
            return adapted.getName();
        }

        @Override
        public InternationalString getDescription() {
            return adapted.getDescription();
        }

        @Override
        public String getNameInDefaultLocale() {
            return adapted.getNameInDefaultLocale();
        }

        @Override
        public String getDescriptionInDefaultLocale() {
            return adapted.getDescriptionInDefaultLocale();
        }

        @Override
        public String getId() {
            return adapted.getId();
        }

        @Override
        public StructureClass getStructureClass() {
            return adapted.getStructureClass();
        }

        @Override
        public URI getUri() {
            return adapted.getUri();
        }

        @Override
        public ArtefactReference getContainer() {
            return adapted.getContainer();
        }

        @Override
        public List<Annotation> getAnnotations() {
            return adapted.getAnnotations();
        }
    }
}