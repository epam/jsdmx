package com.epam.jsdmx.infomodel.sdmx30;

import java.net.URI;

import lombok.Data;

@Data
public class DataSourceImpl implements DataSource {

    private URI source;

    @Override
    public URI getSource() {
        return source;
    }
}
