package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.writer.XmlWriterUtils.writeCharacters;

import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProvisionAgreement;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;

public class MetadataProvisionAgreementWriter extends XmlWriter<MetadataProvisionAgreement> {

    public MetadataProvisionAgreementWriter(NameableWriter nameableWriter,
                                            AnnotableWriter annotableWriter,
                                            CommonAttributesWriter commonAttributesWriter,
                                            LinksWriter linksWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
    }

    @Override
    protected void writeAttributes(MetadataProvisionAgreement artefact, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(artefact, writer);
    }

    @Override
    protected void writeCustomAttributeElements(MetadataProvisionAgreement metadataProvisionAgreement, XMLStreamWriter writer) throws XMLStreamException {
        ArtefactReference controlledStructureUsage = metadataProvisionAgreement.getControlledStructureUsage();
        if (controlledStructureUsage != null
            && controlledStructureUsage.getMaintainableStructureClass().equals(StructureClassImpl.METADATAFLOW)) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.METADATA_FLOW);
            writeCharacters(controlledStructureUsage.getUrn(), writer);
            writer.writeEndElement();
        }

        ArtefactReference metadataProvider = metadataProvisionAgreement.getMetadataProvider();
        if (metadataProvider != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.METADATA_PROVIDER);
            writeCharacters(metadataProvider.getUrn(), writer);
            writer.writeEndElement();
        }

    }

    @Override
    protected String getName() {
        return XmlConstants.METADATA_PROVISION_AGREEMENT;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.METADATA_PROVISION_AGREEMENTS;
    }

    @Override
    protected Set<MetadataProvisionAgreement> extractArtefacts(Artefacts artefacts) {
        return artefacts.getMetadataProvisionAgreements();
    }
}
