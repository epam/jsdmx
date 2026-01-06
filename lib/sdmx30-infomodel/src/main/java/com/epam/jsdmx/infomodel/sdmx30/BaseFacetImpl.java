package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseFacetImpl implements Facet {

    private FacetType type;
    private String value;
    private FacetValueType valueType;
    private List<SentinelValue> sentinelValues = new ArrayList<>();

    public BaseFacetImpl(BaseFacetImpl from) {
        Objects.requireNonNull(from);
        this.type = from.getType();
        this.value = from.getValue();
        this.valueType = from.getValueType();
        this.sentinelValues = StreamUtils.streamOfNullable(from.getSentinelValues())
            .map(SentinelValue::clone)
            .collect(toList());
    }

    public BaseFacetImpl(FacetValueType valueType) {
        this.valueType = valueType;
    }

    @Override
    public BaseFacetImpl clone() {
        return new BaseFacetImpl(this);
    }

    @Override
    public List<SentinelValue> getSentinelValues() {
        return sentinelValues;
    }
}
