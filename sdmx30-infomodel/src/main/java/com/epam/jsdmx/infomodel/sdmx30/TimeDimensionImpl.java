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
public class TimeDimensionImpl
    extends DimensionComponentImpl
    implements TimeDimension {

    public TimeDimensionImpl(TimeDimension from) {
        super(Objects.requireNonNull(from));
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.TIME_DIMENSION;
    }

    @Override
    public TimeDimensionImpl clone() {
        return new TimeDimensionImpl(this);
    }
}
