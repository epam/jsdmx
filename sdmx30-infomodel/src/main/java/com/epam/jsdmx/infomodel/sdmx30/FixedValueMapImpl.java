package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FixedValueMapImpl
    extends AnnotableArtefactImpl
    implements FixedValueMap {

    private MappingRoleType role;
    private String value;
    private String component;

    public FixedValueMapImpl(FixedValueMap from) {
        super(Objects.requireNonNull(from));
        this.role = from.getRole();
        this.value = from.getValue();
        this.component = from.getComponent();
    }

}
