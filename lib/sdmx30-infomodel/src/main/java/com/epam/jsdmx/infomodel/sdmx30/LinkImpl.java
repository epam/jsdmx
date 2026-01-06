package com.epam.jsdmx.infomodel.sdmx30;

import java.net.URI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkImpl implements Link {
    private URI href;
    private String rel;
    private String urn;
    private URI uri;
    private String title;
    private InternationalString titles;
    private String type;
    private String hreflang;
}
