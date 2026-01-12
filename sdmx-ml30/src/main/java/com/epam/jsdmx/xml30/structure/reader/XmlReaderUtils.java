package com.epam.jsdmx.xml30.structure.reader;

import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.ComponentImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactImpl;
import com.epam.jsdmx.infomodel.sdmx30.Party;
import com.epam.jsdmx.infomodel.sdmx30.Version;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

public final class XmlReaderUtils {

    private XmlReaderUtils() {
    }

    public static int moveToNextTag(XMLStreamReader streamReader) throws XMLStreamException {
        int event = streamReader.next();

        while (event != XMLStreamConstants.START_ELEMENT && event != XMLStreamConstants.END_ELEMENT) {
            event = streamReader.next();
        }
        return event;
    }

    public static void getPartyNames(XMLStreamReader reader, Party party) throws XMLStreamException {
        if (reader.getEventType() == XMLStreamConstants.END_ELEMENT) {
            party.setName(new InternationalString(new HashMap<>()));
            return;
        }

        Map<String, String> names = new HashMap<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.SENDER, XmlConstants.RECEIVER)) {
            if (!reader.getLocalName().equals(XmlConstants.NAME)) {
                return;
            }

            String locale = reader.getAttributeValue(XmlConstants.XML_1998_NAMESPACE, XmlConstants.LANG);
            String text = reader.getElementText();
            names.put(locale, text);
            moveToNextTag(reader);
        }

        party.setName(new InternationalString(names));
    }

    public static boolean isEndingTag(XMLStreamReader reader, String... names) {
        if (reader.getEventType() != XMLStreamConstants.END_ELEMENT) {
            return false;
        }

        return names.length == 0 || Arrays.stream(names).anyMatch(name -> name.equals(reader.getLocalName()));
    }

    public static boolean isNotEndingTag(XMLStreamReader reader, String... names) {
        return !isEndingTag(reader, names);
    }

    public static void setCommonAttributes(XMLStreamReader reader, MaintainableArtefactImpl artefact) {

        setUri(reader, artefact);

        setStructureUrl(reader, artefact);

        setServiceUrl(reader, artefact);

        setId(reader, artefact);

        setAgencyId(reader, artefact);

        setVersion(reader, artefact);

        setExternalReference(reader, artefact);

        setValidTo(reader, artefact);

        setValidFrom(reader, artefact);

    }

    public static void setValidFrom(XMLStreamReader reader, MaintainableArtefactImpl artefact) {
        String validFrom = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALID_FROM);
        if (validFrom != null) {
            artefact.setValidFrom(validFrom);
        }
    }

    public static void setValidTo(XMLStreamReader reader, MaintainableArtefactImpl artefact) {
        String validTo = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALID_TO);
        if (validTo != null) {
            artefact.setValidTo(validTo);
        }
    }

    @SneakyThrows
    public static void setStructureUrl(XMLStreamReader reader, MaintainableArtefactImpl artefact) {
        String structureUrl = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.STRUCTURE_URL);
        if (structureUrl != null) {
            artefact.setStructureUrl(new URL(structureUrl));
        }
    }

    @SneakyThrows
    public static void setUri(XMLStreamReader reader, MaintainableArtefactImpl artefact) {
        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            artefact.setUri(new URI(uri));
        }
    }

    public static void setExternalReference(XMLStreamReader reader, MaintainableArtefactImpl artefact) {
        String isExternalReference = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_EXTERNAL_REFERENCE);
        if (isExternalReference != null) {
            artefact.setExternalReference(Boolean.parseBoolean(isExternalReference));
        }
    }

    public static void setVersion(XMLStreamReader reader, MaintainableArtefactImpl artefact) {
        String version = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VERSION);
        if (version == null) {
            throw new IllegalArgumentException("version of " + artefact + XmlConstants.CANNOT_BE_NULL);
        }
        artefact.setVersion(Version.createFromString(version));
    }

    public static void setAgencyId(XMLStreamReader reader, MaintainableArtefactImpl artefact) {
        String agencyID = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.AGENCY_ID);
        if (agencyID == null) {
            throw new IllegalArgumentException("agencyID of " + artefact + XmlConstants.CANNOT_BE_NULL);
        }
        artefact.setOrganizationId(agencyID);
    }

    public static void setId(XMLStreamReader reader, MaintainableArtefactImpl artefact) {
        String id = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID);
        if (id == null) {
            throw new IllegalArgumentException("id of " + artefact + XmlConstants.CANNOT_BE_NULL);
        }
        artefact.setId(id);
    }

    @SneakyThrows
    public static void setServiceUrl(XMLStreamReader reader, MaintainableArtefactImpl artefact) {
        String serviceUrl = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.SERVICE_URL);
        if (serviceUrl != null) {
            artefact.setServiceUrl(new URL(serviceUrl));
        }
    }

    public static void readConceptIdentity(XMLStreamReader reader, ComponentImpl attribute) throws XMLStreamException {
        Optional.ofNullable(reader.getElementText())
            .map(String::trim)
            .map(IdentifiableArtefactReferenceImpl::new)
            .ifPresent(attribute::setConceptIdentity);
    }

    public static void readConceptRole(XMLStreamReader reader, List<ArtefactReference> conceptRoles) throws XMLStreamException {
        Optional.ofNullable(reader.getElementText())
            .map(IdentifiableArtefactReferenceImpl::new)
            .ifPresent(conceptRoles::add);
    }

    public static Instant getDate(XMLStreamReader reader, String attributeName) {
        return Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, attributeName))
            .map(LocalDate::parse)
            .map(XmlReaderUtils::convertToInstant)
            .orElse(null);
    }

    public static Instant convertToInstant(LocalDate date) {
        return date == null ? null : date.atStartOfDay().toInstant(ZoneOffset.UTC);
    }

    public static boolean isNotEmptyOrNullElementText(String text) {
        return StringUtils.isNotBlank(text);
    }

    public static Instant getInstantFromYearString(String yearString) {
        return Instant.from((new DateTimeFormatterBuilder().appendPattern(XmlConstants.YEAR_PATTERN)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_DAY, 0)
            .parseDefaulting(ChronoField.SECOND_OF_DAY, 0)
            .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
            .toFormatter()
            .withZone(ZoneId.of("UTC"))).parse(yearString));
    }

    public static Boolean getIncluded(XMLStreamReader reader) {
        String included = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.INCLUDE);
        return Boolean.parseBoolean(included);
    }

    public static Boolean getRemovePrefix(XMLStreamReader reader) {
        String removePrefix = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.REMOVE_PREFIX);
        return Boolean.parseBoolean(removePrefix);
    }

    public static String getId(XMLStreamReader reader) {
        return reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID);
    }
}
