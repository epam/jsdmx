package com.epam.jsdmx.xml30.structure.reader;

import java.net.URISyntaxException;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CategorisationImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class CategorisationReader extends XmlReader<CategorisationImpl> {

    public CategorisationReader(AnnotableReader annotableReader,
                                NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected CategorisationImpl createMaintainableArtefact() {
        return new CategorisationImpl();
    }

    @Override
    protected void read(XMLStreamReader reader, CategorisationImpl categorisation) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.SOURCE:
                String source = reader.getElementText();
                if (XmlReaderUtils.isNotEmptyOrNullElementText(source)) {
                    IdentifiableArtefactReferenceImpl artefactReference = new IdentifiableArtefactReferenceImpl(source);
                    categorisation.setCategorizedArtefact(artefactReference);
                }
                break;
            case XmlConstants.TARGET:
                String target = reader.getElementText();
                if (XmlReaderUtils.isNotEmptyOrNullElementText(target)) {
                    IdentifiableArtefactReferenceImpl artefactReference = new IdentifiableArtefactReferenceImpl(target);
                    categorisation.setCategorizedBy(artefactReference);
                }
                break;
            default:
                throw new IllegalArgumentException("Categorisation " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, CategorisationImpl maintainableArtefact) throws XMLStreamException {
        XmlReaderUtils.setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.CATEGORISATION;
    }

    @Override
    protected String getNames() {
        return XmlConstants.CATEGORISATIONS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<CategorisationImpl> artefacts) {
        artefact.getCategorisations().addAll(artefacts);
    }
}
