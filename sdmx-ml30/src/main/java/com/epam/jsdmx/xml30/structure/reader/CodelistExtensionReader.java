package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.CascadeValue;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtensionImpl;
import com.epam.jsdmx.infomodel.sdmx30.ExclusiveCodeSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.InclusiveCodeSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.MemberValue;
import com.epam.jsdmx.infomodel.sdmx30.MemberValueImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class CodelistExtensionReader {

    public CodelistExtensionImpl getCodelistExtension(XMLStreamReader reader) throws XMLStreamException {
        moveToNextTag(reader);
        var codelistExtension = new CodelistExtensionImpl();
        while (isNotEndingTag(reader, XmlConstants.CODELIST_EXTENSION)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.INCLUSIVE_CODE_SELECTION:
                    var inclusiveCodeSelection = new InclusiveCodeSelectionImpl();
                    setMemberValues(inclusiveCodeSelection, reader);
                    codelistExtension.setCodeSelection(inclusiveCodeSelection);
                    break;
                case XmlConstants.EXCLUSIVE_CODE_SELECTION:
                    var exclusiveCodeSelection = new ExclusiveCodeSelectionImpl();
                    setMemberValues(exclusiveCodeSelection, reader);
                    codelistExtension.setCodeSelection(exclusiveCodeSelection);
                    break;
                case XmlConstants.CODELIST:
                    String clText = reader.getElementText();
                    codelistExtension.setCodelist(new MaintainableArtefactReference(clText));
                    break;
                default:
                    throw new IllegalArgumentException("CodelistExtension " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        return codelistExtension;
    }

    private void setMemberValues(ExclusiveCodeSelectionImpl exclusiveCodeSelection, XMLStreamReader reader) throws XMLStreamException {
        List<MemberValue> memberValues = getMemberValues(reader);
        exclusiveCodeSelection.setMembers(memberValues);
    }

    private void setMemberValues(InclusiveCodeSelectionImpl inclusiveCodeSelection, XMLStreamReader reader) throws XMLStreamException {
        List<MemberValue> memberValues = getMemberValues(reader);
        inclusiveCodeSelection.setMembers(memberValues);
    }

    private List<MemberValue> getMemberValues(XMLStreamReader reader) throws XMLStreamException {
        List<MemberValue> memberValues = new ArrayList<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.INCLUSIVE_CODE_SELECTION, XmlConstants.EXCLUSIVE_CODE_SELECTION)) {
            String localName = reader.getLocalName();
            if (XmlConstants.MEMBER_VALUE.equals(localName)) {
                var memberValue = new MemberValueImpl();
                String cascadeValues = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.CASCADE_VALUES);
                memberValue.setCascadeValue(CascadeValue.valueOf(cascadeValues.toUpperCase()));
                String value = reader.getElementText();
                if (XmlReaderUtils.isNotEmptyOrNullElementText(value)) {
                    memberValue.setValue(value);
                }
                memberValues.add(memberValue);
            }
            moveToNextTag(reader);
        }
        return memberValues;
    }
}
