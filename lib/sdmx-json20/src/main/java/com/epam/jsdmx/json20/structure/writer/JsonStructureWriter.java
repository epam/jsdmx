package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefact;
import com.epam.jsdmx.json20.structure.reader.JsonRuntimeException;
import com.epam.jsdmx.serializer.sdmx30.structure.StructureWriter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Streaming serialization for json (2.0)
 */
public class JsonStructureWriter extends BaseWriterJson implements StructureWriter {

    protected final MetaWriter metaWriter;
    protected final List<? extends MaintainableWriter<? extends MaintainableArtefact>> writers;
    private static final String LAZY = "Lazy";

    public JsonStructureWriter(ObjectMapper mapper,
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

                writeCustomSection(artefacts, jsonGenerator);

                jsonGenerator.writeEndObject();

                jsonGenerator.flush();
                jsonGenerator.close();
            }
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    protected void writeCustomSection(Artefacts artefacts, JsonGenerator jsonGenerator) {
        // do nothing here
    }

    protected void writeArtefacts(Artefacts artefacts) throws IOException {

        writers.forEach(writer -> {
            Set<? extends MaintainableArtefact> maintainableArtefacts = writer.extractArtefacts(artefacts);
            maintainableArtefacts = isLazy(maintainableArtefacts) ? maintainableArtefacts : new HashSet<>(maintainableArtefacts);
            try {
                if (!maintainableArtefacts.isEmpty()) {
                    jsonGenerator.writeFieldName(writer.getArrayName());
                    jsonGenerator.writeStartArray();

                    // Use an iterator to remove artefacts from the set after writing
                    Iterator<? extends MaintainableArtefact> iterator = maintainableArtefacts.iterator();

                    while (iterator.hasNext()) {
                        MaintainableArtefact artefact = iterator.next();

                        try {
                            // This will ensure that the objects are automatically collected as soon as no strong references exist
                            WeakReference<MaintainableArtefact> weakRef = new WeakReference<>(artefact);
                            writer.write(jsonGenerator, weakRef.get());

                            // Remove the artefact from the set to allow garbage collection
                            iterator.remove();

                            // Remove the strong reference
                            artefact = null;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

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

            Set<? extends T> maintainableArtefacts = isLazy(artefacts) ? artefacts : new HashSet<>(artefacts);

            Iterator<? extends T> iterator = maintainableArtefacts.iterator();

            while (iterator.hasNext()) {
                T artefact = iterator.next();

                // This will ensure that the objects are automatically collected as soon as no strong references exist
                WeakReference<T> weakRef = new WeakReference<>(artefact);
                writer.write(jsonGenerator, weakRef.get());

                // Remove the artefact from the collection to make it eligible for GC
                iterator.remove();

                // Remove the strong reference
                artefact = null;
            }

            jsonGenerator.writeEndArray();
        }
    }

    private boolean isLazy(Set<? extends MaintainableArtefact> artefacts) {
        return artefacts.getClass().getName().contains(LAZY);
    }
}
