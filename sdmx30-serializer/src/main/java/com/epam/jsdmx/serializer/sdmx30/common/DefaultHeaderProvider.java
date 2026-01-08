package com.epam.jsdmx.serializer.sdmx30.common;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

import com.epam.jsdmx.infomodel.sdmx30.DefaultLocaleHolder;
import com.epam.jsdmx.infomodel.sdmx30.Party;

import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomUtils;

public class DefaultHeaderProvider {

    /**
     * This constant holds the <b>Schema ID</b>. Currently, Schema ID is different from the real
     * <a href="https://github.com/sdmx-twg/sdmx-json/blob/master/metadata-message/tools/schemas/2.0.0/sdmx-json-metadata-schema.json">
     *     sdmx-json-metadata-schema.json</a>
     * storage address.
     * In other words, Schema ID stored in this constant should match to ID provided inside
     * <a href="https://github.com/sdmx-twg/sdmx-json/blob/master/metadata-message/tools/schemas/2.0.0/sdmx-json-metadata-schema.json">
     *     sdmx-json-metadata-schema.json</a>.
     */
    public static final String SCHEMA
        = "https://raw.githubusercontent.com/sdmx-twg/sdmx-json/master/metadata-message/tools/schemas/2.0.0/sdmx-json-metadata-schema.json";

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
