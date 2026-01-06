package com.epam.jsdmx.xml21.structure.writer;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Component;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;
import com.epam.jsdmx.serializer.sdmx21.ReferenceResolver;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@RequiredArgsConstructor
public class ReferenceWriter {

    private static final String AGENCY_ID = "agencyID";
    private static final String ID = "id";
    private static final String PACKAGE = "package";
    private static final String CLASS = "class";

    private static final String CODELIST_PCKG = "codelist";
    private static final String CATEGORY_SCHEME_PCKG = "categoryscheme";
    private static final String CONCEPT_SCHEME_PCKG = "conceptscheme";
    private static final String DATASTRUCTURE_PCKG = "datastructure";
    private static final String METADATASTRUCTURE_PCKG = "metadatastructure";
    private static final String PROVISION_AGREEMENT_PCKG = "registry";

    private final ReferenceResolver referenceResolver;
    private final ReferenceAdapter referenceAdapter;

    public void writeUrn(XMLStreamWriter writer, String urn) throws XMLStreamException {
        XmlWriterUtils.writeUrn(referenceAdapter.adaptUrn(urn), writer);
    }

    public void writeUrnAttribute(XMLStreamWriter writer, String urn) throws XMLStreamException {
        writer.writeAttribute(XmlConstants.URN, referenceAdapter.adaptUrn(urn));
    }

    public void writeConceptIdentity(XMLStreamWriter writer, Component component) throws XMLStreamException {
        ArtefactReference conceptIdentity = component.getConceptIdentity();
        if (conceptIdentity != null) {
            writer.writeStartElement(XmlConstants.STR + XmlConstants.CONCEPT_IDENTITY);
            writeConceptReference(writer, conceptIdentity);
            writer.writeEndElement();
        }
    }

    public void writeConceptReference(XMLStreamWriter writer, ArtefactReference conceptIdentity) throws XMLStreamException {
        writeItemReference(writer, conceptIdentity, CONCEPT_SCHEME_PCKG, "Concept");
    }

    public void writeCodeReference(XMLStreamWriter writer, ArtefactReference concept) throws XMLStreamException {
        writeItemReference(writer, concept, CODELIST_PCKG, "Code");
    }

    public void writeCategoryReference(XMLStreamWriter writer, ArtefactReference category) throws XMLStreamException {
        writeItemReference(writer, category, CATEGORY_SCHEME_PCKG, "Category");
    }


    public void writeItemReference(XMLStreamWriter writer, ArtefactReference itemRef, String packg, String clss) throws XMLStreamException {
        final ArtefactReference resolved = referenceResolver.resolve(itemRef);

        writer.writeEmptyElement(XmlConstants.REF);
        writer.writeAttribute("maintainableParentID", itemRef.getId());
        writer.writeAttribute("maintainableParentVersion", resolved.getVersionString());
        writer.writeAttribute(AGENCY_ID, itemRef.getOrganisationId());
        writer.writeAttribute(ID, itemRef.getItemId());
        writer.writeAttribute(PACKAGE, packg);
        writer.writeAttribute(CLASS, clss);
    }

    public void writeCodelistReference(XMLStreamWriter writer, ArtefactReference ref) throws XMLStreamException {
        writeMaintainableReference(writer, ref, CODELIST_PCKG, "Codelist");
    }

    public void writeDataStructureReference(XMLStreamWriter writer, ArtefactReference ref) throws XMLStreamException {
        writeMaintainableReference(writer, ref, DATASTRUCTURE_PCKG, "DataStructure");
    }

    public void writeMetaDataStructureReference(XMLStreamWriter writer, ArtefactReference ref) throws XMLStreamException {
        writeMaintainableReference(writer, ref, METADATASTRUCTURE_PCKG, "MetadataStructure");
    }

    public void writeDataflowReference(XMLStreamWriter writer, ArtefactReference ref) throws XMLStreamException {
        writeMaintainableReference(writer, ref, DATASTRUCTURE_PCKG, "Dataflow");
    }

    public void writeMetaDataflowReference(XMLStreamWriter writer, ArtefactReference ref) throws XMLStreamException {
        writeMaintainableReference(writer, ref, METADATASTRUCTURE_PCKG, "Metadataflow");
    }

    public void writeProvisionAgreementReference(XMLStreamWriter writer, ArtefactReference ref) throws XMLStreamException {
        writeMaintainableReference(writer, ref, PROVISION_AGREEMENT_PCKG, "ProvisionAgreement");
    }

    public void writeMetaProvisionAgreementReference(XMLStreamWriter writer, ArtefactReference ref) throws XMLStreamException {
        writeMaintainableReference(writer, ref, PROVISION_AGREEMENT_PCKG, "MetadataProvisionAgreement");
    }

    public void writeMetadataSetReference(XMLStreamWriter writer, ArtefactReference ref) throws XMLStreamException {
        writeMaintainableReference(writer, ref, METADATASTRUCTURE_PCKG, "MetadataSet");
    }

    public void writeDimensionReference(XMLStreamWriter writer, String groupDimensionName) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STR + XmlConstants.DIMENSION_REFERENCE);
        writer.writeEmptyElement(XmlConstants.REF);
        writer.writeAttribute(XmlConstants.ID, groupDimensionName);
        writer.writeEndElement();
    }

    public void writeObjectReference(XMLStreamWriter writer, ArtefactReference artefact) throws XMLStreamException {
        final ArtefactReference resolved = referenceResolver.resolve(artefact);
        final String packg = getPackageFor(resolved);
        final String klass = getClassName(resolved);
        if (artefact.isItemReference()) {
            writeItemReference(writer, artefact, packg, klass);
        } else {
            writeMaintainableReference(writer, artefact, packg, klass);
        }
    }

    private String getClassName(ArtefactReference resolved) {
        final StructureClass structureClass = getStructureClass(resolved);
        if (StructureClassImpl.HIERARCHY.equals(structureClass)) {
            return "HierarchicalCodelist";
        }
        return structureClass.getSimpleName();
    }

    private StructureClass getStructureClass(ArtefactReference resolved) {
        return referenceAdapter.adaptType(resolved.getStructureClass());
    }

    private String getPackageFor(ArtefactReference ref) {
        final StructureClass structureClass = getStructureClass(ref);
        //dataflow
        //data structure
        if (StructureClassImpl.DATAFLOW.equals(structureClass)
            || StructureClassImpl.DATA_STRUCTURE.equals(structureClass)) {
            return "datastructure";
        }
        //category scheme
        //categorisation
        if (StructureClassImpl.CATEGORY_SCHEME.equals(structureClass)
            || StructureClassImpl.CATEGORY.equals(structureClass)
            || StructureClassImpl.CATEGORISATION.equals(structureClass)) {
            return "categoryscheme";
        }
        //codelist
        //hierarchical codelist
        if (StructureClassImpl.CODELIST.equals(structureClass)
            || StructureClassImpl.CODE.equals(structureClass)
            || StructureClassImpl.HIERARCHY.equals(structureClass)) {
            return "codelist";
        }
        //concept scheme
        if (StructureClassImpl.CONCEPT_SCHEME.equals(structureClass)
            || StructureClassImpl.CONCEPT.equals(structureClass)) {
            return "conceptscheme";
        }
        return "base";
    }

    private void writeMaintainableReference(XMLStreamWriter writer, ArtefactReference ref, String packg, String clss) throws XMLStreamException {
        final ArtefactReference resolved = referenceResolver.resolve(ref);

        writer.writeEmptyElement(XmlConstants.REF);
        writer.writeAttribute(AGENCY_ID, ref.getOrganisationId());
        writer.writeAttribute(ID, ref.getId());
        writer.writeAttribute("version", resolved.getVersionString());
        writer.writeAttribute(PACKAGE, packg);
        writer.writeAttribute(CLASS, clss);
    }

    /**
     * with id only
     **/
    public void writeLocalReference(XMLStreamWriter writer, String id) throws XMLStreamException {
        writer.writeEmptyElement(XmlConstants.REF);
        XmlWriterUtils.writeMandatoryAttribute(id, writer, XmlConstants.ID);
    }

    public void writeConceptRoles(XMLStreamWriter writer, List<ArtefactReference> conceptRoles) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(conceptRoles)) {
            for (ArtefactReference concept : conceptRoles) {
                if (concept != null) {
                    writer.writeStartElement(XmlConstants.STR + XmlConstants.CONCEPT_ROLE);
                    writeConceptReference(writer, concept);
                    writer.writeEndElement();
                }
            }
        }
    }

}
