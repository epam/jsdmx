package com.epam.jsdmx.infomodel.sdmx30;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LocalisedMemberValueImpl extends SelectionValueImpl implements LocalisedMemberValue {

    private String value;
    private String locale;

}
