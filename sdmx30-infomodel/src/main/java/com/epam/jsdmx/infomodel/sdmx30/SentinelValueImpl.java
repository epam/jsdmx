package com.epam.jsdmx.infomodel.sdmx30;

import lombok.Data;

@Data
public class SentinelValueImpl implements SentinelValue {

    private String value;
    private InternationalString name;
    private InternationalString description;

    public SentinelValueImpl(SentinelValueImpl from) {
        this.value = from.getValue();
        this.name = from.getName();
        this.description = from.getDescription();
    }

    @Override
    public SentinelValue clone() {
        return new SentinelValueImpl(this);
    }
}
