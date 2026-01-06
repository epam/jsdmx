package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.BaseFacetImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodingFormatImpl;
import com.epam.jsdmx.infomodel.sdmx30.FacetType;
import com.epam.jsdmx.infomodel.sdmx30.HierarchicalCode;
import com.epam.jsdmx.infomodel.sdmx30.HierarchicalCodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.LevelImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

public class HierarchyReader extends XmlReader<HierarchyImpl> {

    public HierarchyReader(AnnotableReader annotableReader,
                           NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @SneakyThrows
    private static void readCodeAttributes(XMLStreamReader reader, HierarchicalCodeImpl code) {
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(code::setId);

        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            code.setUri(new URI(uri));
        }

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALID_TO))
            .map(Instant::parse)
            .ifPresent(code::setValidTo);

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALID_FROM))
            .map(Instant::parse)
            .ifPresent(code::setValidFrom);
    }

    @Override
    protected HierarchyImpl createMaintainableArtefact() {
        return new HierarchyImpl();
    }

    @Override
    protected void read(XMLStreamReader reader, HierarchyImpl hierarchy) throws URISyntaxException, XMLStreamException {
        List<HierarchicalCode> codes = new ArrayList<>();
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.LEVEL:
                hierarchy.setLevel(getLevel(reader));
                break;
            case XmlConstants.HIERARCHICAL_CODE:
                HierarchicalCode code = addHierarchicalCode(reader);
                codes.add(code);
                break;
            default:
                throw new IllegalArgumentException("Hierarchy " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
        hierarchy.setCodes(codes);

    }

    @Override
    protected void setAttributes(XMLStreamReader reader, HierarchyImpl hierarchy) {
        XmlReaderUtils.setCommonAttributes(reader, hierarchy);
    }

    @Override
    protected String getName() {
        return XmlConstants.HIERARCHY;
    }

    @Override
    protected String getNames() {
        return XmlConstants.HIERARCHIES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<HierarchyImpl> artefacts) {
        artefact.getHierarchies().addAll(artefacts);
    }

    private HierarchicalCode addHierarchicalCode(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        var code = new HierarchicalCodeImpl();
        List<HierarchicalCode> hierarchicalCodes = new ArrayList<>();
        readCodeAttributes(reader, code);

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.HIERARCHICAL_CODE)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    this.annotableReader.setAnnotations(reader, code);
                    break;
                case XmlConstants.CODE:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .map(IdentifiableArtefactReferenceImpl::new)
                        .ifPresent(code::setCode);
                    break;
                case XmlConstants.LEVEL:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(code::setLevelId);
                    break;
                case XmlConstants.HIERARCHICAL_CODE:
                    hierarchicalCodes.add(addHierarchicalCode(reader));
                    break;
                default:
                    throw new IllegalArgumentException("HierarchicalCode " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        code.setHierarchicalCodes(hierarchicalCodes);
        return code;
    }

    @SneakyThrows
    private LevelImpl getLevel(XMLStreamReader reader) {
        var level = new LevelImpl();

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(level::setId);

        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            level.setUri(new URI(uri));
        }

        Map<String, String> names = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.LEVEL)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    this.annotableReader.setAnnotations(reader, level);
                    break;
                case XmlConstants.COM_NAME:
                    this.nameableReader.setNameable(reader, names);
                    break;
                case XmlConstants.COM_DESCRIPTION:
                    this.nameableReader.setNameable(reader, descriptions);
                    break;
                case XmlConstants.CODING_FORMAT:
                    setCodingFormat(reader, level);
                    break;
                case XmlConstants.LEVEL:
                    level.setChild(getLevel(reader));
                    break;
                default:
                    throw new IllegalArgumentException("Level " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        level.setName(names.isEmpty() ? null : new InternationalString(names));
        level.setDescription(descriptions.isEmpty() ? null : new InternationalString(descriptions));

        return level;
    }

    private void setCodingFormat(XMLStreamReader reader, LevelImpl level) throws XMLStreamException {
        if (reader.getAttributeCount() > 0) {
            QName format = reader.getAttributeName(0);
            String codeFormat = format.getLocalPart();
            String formatValue = reader.getAttributeValue(StringUtils.EMPTY, codeFormat);
            var codingFormat = new CodingFormatImpl();
            var facet = new BaseFacetImpl();
            facet.setType(FacetType.valueOf(XmlConstants.MAP_CODING_FORMAT.get(codeFormat)));
            facet.setValue(formatValue);
            codingFormat.setCodingFormat(facet);
            level.setCodeFormat(List.of(codingFormat));
        }
        moveToNextTag(reader);
    }

}
