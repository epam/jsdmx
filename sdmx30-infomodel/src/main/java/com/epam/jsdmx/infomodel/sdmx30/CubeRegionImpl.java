package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CubeRegionImpl extends AnnotableArtefactImpl implements CubeRegion {

    private boolean included;
    private List<MemberSelection> memberSelections = new ArrayList<>();
    private List<CubeRegionKey> cubeRegionKeys = new ArrayList<>();

    public CubeRegionImpl(CubeRegionImpl from) {
        this.included = from.isIncluded();
        this.memberSelections = from.getMemberSelections();
        this.cubeRegionKeys = from.getCubeRegionKeys();
    }

    @Override
    public CubeRegionImpl clone() {
        return new CubeRegionImpl(this);
    }
}
