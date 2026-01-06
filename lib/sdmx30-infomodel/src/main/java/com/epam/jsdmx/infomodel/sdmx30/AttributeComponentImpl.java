package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class AttributeComponentImpl
    extends ComponentImpl
    implements AttributeComponent {

    public AttributeComponentImpl(AttributeComponent from) {
        super(Objects.requireNonNull(from));
    }

}
