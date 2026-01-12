package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefact;
import com.epam.jsdmx.serializer.sdmx30.structure.StructureWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Json10StructureWriter extends BaseWriterJson implements StructureWriter {

    protected final MetaWriter metaWriter;
    protected final List<? extends MaintainableWriter<? extends MaintainableArtefact>> writers;

    public Json10StructureWriter(ObjectMapper mapper,
                                 OutputStream outputStream,
                                 MetaWriter metaWriter,
                                 List<? extends MaintainableWriter<? extends MaintainableArtefact>> writers) {
        super(mapper, outputStream);
        this.metaWriter = metaWriter;
        this.writers = writers;
    }

    @Override
    public void write(Artefacts artefacts) {
        try {
            if (artefacts != null) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeFieldName(StructureUtils.DATA);

                jsonGenerator.writeStartObject();
                writeArtefacts(artefacts);
                jsonGenerator.writeEndObject();

                metaWriter.writeDefaultHeader(jsonGenerator);

                jsonGenerator.writeEndObject();

                jsonGenerator.flush();
                jsonGenerator.close();
            }
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    protected void writeArtefacts(Artefacts artefacts) throws IOException {

        writers.forEach(writer -> {
            Set<? extends MaintainableArtefact> maintainableArtefacts = writer.extractArtefacts(artefacts);
            try {
                if (!maintainableArtefacts.isEmpty()) {
                    jsonGenerator.writeFieldName(writer.getArrayName());
                    jsonGenerator.writeStartArray();
                    maintainableArtefacts.forEach(art -> {
                        try {
                            if (art.getVersion().isStable()) {
                                writer.write(jsonGenerator, art);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    jsonGenerator.writeEndArray();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected <T extends MaintainableArtefact> void writeArtefact(MaintainableWriter<T> writer,
                                                                  Set<? extends T> artefacts,
                                                                  String artefactName) throws IOException {
        if (artefacts != null && !artefacts.isEmpty()) {
            jsonGenerator.writeFieldName(artefactName);
            jsonGenerator.writeStartArray();
            for (T artefact : artefacts) {
                writer.write(jsonGenerator, artefact);
            }
            jsonGenerator.writeEndArray();
        }
    }
}
