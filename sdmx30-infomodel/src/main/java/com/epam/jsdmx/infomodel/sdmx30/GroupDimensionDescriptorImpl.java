package com.epam.jsdmx.infomodel.sdmx30;


import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class GroupDimensionDescriptorImpl
    extends IdentifiableArtefactImpl
    implements GroupDimensionDescriptor {

    private List<String> dimensions = new ArrayList<>();

    @Override
    public StructureClassImpl getStructureClass() {
        return StructureClassImpl.GROUP_DIMENSION_DESCRIPTOR;
    }

}
