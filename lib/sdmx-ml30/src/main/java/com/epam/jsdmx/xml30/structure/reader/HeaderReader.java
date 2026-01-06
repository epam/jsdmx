package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Party;
import com.epam.jsdmx.serializer.sdmx30.common.Header;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class HeaderReader {

    public static final String ID = "ID";
    public static final String TEST = "Test";
    public static final String PREPARED = "Prepared";
    public static final String SENDER = "Sender";
    public static final String RECEIVER = "Receiver";

    public Header read(XMLStreamReader reader) throws XMLStreamException {
        var header = new Header();

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.HEADER)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case ID:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(header::setId);
                    break;
                case TEST:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(test -> header.setTest(Boolean.parseBoolean(test)));
                    break;
                case PREPARED:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(prepared -> header.setPrepared(Instant.parse(prepared)));
                    break;
                case SENDER:
                    var party = new Party();
                    String sender = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID);
                    party.setId(sender);
                    XmlReaderUtils.getPartyNames(reader, party);
                    header.setSender(party);
                    break;
                case RECEIVER:
                    var partyReceiver = new Party();
                    String receiver = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID);
                    partyReceiver.setId(receiver);
                    XmlReaderUtils.getPartyNames(reader, partyReceiver);
                    header.setReceivers(List.of(partyReceiver));
                    break;
                default:
                    throw new IllegalArgumentException("Header " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        return header;
    }
}
