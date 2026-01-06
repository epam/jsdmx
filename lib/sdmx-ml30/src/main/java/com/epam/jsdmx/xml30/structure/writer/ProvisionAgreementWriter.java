package com.epam.jsdmx.xml30.structure.writer;

import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ProvisionAgreement;

public class ProvisionAgreementWriter extends XmlWriter<ProvisionAgreement> {

    public ProvisionAgreementWriter(NameableWriter nameableWriter,
                                    AnnotableWriter annotableWriter,
                                    CommonAttributesWriter commonAttributesWriter,
                                    LinksWriter linksWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
    }

    @Override
    protected void writeAttributes(ProvisionAgreement provisionAgreement, XMLStreamWriter writer) throws XMLStreamException {
        commonAttributesWriter.writeAttributes(provisionAgreement, writer);
    }

    @Override
    protected void writeCustomAttributeElements(ProvisionAgreement provisionAgreement, XMLStreamWriter writer) throws XMLStreamException {
        ArtefactReference dataProvider = provisionAgreement.getDataProvider();
        ArtefactReference controlledStructureUsage = provisionAgreement.getControlledStructureUsage();

        if (controlledStructureUsage != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.DATAFLOW);
            XmlWriterUtils.writeCharacters(controlledStructureUsage.getUrn(), writer);
            writer.writeEndElement();
        }

        if (dataProvider != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.DATA_PROVIDER);
            XmlWriterUtils.writeCharacters(dataProvider.getUrn(), writer);
            writer.writeEndElement();
        }
    }

    @Override
    protected String getName() {
        return XmlConstants.PROVISION_AGREEMENT;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.PROVISION_AGREEMENTS;
    }

    @Override
    protected Set<ProvisionAgreement> extractArtefacts(Artefacts artefacts) {
        return artefacts.getProvisionAgreements();
    }
}
