package com.epam.jsdmx.json10.structure.writer;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

import com.epam.jsdmx.infomodel.sdmx30.DefaultLocaleHolder;
import com.epam.jsdmx.infomodel.sdmx30.Party;
import com.epam.jsdmx.serializer.sdmx30.common.Header;

import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomUtils;

public class DefaultHeaderProvider {
    public static final String SCHEMA
        = "https://github.com/sdmx-twg/sdmx-json/blob/v1.0/structure-message/tools/schemas/1.0/sdmx-json-structure-schema.json";

    @SneakyThrows
    public Header provide() {
        Header header = new Header();
        header.setSchema(new URI(SCHEMA));
        header.setId(generateId());
        header.setContentLanguages(getDefaultLocales());
        header.setTest(false);
        header.setPrepared(Instant.now());
        header.setSender(generateUnknownParty());
        return header;
    }

    private String generateId() {
        return "IDREF" + RandomUtils.nextInt(1000, 9999);
    }

    private Party generateUnknownParty() {
        return Party.builder()
            .id("unknown")
            .build();
    }

    private List<Locale> getDefaultLocales() {
        return List.of(DefaultLocaleHolder.INSTANCE.get());
    }
}
