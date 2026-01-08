package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyAssociation;

import com.fasterxml.jackson.core.JsonGenerator;

public class HierarchyAssociationWriter extends MaintainableWriter<HierarchyAssociation> {

    public HierarchyAssociationWriter(VersionableWriter versionableWriter,
                                      LinksWriter linksWriter) {
        super(versionableWriter, linksWriter);
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, HierarchyAssociation hierarchyAssociation) throws IOException {
        super.writeFields(jsonGenerator, hierarchyAssociation);

        ArtefactReference contextObject = hierarchyAssociation.getContextObject();
        if (contextObject != null) {
            jsonGenerator.writeStringField(StructureUtils.CONTEXT_OBJECT, contextObject.getUrn());
        }

        ArtefactReference linkedHierarchy = hierarchyAssociation.getLinkedHierarchy();
        if (linkedHierarchy != null) {
            jsonGenerator.writeStringField(StructureUtils.LINKED_HIERARCHY, linkedHierarchy.getUrn());
        }

        ArtefactReference linkedObject = hierarchyAssociation.getLinkedObject();
        if (linkedObject != null) {
            jsonGenerator.writeStringField(StructureUtils.LINKED_OBJECT, linkedObject.getUrn());
        }
    }

    @Override
    protected Set<HierarchyAssociation> extractArtefacts(Artefacts artefacts) {
        return artefacts.getHierarchyAssociations();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.HIERARCHY_ASSOCIATIONS;
    }
}
