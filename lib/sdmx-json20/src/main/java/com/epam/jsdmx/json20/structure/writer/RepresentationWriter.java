package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Facet;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.Representation;
import com.epam.jsdmx.infomodel.sdmx30.SentinelValue;
import com.epam.jsdmx.infomodel.sdmx30.TextFormatImpl;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;

public class RepresentationWriter {

    private final ReferenceAdapter referenceAdapter;

    public RepresentationWriter(ReferenceAdapter referenceAdapter) {
        this.referenceAdapter = referenceAdapter;
    }

    public void writeRepresentation(JsonGenerator jsonGenerator,
                                    Representation representation,
                                    String representationName) throws IOException {
        jsonGenerator.writeFieldName(representationName);
        jsonGenerator.writeStartObject();
        if (representation != null) {
            if (representation.isEnumerated()) {
                jsonGenerator.writeStringField(StructureUtils.ENUMERATION, referenceAdapter.toAdaptedUrn(representation.enumerated()));
            } else {
                Set<Facet> facets = representation.nonEnumerated();
                TextFormatImpl textFormat = new TextFormatImpl(facets);
                jsonGenerator.writeFieldName(StructureUtils.FORMAT);
                jsonGenerator.writeStartObject();

                writeTextFormat(jsonGenerator, textFormat);

                writeSentinelValues(jsonGenerator, textFormat);

                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndObject();
    }

    private void writeSentinelValues(JsonGenerator jsonGenerator, TextFormatImpl textFormat) throws IOException {
        if (textFormat.hasSentinelValues()) {
            Optional<List<SentinelValue>> sentinelValues = textFormat.getSentinelValues();
            if (sentinelValues.isPresent()) {
                List<SentinelValue> sentinelValueList = sentinelValues.get();
                jsonGenerator.writeFieldName(StructureUtils.SENTINEL_VALUES);
                jsonGenerator.writeStartArray();
                for (SentinelValue sentinelValue : sentinelValueList) {
                    writeSentinelValue(jsonGenerator, sentinelValue);
                }
                jsonGenerator.writeEndArray();
            }
        }
    }

    private void writeSentinelValue(JsonGenerator jsonGenerator, SentinelValue sentinelValue) throws IOException {
        jsonGenerator.writeStartObject();
        String value = sentinelValue.getValue();
        if (value != null) {
            jsonGenerator.writeStringField(StructureUtils.VALUE, value);

            writeName(jsonGenerator, sentinelValue);

            writeDescription(jsonGenerator, sentinelValue);
        }
        jsonGenerator.writeEndObject();
    }

    private void writeDescription(JsonGenerator jsonGenerator, SentinelValue sentinelValue) throws IOException {
        InternationalString description = sentinelValue.getDescription();
        if (description != null) {
            jsonGenerator.writeStringField(
                StructureUtils.DESCRIPTION,
                description.getForDefaultLocale()
            );
        }
        StructureUtils.writeInternationalString(jsonGenerator, description, StructureUtils.DESCRIPTIONS);
    }

    private void writeName(JsonGenerator jsonGenerator, SentinelValue sentinelValue) throws IOException {
        InternationalString name = sentinelValue.getName();
        if (name != null) {
            jsonGenerator.writeStringField(
                StructureUtils.NAME,
                name.getForDefaultLocale()
            );
        }
        StructureUtils.writeInternationalString(jsonGenerator, name, StructureUtils.NAMES);
    }

    private void writeTextFormat(JsonGenerator jsonGenerator, TextFormatImpl textFormat) throws IOException {

        StructureUtils.writeCommonFormatAttributes(jsonGenerator, textFormat);
        Optional<BigInteger> decimals = textFormat.getDecimals();
        if (decimals.isPresent()) {
            jsonGenerator.writeNumberField(StructureUtils.DECIMALS, decimals.get());
        }
        Optional<Boolean> multiLingual = textFormat.getIsMultiLingual();
        if (multiLingual.isPresent()) {
            jsonGenerator.writeBooleanField(StructureUtils.IS_MULTI_LINGUAL, multiLingual.get());
        }
    }
}
