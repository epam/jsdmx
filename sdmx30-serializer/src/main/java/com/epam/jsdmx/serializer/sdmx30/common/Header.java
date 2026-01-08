package com.epam.jsdmx.serializer.sdmx30.common;


import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.Party;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base type that defines the basis for all message headers
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Header {

    private URI schema;
    private String id;
    private boolean test;
    private Instant prepared;
    private List<Locale> contentLanguages;
    private InternationalString name;
    private Party sender;
    private List<Party> receivers;

}
