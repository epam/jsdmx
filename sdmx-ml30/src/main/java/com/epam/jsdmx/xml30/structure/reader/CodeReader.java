package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Code;
import com.epam.jsdmx.infomodel.sdmx30.CodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public interface CodeReader<T extends CodeImpl> {

    default void setCodeElements(XMLStreamReader reader,
                                 T code,
                                 NameableReader nameableReader,
                                 AnnotableReader annotableReader,
                                 Map<T, String> codeWithParentId) throws XMLStreamException, URISyntaxException {
        Map<String, String> names = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();
        String parentId = null;
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.CODE, XmlConstants.GEO_FEATURE_SET_CODE, XmlConstants.GEO_GRID_CODES)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_NAME:
                    nameableReader.setNameable(reader, names);
                    break;
                case XmlConstants.COM_DESCRIPTION:
                    nameableReader.setNameable(reader, descriptions);
                    break;
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, code);
                    break;
                case XmlConstants.STRUCTURE_PARENT:
                    parentId = reader.getElementText();
                    code.setParentId(parentId);
                    break;
                default:
                    readCodeSpecialFields(reader, code, localName);
                    break;
            }
            moveToNextTag(reader);
        }
        code.setName(names.isEmpty() ? null : new InternationalString(names));
        code.setDescription(descriptions.isEmpty() ? null : new InternationalString(descriptions));
        codeWithParentId.put(code, parentId);
    }

    default void readCodeSpecialFields(XMLStreamReader reader, T code, String localName) throws XMLStreamException {
        throw new IllegalArgumentException("Code does not support " + localName);
    }

    default void setCodeAttributes(XMLStreamReader reader, T code) throws URISyntaxException {
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(code::setId);

        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            code.setUri(new URI(uri));
        }
    }

    default void setCode(XMLStreamReader reader,
                         T code,
                         NameableReader nameableReader,
                         AnnotableReader annotableReader,
                         Map<T, String> codeWithParentId) throws URISyntaxException, XMLStreamException {
        setCodeAttributes(reader, code);
        setCodeElements(reader, code, nameableReader, annotableReader, codeWithParentId);
    }

    default List<T> formCodesWithHierarchy(Map<T, String> codeWithParentId) {
        Map<String, List<T>> parentAndChildrenCodes = codeWithParentId.entrySet()
            .stream()
            .filter(cod -> cod.getValue() != null)
            .collect(Collectors.groupingBy(
                Map.Entry::getValue,
                Collectors.mapping(Map.Entry::getKey, Collectors.toList())
            ));
        Set<T> codes = codeWithParentId.keySet();
        for (Map.Entry<String, List<T>> entry : parentAndChildrenCodes.entrySet()) {
            Optional<T> parentCode = codes.stream()
                .filter(cod -> cod.getId().equals(entry.getKey()))
                .findFirst();
            parentCode.ifPresent(code -> {
                List<Code> value = entry.getValue().stream()
                    .map(Code.class::cast)
                    .collect(Collectors.toList());
                code.setHierarchy(value);
            });
        }
        return new ArrayList<>(codes);
    }
}
