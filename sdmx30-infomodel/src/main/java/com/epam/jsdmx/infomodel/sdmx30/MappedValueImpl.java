package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class MappedValueImpl
    implements MappedValue {

    private String value;
    private int startIndex;
    private int endIndex;
    private boolean regEx;

    public MappedValueImpl(MappedValue from) {
        Objects.requireNonNull(from);
        this.value = from.getValue();
        this.startIndex = from.getStartIndex();
        this.endIndex = from.getEndIndex();
        this.regEx = from.isRegEx();
    }
}
