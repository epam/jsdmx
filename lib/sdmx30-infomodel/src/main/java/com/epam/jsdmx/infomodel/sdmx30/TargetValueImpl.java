package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class TargetValueImpl implements TargetValue {
    private String value;

    public TargetValueImpl(TargetValue from) {
        Objects.requireNonNull(from);
        this.value = from.getValue();
    }
}
