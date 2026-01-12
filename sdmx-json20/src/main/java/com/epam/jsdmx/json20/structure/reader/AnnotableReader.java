package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.isNullValue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.jsdmx.infomodel.sdmx30.AnnotableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.AnnotableArtefactImpl;
import com.epam.jsdmx.infomodel.sdmx30.Annotation;
import com.epam.jsdmx.infomodel.sdmx30.AnnotationImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.InternationalUri;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

@NoArgsConstructor
public class AnnotableReader {

    public AnnotationImpl readAnnotation(JsonParser parser) throws IOException {
        AnnotationImpl annotation = new AnnotationImpl();
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.ID:
                    setAnnotationId(parser, annotation);
                    break;
                case StructureUtils.TITLE:
                    setAnnotationTitle(parser, annotation);
                    break;
                case StructureUtils.VALUE:
                    setAnnotationValue(parser, annotation);
                    break;
                case StructureUtils.TYPE:
                    setAnnotationType(parser, annotation);
                    break;
                case StructureUtils.TEXT:
                    parser.nextToken();
                    break;
                case StructureUtils.TEXTS:
                    setAnnotationTexts(parser, annotation);
                    break;
                case StructureUtils.LINKS:
                    setAnnotationLinks(parser, annotation);
                    break;
                default:
                    throw new IllegalArgumentException("No such argument in Annotations: " + fieldName);
            }
        }
        return annotation;
    }

    private void setAnnotationTitle(JsonParser parser, AnnotationImpl annotation) throws IOException {
        String title = ReaderUtils.getStringJsonField(parser);
        if (title != null) {
            annotation.setTitle(title);
        }
    }

    private void setAnnotationValue(JsonParser parser, AnnotationImpl annotation) throws IOException {
        String value = ReaderUtils.getStringJsonField(parser);
        if (value != null) {
            annotation.setValue(value);
        }
    }

    private void setAnnotationType(JsonParser parser, AnnotationImpl annotation) throws IOException {
        String type = ReaderUtils.getStringJsonField(parser);
        if (type != null) {
            annotation.setType(type);
        }
    }

    private void setAnnotationLinks(JsonParser parser, AnnotationImpl annotation) throws IOException {
        List<URI> uriList = ReaderUtils.getArray(parser, parser1 -> getURIFromLink(parser));
        if (CollectionUtils.isNotEmpty(uriList)) {
            annotation.setUrl(new InternationalUri(uriList.get(0)));
        }
    }

    private void setAnnotationTexts(JsonParser parser, AnnotationImpl annotation) throws IOException {
        Map<String, String> localizedText = new HashMap<>();
        parser.nextToken();
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            localizedText.put(parser.getCurrentName(), parser.getText());
        }
        annotation.setText(new InternationalString(localizedText));
    }

    private void setAnnotationId(JsonParser parser, AnnotationImpl annotation) throws IOException {
        String id = ReaderUtils.getStringJsonField(parser);
        if (id != null) {
            annotation.setId(id);
        }
    }

    private URI getURIFromLink(JsonParser parser) {
        try {
            while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.HREF:
                    case StructureUtils.URN:
                    case StructureUtils.REL:
                    case StructureUtils.TITLE:
                    case StructureUtils.TITLES:
                    case StructureUtils.TYPE:
                    case StructureUtils.HREFLANG:
                        parser.nextToken();
                        break;
                    case StructureUtils.URI:
                        String uriString = ReaderUtils.getStringJsonField(parser);
                        if (uriString != null) {
                            return new URI(uriString);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("No such argument in Links: " + fieldName);
                }
            }
            return null;
        } catch (IOException | URISyntaxException e) {
            throw new JsonRuntimeException(e);
        }
    }

    public AnnotableArtefact read(AnnotableArtefactImpl annotableArtefact, JsonParser parser) throws IOException {
        parser.nextToken();
        if (isNullValue(parser)) {
            return annotableArtefact;
        }
        List<Annotation> annotationList = new ArrayList<>();
        while (!JsonToken.END_ARRAY.equals(parser.currentToken())) {
            if (JsonToken.START_OBJECT.equals(parser.nextToken())) {
                AnnotationImpl annot = readAnnotation(parser);
                annotationList.add(annot);
            }
        }
        annotableArtefact.setAnnotations(annotationList);
        return annotableArtefact;
    }
}
