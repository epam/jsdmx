package com.epam.jsdmx.infomodel.sdmx30;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExclusiveCodeSelectionImpl
    extends BaseCodeSelectionImpl
    implements ExclusiveCodeSelection {
}
