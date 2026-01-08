package com.epam.jsdmx.json20.structure.writer;

import java.io.OutputStream;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class BaseWriterJson {

    protected final JsonGenerator jsonGenerator;

    @SneakyThrows
    protected BaseWriterJson(ObjectMapper mapper,
                             OutputStream outputStream) {
        this.jsonGenerator = mapper.getFactory().createGenerator(outputStream, JsonEncoding.UTF8);
    }

}
