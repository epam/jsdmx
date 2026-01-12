package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setCommonAttributes;

import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.ProvisionAgreementImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class ProvisionAgreementReader extends XmlReader<ProvisionAgreementImpl> {

    public ProvisionAgreementReader(AnnotableReader annotableReader,
                                    NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected ProvisionAgreementImpl createMaintainableArtefact() {
        return new ProvisionAgreementImpl();
    }

    @Override
    protected void read(XMLStreamReader reader, ProvisionAgreementImpl provisionAgreement) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.DATAFLOW:
                Optional.ofNullable(reader.getElementText())
                    .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(provisionAgreement::setControlledStructureUsage);
                break;
            case XmlConstants.DATA_PROVIDER:
                Optional.ofNullable(reader.getElementText())
                    .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                    .map(IdentifiableArtefactReferenceImpl::new)
                    .ifPresent(provisionAgreement::setDataProvider);
                break;
            default:
                throw new IllegalArgumentException("ProvisionAgreement " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, ProvisionAgreementImpl maintainableArtefact) throws XMLStreamException {
        setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.PROVISION_AGREEMENT;
    }

    @Override
    protected String getNames() {
        return XmlConstants.PROVISION_AGREEMENTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<ProvisionAgreementImpl> artefacts) {
        artefact.getProvisionAgreements().addAll(artefacts);
    }
}
