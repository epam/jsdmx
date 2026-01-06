package com.epam.jsdmx.infomodel.sdmx30;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeDimensionValueImpl implements TimeDimensionValue {

    private String operator;
    private String timeValue;

    public TimeDimensionValueImpl(TimeDimensionValue from) {
        this.operator = from.getOperator();
        this.timeValue = from.getTimeValue();
    }

    @Override
    public TimeDimensionValue clone() {
        return new TimeDimensionValueImpl(this);
    }
}
