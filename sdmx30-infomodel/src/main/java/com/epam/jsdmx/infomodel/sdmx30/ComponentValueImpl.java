package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentValueImpl implements ComponentValue {

    private String value;
    private String componentId;
    private List<TimeDimensionValue> timeDimensionValues = new ArrayList<>();

    public ComponentValueImpl(ComponentValue from) {
        this.value = from.getValue();
        this.componentId = from.getComponentId();
        this.timeDimensionValues = StreamUtils.streamOfNullable(from.getTimeDimensionValues())
            .map(TimeDimensionValue::clone)
            .collect(toList());
    }

    @Override
    public ComponentValue clone() {
        return new ComponentValueImpl(this);
    }
}
