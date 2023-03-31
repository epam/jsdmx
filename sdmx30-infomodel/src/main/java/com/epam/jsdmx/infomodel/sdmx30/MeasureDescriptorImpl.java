package com.epam.jsdmx.infomodel.sdmx30;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MeasureDescriptorImpl
    extends ComponentListImpl<Measure>
    implements MeasureDescriptor {

    @Override
    public StructureClassImpl getStructureClass() {
        return StructureClassImpl.MEASURE_DESCRIPTOR;
    }
}
