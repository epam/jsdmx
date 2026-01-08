package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.AnnotableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.Annotation;
import com.epam.jsdmx.infomodel.sdmx30.AnnotationImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.InternationalUri;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class AnnotableReader {

    public void setAnnotations(XMLStreamReader reader, AnnotableArtefact artefact) throws XMLStreamException, URISyntaxException {
        List<Annotation> annotations = getAnnotations(reader);
        artefact.getAnnotations().addAll(annotations);
    }

    public List<Annotation> getAnnotations(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        List<Annotation> annotations = new ArrayList<>();
        Map<String, URI> urls = new HashMap<>();
        Map<String, String> texts = new HashMap<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.COM_ANNOTATIONS)) {
            String localName = reader.getLocalName();
            if (XmlConstants.COM_ANNOTATION.equals(localName)) {
                fillAnnotation(reader, annotations, urls, texts);
            }
            moveToNextTag(reader);
        }
        return annotations;
    }

    private void fillAnnotation(XMLStreamReader reader,
                                List<Annotation> annotations,
                                Map<String, URI> urls,
                                Map<String, String> texts) throws XMLStreamException, URISyntaxException {
        var annotation = new AnnotationImpl();

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(annotation::setId);

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.COM_ANNOTATION)) {
            String local = reader.getLocalName();
            switch (local) {
                case XmlConstants.COM_ANNOTATION_TITLE:
                    annotation.setTitle(reader.getElementText());
                    break;
                case XmlConstants.COM_ANNOTATION_TYPE:
                    annotation.setType(reader.getElementText());
                    break;
                case XmlConstants.COM_ANNOTATION_TEXT:
                    readOneInternationalStringAndPutToMap(reader, texts);
                    break;
                case XmlConstants.COM_ANNOTATION_VALUE:
                    annotation.setValue(reader.getElementText());
                    break;
                case XmlConstants.COM_ANNOTATION_URL:
                    String locale = reader.getAttributeValue(XmlConstants.XML_1998_NAMESPACE, XmlConstants.LANG);
                    String text = reader.getElementText();
                    if (locale != null && text != null) {
                        urls.put(locale, new URI(text));
                    }
                    break;
                default:
                    throw new IllegalStateException("Annotation does not support element: " + local);
            }
            moveToNextTag(reader);
        }
        annotation.setUrl(urls.isEmpty() ? null : new InternationalUri(urls));
        annotation.setText(texts.isEmpty() ? null : new InternationalString(texts));
        annotations.add(annotation);
    }

    private void readOneInternationalStringAndPutToMap(XMLStreamReader reader,
                                                       Map<String, String> map) throws XMLStreamException {
        String locale = reader.getAttributeValue(XmlConstants.XML_1998_NAMESPACE, XmlConstants.LANG);
        String text = reader.getElementText();
        if (locale != null && text != null) {
            map.put(locale, text);
        }
    }
}
