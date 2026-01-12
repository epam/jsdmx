package com.epam.jsdmx.xml21.structure.writer;

import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.InternationalUri;
import com.epam.jsdmx.infomodel.sdmx30.Version;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

public final class XmlWriterUtils {

    private XmlWriterUtils() {
    }

    public static void writeArtefactReference(XMLStreamWriter writer, String artefactReferenceName, String reference) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STR + artefactReferenceName);
        writeCharacters(reference, writer);
        writer.writeEndElement();
    }

    public static void writeInternationalString(InternationalString internationalString, XMLStreamWriter writer, String localName) throws XMLStreamException {
        if (internationalString != null) {
            Map<String, String> stringsWithLocales = internationalString.getAll();
            for (Map.Entry<String, String> stringLocale : stringsWithLocales.entrySet()) {
                if (stringLocale != null) {
                    writer.writeStartElement(localName);
                    writer.writeAttribute(XmlConstants.XML_LANG, stringLocale.getKey());
                    String value = stringLocale.getValue();
                    writeCharacters(value, writer);
                    writer.writeEndElement();
                }
            }
        }
    }

    public static void writeCharacters(String value, XMLStreamWriter writer) throws XMLStreamException {
        try {
            writer.writeCharacters(Objects.requireNonNullElse(value, Strings.EMPTY));
        } catch (XMLStreamException e) {
            if (StringUtils.contains(e.getMessage(), "Invalid white space character (")) {
                writer.writeCharacters(removeInvalidChars(value));
            } else {
                throw e;
            }
        }
    }

    private static String removeInvalidChars(String value) {
        return value.replaceAll("[\\x00-\\x1F\\x7F-\\x9F]", "");
    }

    public static void writeInternationalUri(InternationalUri internationalUri, XMLStreamWriter writer, String localName) throws XMLStreamException {
        if (internationalUri != null) {
            Map<String, URI> stringsWithLocales = internationalUri.getAll();
            for (Map.Entry<String, URI> stringLocale : stringsWithLocales.entrySet()) {
                if (stringLocale != null) {
                    writer.writeStartElement(localName);
                    writer.writeAttribute(XmlConstants.XML_LANG, stringLocale.getKey());
                    URI value = stringLocale.getValue();
                    writeCharacters(value.toString(), writer);
                    writer.writeEndElement();
                }
            }
        }
    }

    public static void writeVersion(Version version, XMLStreamWriter writer) throws XMLStreamException {
        if (version != null) {
            writer.writeAttribute(XmlConstants.VERSION, version.getValue());
        }
    }

    public static void writeUrn(String urn, XMLStreamWriter writer) throws XMLStreamException {
        if (urn != null) {
            writer.writeAttribute(XmlConstants.URN, urn);
        }
    }

    public static void writeUri(URI uri, XMLStreamWriter writer) throws XMLStreamException {
        if (uri != null) {
            writer.writeAttribute(XmlConstants.URI, uri.toString());
        }
    }

    public static void writeUrl(URL url, XMLStreamWriter writer, String urlName) throws XMLStreamException {
        if (url != null) {
            writer.writeAttribute(urlName, url.toString());
        }
    }

    public static void writeInstant(Instant instant, XMLStreamWriter writer, String instanceName) throws XMLStreamException {
        if (instant != null) {
            writer.writeAttribute(instanceName, instant.toString());
        }
    }

    public static void writeMandatoryAttribute(String attribute, XMLStreamWriter writer, String attributeName) throws XMLStreamException {
        if (attribute != null) {
            writer.writeAttribute(attributeName, attribute);
        } else {
            throw new IllegalArgumentException(attributeName + XmlConstants.CANNOT_BE_NULL);
        }
    }

    public static void writeAttributeIfNotNull(String attribute, XMLStreamWriter writer, String attributeName) throws XMLStreamException {
        if (attribute != null) {
            writer.writeAttribute(attributeName, attribute);
        }
    }

    public static void writeIdUriAttributes(XMLStreamWriter writer,
                                            String id,
                                            URI uri) throws XMLStreamException {
        writeMandatoryAttribute(id, writer, XmlConstants.ID);
        writeUri(uri, writer);
    }

    public static String instantToDateString(Instant time) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.systemDefault());
        if (time != null) {
            return formatter.format(time);
        }
        return null;
    }

    public static void writeIsFinal(XMLStreamWriter writer, Version version) throws XMLStreamException {
        if (version != null) {
            writer.writeAttribute("isFinal", Boolean.toString(version.isStable()));
        }
    }
}
