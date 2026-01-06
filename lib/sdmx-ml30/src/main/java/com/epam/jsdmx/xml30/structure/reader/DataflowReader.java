package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setCommonAttributes;

import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataflowImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class DataflowReader extends XmlReader<DataflowImpl> {

    public DataflowReader(AnnotableReader annotableReader,
                          NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected DataflowImpl createMaintainableArtefact() {
        return new DataflowImpl();
    }

    @Override
    protected void read(XMLStreamReader reader, DataflowImpl dataflow) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        if (!XmlConstants.STRUCTURE_UPPER.equals(localName)) {
            throw new IllegalArgumentException("Dataflow " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }

        Optional.ofNullable(reader.getElementText())
            .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
            .map(MaintainableArtefactReference::new)
            .ifPresent(dataflow::setStructure);
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, DataflowImpl maintainableArtefact) throws XMLStreamException {
        setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.DATAFLOW;
    }

    @Override
    protected String getNames() {
        return XmlConstants.DATAFLOWS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<DataflowImpl> artefacts) {
        artefact.getDataflows().addAll(artefacts);
    }
}
