package com.epam.jsdmx.infomodel.sdmx30;

import java.time.Instant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public abstract class SelectionValueImpl implements SelectionValue {

    private Instant validFrom;
    private Instant validTo;

}
