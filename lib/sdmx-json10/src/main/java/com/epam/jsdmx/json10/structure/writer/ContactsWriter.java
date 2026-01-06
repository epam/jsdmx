package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Contact;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections4.CollectionUtils;

public class ContactsWriter {

    public void writeContacts(JsonGenerator jsonGenerator, List<Contact> contacts) throws IOException {
        if (CollectionUtils.isNotEmpty(contacts)) {
            jsonGenerator.writeFieldName(StructureUtils.CONTACTS);
            jsonGenerator.writeStartArray();
            for (Contact contact : contacts) {
                if (contact != null) {
                    jsonGenerator.writeStartObject();

                    String name = contact.getName();
                    if (name != null) {
                        jsonGenerator.writeStringField(StructureUtils.NAME, name);
                    }

                    String organizationUnit = contact.getOrganizationUnit();
                    writeOrganisationUnit(jsonGenerator, organizationUnit);

                    InternationalString responsibility = contact.getResponsibility();
                    StructureUtils.writeInternationalString(jsonGenerator, responsibility, StructureUtils.ROLE);

                    String telephone = contact.getTelephone();
                    writeOneStringArray(jsonGenerator, telephone, StructureUtils.TELEPHONES);

                    String fax = contact.getFax();
                    writeOneStringArray(jsonGenerator, fax, StructureUtils.FAXES);

                    String x400 = contact.getX400();
                    writeOneStringArray(jsonGenerator, x400, StructureUtils.X_400_S);

                    URI uri = contact.getUri();
                    writeURI(jsonGenerator, uri);

                    String email = contact.getEmail();
                    writeOneStringArray(jsonGenerator, email, StructureUtils.EMAILS);

                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }

    private void writeOrganisationUnit(JsonGenerator jsonGenerator, String organizationUnit) throws IOException {
        if (organizationUnit != null) {
            jsonGenerator.writeStringField(StructureUtils.DEPARTMENT, organizationUnit);
        }
    }

    private void writeURI(JsonGenerator jsonGenerator, URI uri) throws IOException {
        if (uri != null) {
            writeOneStringArray(jsonGenerator, uri.toString(), StructureUtils.URIS);
        }
    }

    private void writeOneStringArray(JsonGenerator jsonGenerator, String parameter, String parameterName) throws IOException {
        jsonGenerator.writeFieldName(parameterName);
        jsonGenerator.writeStartArray();
        if (parameter != null) {
            jsonGenerator.writeString(parameter);
        }
        jsonGenerator.writeEndArray();
    }
}
