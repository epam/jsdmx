package com.epam.jsdmx.infomodel.sdmx30;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TimeRangePeriodImpl extends SelectionValueImpl implements TimeRangePeriod {

    private boolean inclusive;
    private String period;

}
