package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class XmlReader<T extends MaintainableArtefactImpl> {

    protected final AnnotableReader annotableReader;
    protected final NameableReader nameableReader;

    protected abstract T createMaintainableArtefact();

    protected abstract void read(XMLStreamReader reader, T maintainableArtefact) throws URISyntaxException, XMLStreamException;

    protected T read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        T maintainable = createMaintainableArtefact();
        setAttributes(reader, maintainable);
        Map<String, String> names = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();

        moveToNextTag(reader);
        while (isNotEndingTag(reader, getName())) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, maintainable);
                    break;
                case XmlConstants.COM_NAME:
                    nameableReader.setNameable(reader, names);
                    break;
                case XmlConstants.COM_DESCRIPTION:
                    nameableReader.setNameable(reader, descriptions);
                    break;
                case XmlConstants.COM_LINK:
                    moveToNextTag(reader);
                    break;
                default:
                    read(reader, maintainable);
            }
            moveToNextTag(reader);
        }
        maintainable.setName(names.isEmpty() ? null : new InternationalString(names));
        maintainable.setDescription(descriptions.isEmpty() ? null : new InternationalString(descriptions));
        return maintainable;
    }

    protected abstract void setAttributes(XMLStreamReader reader, T maintainableArtefact) throws XMLStreamException;

    protected void clean() {
        // individual for each artefact
    }


    protected abstract String getName();

    protected abstract String getNames();

    protected abstract void setArtefacts(Artefacts artefact, Set<T> artefacts);
}
