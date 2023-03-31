package com.epam.jsdmx.infomodel.sdmx30;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RangePeriodImpl extends SelectionValueImpl implements RangePeriod {
    private TimeRangePeriod startPeriod;
    private TimeRangePeriod endPeriod;
}
