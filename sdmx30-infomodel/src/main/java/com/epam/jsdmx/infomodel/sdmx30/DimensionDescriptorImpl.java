package com.epam.jsdmx.infomodel.sdmx30;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class DimensionDescriptorImpl
    extends ComponentListImpl<DimensionComponent>
    implements DimensionDescriptor {

    public DimensionDescriptorImpl() {
    }

    public DimensionDescriptorImpl(DimensionDescriptor from) {
        super(from);
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.DIMENSION_DESCRIPTOR;
    }
}
