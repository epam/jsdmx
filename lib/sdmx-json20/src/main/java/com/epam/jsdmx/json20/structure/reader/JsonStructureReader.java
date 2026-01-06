package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.io.InputStream;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.serializer.sdmx30.common.DataLocation;
import com.epam.jsdmx.serializer.sdmx30.common.Header;
import com.epam.jsdmx.serializer.sdmx30.structure.StructureReader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.extern.slf4j.Slf4j;

/**
 * Streaming deserialization for json (2.0)
 */
@Slf4j
public class JsonStructureReader implements StructureReader {

    private final DataReader dataReader;
    private final MetaReader metaReader;

    public JsonStructureReader(DataReader dataReader, MetaReader metaReader) {
        this.dataReader = dataReader;
        this.metaReader = metaReader;
    }

    @Override
    public ArtefactsImpl read(DataLocation location) {
        final InputStream inputStream = location.inputStream();
        JsonFactory factory = new JsonFactory();
        ArtefactsImpl artefacts = new ArtefactsImpl();
        try (JsonParser parser = factory.createParser(inputStream)) {
            while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
                ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case "data":
                        parser.nextToken();
                        artefacts = getData(parser);
                        break;
                    case "meta":
                        parser.nextToken();
                        getHeader(parser);
                        break;
                    case "error":
                        parser.nextToken();
                        break;
                    default:
                        throw new IllegalArgumentException("Not correct structure: " + fieldName);
                }
            }
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
        return artefacts;
    }

    private Header getHeader(JsonParser parser) throws IOException {
        return metaReader.read(parser);
    }

    private ArtefactsImpl getData(JsonParser parser) throws IOException {
        return dataReader.read(parser);
    }

    public ArtefactsImpl readAndClose(DataLocation location) {
        final InputStream inputStream = location.inputStream();
        try (inputStream) {
            return read(location);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
