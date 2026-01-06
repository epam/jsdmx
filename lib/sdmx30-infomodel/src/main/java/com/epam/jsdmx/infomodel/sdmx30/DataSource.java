package com.epam.jsdmx.infomodel.sdmx30;

import java.net.URI;

/**
 * Identification of the location or service from where data or reference metadata can be obtained.
 */
public interface DataSource {
    /**
     * The URL of the data or reference metadata source (a file or a web service)
     */
    URI getSource();
}
