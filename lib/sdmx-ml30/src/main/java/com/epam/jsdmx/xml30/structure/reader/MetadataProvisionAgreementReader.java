package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setCommonAttributes;

import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProvisionAgreementImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class MetadataProvisionAgreementReader extends XmlReader<MetadataProvisionAgreementImpl> {

    public MetadataProvisionAgreementReader(AnnotableReader annotableReader,
                                            NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected MetadataProvisionAgreementImpl createMaintainableArtefact() {
        return new MetadataProvisionAgreementImpl();
    }

    @Override
    protected void read(XMLStreamReader reader, MetadataProvisionAgreementImpl metadataProvisionAgreement) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.METADATAFLOW:
                Optional.ofNullable(reader.getElementText())
                    .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(metadataProvisionAgreement::setControlledStructureUsage);
                break;
            case XmlConstants.METADATA_PROVIDER:
                Optional.ofNullable(reader.getElementText())
                    .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(metadataProvisionAgreement::setMetadataProvider);
                break;
            default:
                throw new IllegalArgumentException("MetadataProvisionAgreement " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, MetadataProvisionAgreementImpl maintainableArtefact) throws XMLStreamException {
        setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.METADATA_PROVISION_AGREEMENT;
    }

    @Override
    protected String getNames() {
        return XmlConstants.METADATA_PROVISION_AGREEMENTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<MetadataProvisionAgreementImpl> artefacts) {
        artefact.getMetadataProvisionAgreements().addAll(artefacts);
    }
}
