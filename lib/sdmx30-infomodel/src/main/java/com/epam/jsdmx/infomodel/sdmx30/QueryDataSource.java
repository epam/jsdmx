package com.epam.jsdmx.infomodel.sdmx30;

import java.net.URI;

/**
 * A data or reference metadata source, which can process a data or metadata query.
 */
public interface QueryDataSource extends DataSource {
    /**
     * Association to the URL for the specification of the web service
     */
    URI getSpecification();
}
