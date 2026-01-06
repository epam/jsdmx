package com.epam.jsdmx.infomodel.sdmx30;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CubeRegionKeyImpl implements CubeRegionKey {

    private boolean included;
    private boolean removePrefix;
    private String componentId;
    private List<SelectionValue> selectionValues = new ArrayList<>();
    private Instant validTo;
    private Instant validFrom;

    public CubeRegionKeyImpl(CubeRegionKeyImpl from) {
        this.included = from.isIncluded();
        this.removePrefix = from.isRemovePrefix();
        this.componentId = from.getComponentId();
        this.selectionValues = from.getSelectionValues();
        this.validTo = from.getValidTo();
        this.validFrom = from.getValidFrom();
    }

    @Override
    public CubeRegionKeyImpl clone() {
        return new CubeRegionKeyImpl(this);
    }
}
