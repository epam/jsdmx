package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.AnnotableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.Annotation;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.InternationalUri;

import org.apache.commons.collections4.CollectionUtils;

public class AnnotableWriter {

    protected void write(List<Annotation> annotations, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isEmpty(annotations)) {
            return;
        }
        writer.writeStartElement(XmlConstants.COMMON + XmlConstants.COM_ANNOTATIONS);
        for (Annotation annotation : annotations) {
            writer.writeStartElement(XmlConstants.COMMON + XmlConstants.COM_ANNOTATION);
            String id = annotation.getId();
            if (id != null) {
                writer.writeAttribute(XmlConstants.ID, id);
            }

            String title = annotation.getTitle();
            if (title != null) {
                writer.writeStartElement(XmlConstants.COMMON + XmlConstants.COM_ANNOTATION_TITLE);
                writer.writeCharacters(title);
                writer.writeEndElement();
            }

            String type = annotation.getType();
            if (type != null) {
                writer.writeStartElement(XmlConstants.COMMON + XmlConstants.COM_ANNOTATION_TYPE);
                writer.writeCharacters(type);
                writer.writeEndElement();
            }

            InternationalUri url = annotation.getUrl();
            XmlWriterUtils.writeInternationalUri(url, writer, XmlConstants.COMMON + XmlConstants.COM_ANNOTATION_URL);

            InternationalString text = annotation.getText();
            XmlWriterUtils.writeInternationalString(text, writer, XmlConstants.COMMON + XmlConstants.COM_ANNOTATION_TEXT);

            String value = annotation.getValue();
            if (value != null) {
                writer.writeStartElement(XmlConstants.COMMON + XmlConstants.COM_ANNOTATION_VALUE);
                writer.writeCharacters(value);
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

    public void write(AnnotableArtefact artefact, XMLStreamWriter writer) throws XMLStreamException {
        write(artefact.getAnnotations(), writer);
    }
}
