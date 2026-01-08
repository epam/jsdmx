package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.Contact;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactImpl;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class OrganisationReader {

    private final ContactReader contactReader;
    private final NameableReader nameableReader;

    public OrganisationReader(ContactReader contactReader,
                              NameableReader nameableReader) {
        this.contactReader = contactReader;
        this.nameableReader = nameableReader;
    }

    public <T extends OrganisationImpl<?>> T getOrganisation(JsonParser parser,
                                                             T organisation,
                                                             Map<T, String> organisationWithParentId) {
        try {
            String parentId = null;
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.CONTACTS:
                        parser.nextToken();
                        organisation.setContacts(ReaderUtils.getArray(parser, (this::getContact)));
                        break;
                    case StructureUtils.PARENT:
                        parentId = ReaderUtils.getStringJsonField(parser);
                        break;
                    default:
                        nameableReader.read(organisation, parser);
                }
            }
            if (organisationWithParentId != null) {
                organisationWithParentId.put(organisation, parentId);
            }
            return organisation;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
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

    private Contact getContact(JsonParser parser) {
        return contactReader.getContact(parser);
    }
}
