package com.epam.jsdmx.serializer.sdmx30.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InMemoryDataLocation implements DataLocation {

    private final byte[] bytes;

    public InMemoryDataLocation(String source) {
        this.bytes = source.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public InputStream inputStream() {
        return new ByteArrayInputStream(bytes);
    }
}
