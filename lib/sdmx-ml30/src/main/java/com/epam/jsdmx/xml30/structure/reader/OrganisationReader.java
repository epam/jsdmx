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

import com.epam.jsdmx.infomodel.sdmx30.Contact;
import com.epam.jsdmx.infomodel.sdmx30.ContactImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
public class OrganisationReader {

    private final AnnotableReader annotableReader;
    private final NameableReader nameableReader;

    public <T extends OrganisationImpl<?>> T addOrganisation(XMLStreamReader reader,
                                                             T organisation,
                                                             Map<T, String> organisationWithParentId) throws URISyntaxException, XMLStreamException {
        String parentId = null;

        Optional.ofNullable(XmlReaderUtils.getId(reader))
            .ifPresent(organisation::setId);

        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            organisation.setUri(new URI(uri));
        }

        Map<String, String> names = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();
        List<Contact> contacts = new ArrayList<>();

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.DATA_CONSUMER, XmlConstants.AGENCY,
            XmlConstants.DATA_PROVIDER, XmlConstants.METADATA_PROVIDER, XmlConstants.ORGANISATION_UNIT)) {
            String name = reader.getLocalName();
            switch (name) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, organisation);
                    break;
                case XmlConstants.COM_NAME:
                    nameableReader.setNameable(reader, names);
                    break;
                case XmlConstants.COM_DESCRIPTION:
                    nameableReader.setNameable(reader, descriptions);
                    break;
                case XmlConstants.CONTACT:
                    ContactImpl contact = getContact(reader);
                    contacts.add(contact);
                    break;
                case XmlConstants.STRUCTURE_PARENT:
                    parentId = reader.getElementText();
                    break;
                default:
                    throw new IllegalArgumentException("Organisation " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);

            }
            moveToNextTag(reader);
        }
        organisation.setName(names.isEmpty() ? null : new InternationalString(names));
        organisation.setDescription(descriptions.isEmpty() ? null : new InternationalString(descriptions));
        organisation.setContacts(contacts);
        if (organisationWithParentId != null) {
            organisationWithParentId.put(organisation, parentId);
        }
        return organisation;
    }

    private ContactImpl getContact(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        var contact = new ContactImpl();

        Map<String, String> roles = new HashMap<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.CONTACT)) {
            String name = reader.getLocalName();
            switch (name) {
                case XmlConstants.NAME:
                    String contactName = reader.getElementText();
                    setContractName(contact, contactName);
                    break;
                case XmlConstants.DEPARTMENT:
                    String department = reader.getElementText();
                    setDepartment(contact, department);
                    break;
                case XmlConstants.ROLE:
                    nameableReader.setNameable(reader, roles);
                    break;
                case XmlConstants.TELEPHONE:
                    String telephone = reader.getElementText();
                    setTelephone(contact, telephone);
                    break;
                case XmlConstants.FAX:
                    String fax = reader.getElementText();
                    setFax(contact, fax);
                    break;
                case XmlConstants.X_400:
                    String x400 = reader.getElementText();
                    setX400(contact, x400);
                    break;
                case XmlConstants.URI:
                    String uri = reader.getElementText();
                    setUri(contact, uri);
                    break;
                case XmlConstants.EMAIL:
                    String email = reader.getElementText();
                    setEmail(contact, email);
                    break;
                default:
                    throw new IllegalArgumentException("Contact " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
            }
            moveToNextTag(reader);
        }
        contact.setResponsibility(roles.isEmpty() ? null : new InternationalString(roles));
        return contact;
    }

    private void setFax(ContactImpl contact, String fax) {
        if (XmlReaderUtils.isNotEmptyOrNullElementText(fax)) {
            contact.setFax(fax);
        }
    }

    private void setX400(ContactImpl contact, String x400) {
        if (XmlReaderUtils.isNotEmptyOrNullElementText(x400)) {
            contact.setX400(x400);
        }
    }

    private void setUri(ContactImpl contact, String uri) throws URISyntaxException {
        if (XmlReaderUtils.isNotEmptyOrNullElementText(uri)) {
            contact.setUri(new URI(uri));
        }
    }

    private void setEmail(ContactImpl contact, String email) {
        if (XmlReaderUtils.isNotEmptyOrNullElementText(email)) {
            contact.setEmail(email);
        }
    }

    private void setTelephone(ContactImpl contact, String telephone) {
        if (XmlReaderUtils.isNotEmptyOrNullElementText(telephone)) {
            contact.setTelephone(telephone);
        }
    }

    private void setDepartment(ContactImpl contact, String department) {
        if (XmlReaderUtils.isNotEmptyOrNullElementText(department)) {
            contact.setOrganizationUnit(department);
        }
    }

    private void setContractName(ContactImpl contact, String contactName) {
        if (XmlReaderUtils.isNotEmptyOrNullElementText(contactName)) {
            contact.setName(contactName);
        }
    }

    public <T extends OrganisationImpl<?>> Map<T, List<T>> formHierarchy(Map<T, String> dataConsumerWithParent) {
        Map<T, List<T>> parentChildren = new HashMap<>();
        Set<T> consumers = dataConsumerWithParent.keySet();
        Map<String, T> consumerWithId = consumers.stream()
            .collect(Collectors.toMap(
                IdentifiableArtefactImpl::getId,
                consumer -> consumer
            ));
        for (Map.Entry<T, String> data : dataConsumerWithParent.entrySet()) {
            String value = data.getValue();
            if (value != null) {
                T dataConsumer = consumerWithId.get(value);
                if (parentChildren.containsKey(dataConsumer)) {
                    parentChildren.get(dataConsumer).add(data.getKey());
                } else {
                    parentChildren.put(dataConsumer, List.of(data.getKey()));
                }
            }
        }
        return parentChildren;
    }
}
