package com.epam.jsdmx.infomodel.sdmx30;

import java.net.URI;

/**
 * Link to additional resources, including external resources
 */
public interface Link {
    /**
     * Holds any valid SDMX Registry URN
     */
    URI getHref();

    /**
     * Relationship of the object to the resource. See semantics below. Use 'self' to indicate the urn to the parent object.
     */
    String getRel();

    /**
     * Holds any valid SDMX Registry URN
     */
    String getUrn();

    /**
     * Holds a URI that contains a link to additional information about the resource,
     * such as a web page. This uri is not an SDMX resource.
     */
    URI getUri();

    /**
     * Title of the link.
     */
    String getTitle();

    /**
     * Provides for a set of language-specific alternates titles.
     */
    InternationalString getTitles();

    /**
     * A hint about the type of representation returned by the link.
     */
    String getType();

    /**
     * The natural language of the external link, the same as used in the HTTP Accept-Language request header.
     */
    String getHreflang();
}
