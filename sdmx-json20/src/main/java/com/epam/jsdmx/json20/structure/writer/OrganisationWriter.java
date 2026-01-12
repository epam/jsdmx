package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.jsdmx.infomodel.sdmx30.Contact;
import com.epam.jsdmx.infomodel.sdmx30.Organisation;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

@AllArgsConstructor
public class OrganisationWriter {

    private final ContactsWriter contactsWriter;
    private final NameableWriter nameableWriter;

    <T extends Organisation<?>> void writeOrganisation(JsonGenerator jsonGenerator,
                                                       List<? extends T> organisationItems,
                                                       String organisationName) throws IOException {
        if (CollectionUtils.isNotEmpty(organisationItems)) {
            Map<String, String> parentIdWithHierarchy = new HashMap<>();
            for (T organisat : organisationItems) {
                List<? extends Organisation<?>> children = organisat.getHierarchy();
                if (CollectionUtils.isNotEmpty(children)) {
                    for (Organisation<?> child : children) {
                        parentIdWithHierarchy.put(child.getId(), organisat.getId());
                    }
                }
            }

            jsonGenerator.writeFieldName(organisationName);
            jsonGenerator.writeStartArray();
            for (T organisation : organisationItems) {
                writeOrganisation(jsonGenerator, parentIdWithHierarchy, organisation);
            }
            jsonGenerator.writeEndArray();
        }
    }

    private <T extends Organisation<?>> void writeOrganisation(JsonGenerator jsonGenerator,
                                                               Map<String, String> parentIdWithHierarchy,
                                                               T organisation) throws IOException {
        if (organisation != null) {
            jsonGenerator.writeStartObject();
            nameableWriter.write(jsonGenerator, organisation);
            List<Contact> contacts = organisation.getContacts();
            contactsWriter.writeContacts(jsonGenerator, contacts);
            String id = organisation.getId();
            if (parentIdWithHierarchy.containsKey(id)) {
                jsonGenerator.writeStringField(StructureUtils.PARENT, parentIdWithHierarchy.get(id));
            }
            jsonGenerator.writeEndObject();
        }
    }
}
