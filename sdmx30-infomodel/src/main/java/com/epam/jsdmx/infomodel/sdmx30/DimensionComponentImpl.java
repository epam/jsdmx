package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public abstract class DimensionComponentImpl
    extends ComponentImpl
    implements DimensionComponent {

    @EqualsAndHashCode.Exclude
    private int order;

    public DimensionComponentImpl(DimensionComponent from) {
        super(Objects.requireNonNull(from));
        this.order = from.getOrder();
    }

}
