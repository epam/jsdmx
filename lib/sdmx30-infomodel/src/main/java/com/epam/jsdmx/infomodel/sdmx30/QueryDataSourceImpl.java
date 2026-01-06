package com.epam.jsdmx.infomodel.sdmx30;

import java.net.URI;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class QueryDataSourceImpl
    extends DataSourceImpl
    implements QueryDataSource {

    private URI specification;

    @Override
    public URI getSpecification() {
        return specification;
    }
}
