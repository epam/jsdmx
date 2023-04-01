package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CubeRegionImpl implements CubeRegion {

    private boolean included;
    private List<MemberSelection> memberSelections = new ArrayList<>();

    public CubeRegionImpl(CubeRegionImpl from) {
        this.included = from.isIncluded();
        this.memberSelections = from.getMemberSelections();
    }

    @Override
    public CubeRegionImpl clone() {
        return new CubeRegionImpl(this);
    }
}
