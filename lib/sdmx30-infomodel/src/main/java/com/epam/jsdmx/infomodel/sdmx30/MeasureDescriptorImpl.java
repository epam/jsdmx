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

    public MeasureDescriptorImpl() {
    }

    public MeasureDescriptorImpl(MeasureDescriptor from) {
        super(from);
    }

    @Override
    public StructureClassImpl getStructureClass() {
        return StructureClassImpl.MEASURE_DESCRIPTOR;
    }
}
