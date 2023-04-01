package com.epam.jsdmx.infomodel.sdmx30;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemberValueImpl
    extends SelectionValueImpl
    implements MemberValue {

    private String value;
    private CascadeValue cascadeValue;

}
