package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.HierarchyAssociationImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;

public class HierarchyAssociationReader extends MaintainableReader<HierarchyAssociationImpl> {

    public HierarchyAssociationReader(VersionableReader versionableReader) {
        super(versionableReader);
    }

    @Override
    protected HierarchyAssociationImpl createMaintainableArtefact() {
        return new HierarchyAssociationImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, HierarchyAssociationImpl hierarchyAssociation) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.LINKED_HIERARCHY:
                String linkedHierarchy = ReaderUtils.getStringJsonField(parser);
                if (linkedHierarchy != null) {
                    hierarchyAssociation.setLinkedHierarchy(new MaintainableArtefactReference(linkedHierarchy));
                }
                break;
            case StructureUtils.CONTEXT_OBJECT:
                String contextObject = ReaderUtils.getStringJsonField(parser);
                if (contextObject != null) {
                    hierarchyAssociation.setContextObject(new MaintainableArtefactReference(contextObject));
                }
                break;
            case StructureUtils.LINKED_OBJECT:
                String linkedObject = ReaderUtils.getStringJsonField(parser);
                if (linkedObject != null) {
                    hierarchyAssociation.setLinkedObject(new MaintainableArtefactReference(linkedObject));
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "HierarchyAssociation: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.HIERARCHY_ASSOCIATIONS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<HierarchyAssociationImpl> artefacts) {
        artefact.getHierarchyAssociations().addAll(artefacts);
    }
}
