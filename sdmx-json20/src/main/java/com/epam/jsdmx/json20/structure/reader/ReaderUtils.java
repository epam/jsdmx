package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.epam.jsdmx.infomodel.sdmx30.Annotation;
import com.epam.jsdmx.infomodel.sdmx30.AnnotationImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReaderUtils {

    public static Map<String, String> getLocalizedField(JsonParser parser) throws IOException {
        return getLocalizedField(parser, false);
    }

    public static Map<String, String> getLocalizedField(JsonParser parser, boolean skipFirstToken) throws IOException {
        Map<String, String> localisations = new HashMap<>();
        if (!skipFirstToken) {
            parser.nextToken();
        }
        while (!JsonToken.END_OBJECT.equals(parser.getCurrentToken())) {
            if (!checkIsNotEmptyObjectAndSkipUntilFieldName(parser)) {
                return null;
            }
            String locale = parser.getCurrentName();
            parser.nextToken();
            String text = parser.getText();
            localisations.put(locale, text);
            parser.nextToken();
        }
        return localisations;
    }

    public static <T> List<T> getArray(JsonParser parser, Function<JsonParser, T> function) throws IOException {
        List<T> listObj = new ArrayList<>();
        while (!JsonToken.END_ARRAY.equals(parser.nextToken()) && parser.hasCurrentToken()) {
            if (JsonToken.START_OBJECT.equals(parser.currentToken())) {
                listObj.add(function.apply(parser));
            }
        }
        return listObj;
    }

    public static List<String> getListStrings(JsonParser parser) throws IOException {
        List<String> strings = new ArrayList<>();
        parser.nextToken();
        while (parser.currentToken() != JsonToken.END_ARRAY) {
            if (parser.currentToken().equals(JsonToken.START_ARRAY)
                && parser.nextToken().equals(JsonToken.END_ARRAY)) {
                return strings;
            }
            String text = parser.getText();
            if (text != null) {
                strings.add(text);
            }
            parser.nextToken();
        }
        return strings;
    }

    public static String getStringJsonField(JsonParser parser) throws IOException {
        parser.nextToken();
        if (JsonToken.VALUE_NULL.equals(parser.getCurrentToken())) {
            return null;
        }
        return parser.getText();
    }

    public static String getFieldAsString(JsonParser parser) throws IOException {
        parser.nextToken();
        if (JsonToken.VALUE_NULL.equals(parser.getCurrentToken())) {
            return null;
        }
        return parser.getValueAsString();
    }

    public static boolean getBooleanJsonField(JsonParser parser) throws IOException {
        parser.nextToken();
        if (JsonToken.VALUE_NULL.equals(parser.getCurrentToken())) {
            throw new IllegalArgumentException("value can't be null");
        }
        return parser.getBooleanValue();
    }

    public static int getIntJsonField(JsonParser parser) throws IOException {
        parser.nextToken();
        if (JsonToken.VALUE_NULL.equals(parser.getCurrentToken())) {
            throw new IllegalArgumentException("value can't be null");
        }
        return parser.getIntValue();
    }

    public static boolean checkIsNotEmptyObjectAndSkipUntilFieldName(JsonParser parser) throws IOException {
        while (!JsonToken.FIELD_NAME.equals(parser.currentToken())) {
            if (JsonToken.END_OBJECT.equals(parser.getCurrentToken())) {
                // skip empty object
                return false;
            }
            if (parser.currentToken() == null) {
                return false;
            }
            parser.nextToken();
        }
        return true;
    }

    public static boolean isNullValue(JsonParser parser) {
        return JsonToken.VALUE_NULL.equals(parser.currentToken());
    }

    public static Instant getInstantObj(JsonParser parser) throws IOException {
        parser.nextToken();
        if (JsonToken.VALUE_NULL.equals(parser.getCurrentToken())) {
            return null;
        }
        String data = parser.getText();
        return mapStringToInstant(data);
    }

    public static Instant mapStringToInstant(String time) {
        if (time.contains(StructureUtils.ZONE)) {
            return Instant.from(getDateTimeFormatterWithZone().parse(time));
        } else {
            LocalDateTime localDateTime = LocalDateTime.parse(time, getDateTimeFormatterWithoutZone());
            return localDateTime.atZone(ZoneOffset.UTC).toInstant();
        }
    }


    private static DateTimeFormatter getDateTimeFormatterWithoutZone() {
        return new DateTimeFormatterBuilder()
            .appendPattern(StructureUtils.BASIC_TIME_FORMAT)
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 9, true)
            .toFormatter();
    }

    private static DateTimeFormatter getDateTimeFormatterWithZone() {
        return new DateTimeFormatterBuilder()
            .appendPattern(StructureUtils.BASIC_TIME_FORMAT)
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 9, true)
            .appendZoneOrOffsetId()
            .toFormatter();
    }

    public static List<Annotation> readJsonStringAnnotations(String annotations, String artefactId) {
        if (StringUtils.isNotBlank(annotations)) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonParser parser = objectMapper.getFactory().createParser(annotations);

                parser.nextToken();

                List<Annotation> annotationList = new ArrayList<>();
                AnnotableReader annotableReader = new AnnotableReader();

                while (parser.currentToken() != JsonToken.END_ARRAY) {
                    if (parser.currentToken() == JsonToken.START_OBJECT) {
                        AnnotationImpl annotation = annotableReader.readAnnotation(parser);
                        annotationList.add(annotation);
                    } else {
                        parser.nextToken();
                    }
                }

                return annotationList;
            } catch (Exception e) {
                throw new JsonRuntimeException("Problem during parsing annotation form JSON for: " + artefactId);
            }
        } else {
            return new ArrayList<>();
        }
    }
}
