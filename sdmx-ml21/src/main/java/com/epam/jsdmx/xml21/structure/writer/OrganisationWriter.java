package com.epam.jsdmx.xml21.structure.writer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Contact;
import com.epam.jsdmx.infomodel.sdmx30.Organisation;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@AllArgsConstructor
public class OrganisationWriter {

    private final ContactWriter contactWriter;
    private final AnnotableWriter annotableWriter;
    private final NameableWriter nameableWriter;

    <T extends Organisation<?>> void writeOrganisation(List<? extends T> organisationItems,
                                                       XMLStreamWriter writer,
                                                       String name) throws XMLStreamException {
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
            for (T organisation : organisationItems) {
                writeOrganisation(writer, name, parentIdWithHierarchy, organisation);
            }
        }
    }

    private <T extends Organisation<?>> void writeOrganisation(XMLStreamWriter writer, String name, 
            Map<String, String> parentIdWithHierarchy, T organisation) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STR + name);
        XmlWriterUtils.writeIdUriAttributes(writer, organisation.getId(), organisation.getUri());

        if (organisation.getContainer() != null) {
            XmlWriterUtils.writeUrn(organisation.getUrn(), writer);
        }

        annotableWriter.write(organisation, writer);
        nameableWriter.write(organisation, writer);

        List<Contact> contacts = organisation.getContacts();
        contactWriter.writeContacts(contacts, writer);

        String organisationId = organisation.getId();
        if (parentIdWithHierarchy.containsKey(organisation.getId())) {
            writer.writeStartElement(XmlConstants.STR + XmlConstants.STRUCTURE_PARENT);
            writer.writeCharacters(parentIdWithHierarchy.get(organisationId));
            writer.writeEndElement();
        }

        writer.writeEndElement();
    }
}
