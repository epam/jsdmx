package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.Party;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;
import com.epam.jsdmx.serializer.sdmx30.common.Header;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.lang3.LocaleUtils;

public class MetaReader {

    public Header read(JsonParser parser) throws IOException {
        Header header = new Header();
        if (JsonToken.START_OBJECT.equals(parser.currentToken())
            && JsonToken.END_OBJECT.equals(parser.nextToken())) {
            return null;
        }
        while (!JsonToken.END_OBJECT.equals(parser.currentToken())) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String currentParserName = parser.getCurrentName();
            switch (currentParserName) {
                case StructureUtils.SCHEMA:
                    String schemaURI = ReaderUtils.getStringJsonField(parser);
                    if (schemaURI != null) {
                        try {
                            URI uri = new URI(schemaURI);
                            header.setSchema(uri);
                        } catch (URISyntaxException e) {
                            throw new MalformedURLException(e.getMessage());
                        }

                    }
                    break;
                case StructureUtils.ID:
                    header.setId(ReaderUtils.getStringJsonField(parser));
                    break;
                case StructureUtils.TEST:
                    header.setTest(ReaderUtils.getBooleanJsonField(parser));
                    break;
                case StructureUtils.PREPARED:
                    header.setPrepared(ReaderUtils.getInstantObj(parser));
                    break;
                case StructureUtils.CONTENT_LANGUAGES:
                    List<String> contentLangs = ReaderUtils.getListStrings(parser);
                    List<Locale> locales = contentLangs.stream()
                        .filter(Objects::nonNull)
                        .map(LocaleUtils::toLocale)
                        .collect(Collectors.toList());
                    header.setContentLanguages(locales);
                    break;
                case StructureUtils.NAME:
                    String name = ReaderUtils.getStringJsonField(parser);
                    if (name != null) {
                        header.setName(new InternationalString(name));
                    }
                    break;
                case StructureUtils.SENDER:
                    header.setSender(getParty(parser));
                    break;
                default:
                    throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN
                        + "Header: " + currentParserName);
            }
            parser.nextToken();
        }
        return header;
    }

    private Party getParty(JsonParser parser) throws IOException {
        if (JsonToken.START_OBJECT.equals(parser.currentToken())
            && JsonToken.END_OBJECT.equals(parser.nextToken())) {
            return null;
        }
        Party party = new Party();
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String currentParserName = parser.getCurrentName();
            switch (currentParserName) {
                case StructureUtils.ID:
                    party.setId(ReaderUtils.getStringJsonField(parser));
                    break;
                case StructureUtils.NAME:
                    party.setName(
                        new InternationalString(
                            ReaderUtils.getStringJsonField(parser)));
                    break;
                default:
                    throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN
                        + "Party: " + currentParserName);
            }
        }
        return party;
    }
}
