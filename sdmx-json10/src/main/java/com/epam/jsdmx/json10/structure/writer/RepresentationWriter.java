package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Facet;
import com.epam.jsdmx.infomodel.sdmx30.Representation;
import com.epam.jsdmx.infomodel.sdmx30.TextFormatImpl;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;
import com.epam.jsdmx.serializer.sdmx21.ReferenceResolver;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RepresentationWriter {

    private final ReferenceResolver referenceResolver;
    private final ReferenceAdapter referenceAdapter;

    public void writeRepresentation(JsonGenerator jsonGenerator,
                                    Representation localRepresentation,
                                    String representationName) throws IOException {
        jsonGenerator.writeFieldName(representationName);
        jsonGenerator.writeStartObject();
        if (localRepresentation != null) {
            if (localRepresentation.isEnumerated()) {
                final ArtefactReference ref = localRepresentation.enumerated();
                final ArtefactReference resolvedRef = referenceResolver.resolve(ref);
                jsonGenerator.writeStringField(StructureUtils.ENUMERATION, referenceAdapter.toAdaptedUrn(resolvedRef));
            } else {
                Set<Facet> facets = localRepresentation.nonEnumerated();
                TextFormatImpl textFormat = new TextFormatImpl(facets);
                jsonGenerator.writeFieldName("textFormat");
                jsonGenerator.writeStartObject();

                writeTextFormat(jsonGenerator, textFormat);

                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndObject();
    }

    private void writeTextFormat(JsonGenerator jsonGenerator, TextFormatImpl textFormat) throws IOException {

        StructureUtils.writeCommonFormatAttributes(jsonGenerator, textFormat);
        Optional<BigInteger> decimals = textFormat.getDecimals();
        if (decimals.isPresent()) {
            jsonGenerator.writeNumberField(StructureUtils.DECIMALS, decimals.get());
        }
    }
}
