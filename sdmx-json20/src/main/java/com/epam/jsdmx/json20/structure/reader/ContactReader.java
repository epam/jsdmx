package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getLocalizedField;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import com.epam.jsdmx.infomodel.sdmx30.Contact;
import com.epam.jsdmx.infomodel.sdmx30.ContactImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class ContactReader {

    public Contact getContact(JsonParser parser) {
        try {
            ContactImpl contact = new ContactImpl();
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.NAME:
                        String name = ReaderUtils.getFieldAsString(parser);
                        contact.setName(name);
                        break;
                    case StructureUtils.DEPARTMENT:
                        String department = ReaderUtils.getFieldAsString(parser);
                        contact.setOrganizationUnit(department);
                        break;
                    case StructureUtils.ROLE:
                        Map<String, String> roles = getLocalizedField(parser);
                        setRoles(contact, roles);
                        break;
                    case StructureUtils.TELEPHONES:
                        List<String> telephones = ReaderUtils.getListStrings(parser);
                        setTelephones(contact, telephones);
                        break;
                    case StructureUtils.FAXES:
                        List<String> faxes = ReaderUtils.getListStrings(parser);
                        setFaxes(contact, faxes);
                        break;
                    case StructureUtils.X_400_S:
                        List<String> x400 = ReaderUtils.getListStrings(parser);
                        setX400(contact, x400);
                        break;
                    case StructureUtils.EMAILS:
                        List<String> emails = ReaderUtils.getListStrings(parser);
                        setEmail(contact, emails);
                        break;
                    case StructureUtils.URIS:
                        List<String> uris = ReaderUtils.getListStrings(parser);
                        setURI(contact, uris);
                        break;
                    default:
                        throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "Contacts: " + fieldName);
                }
            }
            return contact;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void setRoles(ContactImpl contact, Map<String, String> roles) {
        if (roles != null) {
            contact.setResponsibility(new InternationalString(roles));
        }
    }

    private void setTelephones(ContactImpl contact, List<String> telephones) {
        if (CollectionUtils.isNotEmpty(telephones)) {
            contact.setTelephone(telephones.get(0));
        }
    }

    private void setFaxes(ContactImpl contact, List<String> faxes) {
        if (CollectionUtils.isNotEmpty(faxes)) {
            contact.setFax(faxes.get(0));
        }
    }

    private void setX400(ContactImpl contact, List<String> x400) {
        if (CollectionUtils.isNotEmpty(x400)) {
            contact.setX400(x400.get(0));
        }
    }

    private void setEmail(ContactImpl contact, List<String> emails) {
        if (CollectionUtils.isNotEmpty(emails)) {
            contact.setEmail(emails.get(0));
        }
    }

    private void setURI(ContactImpl contact, List<String> uris) throws URISyntaxException {
        if (CollectionUtils.isNotEmpty(uris) && uris.get(0) != null) {
            contact.setUri(new URI(uris.get(0)));
        }
    }
}
