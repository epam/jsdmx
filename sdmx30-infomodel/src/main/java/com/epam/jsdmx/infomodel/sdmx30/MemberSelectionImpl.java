package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class MemberSelectionImpl implements MemberSelection {

    private boolean included;
    private boolean removePrefix;
    private List<SelectionValue> selectionValues = new ArrayList<>();
    private String componentId;

    public MemberSelectionImpl(MemberSelection from) {
        this.included = from.isIncluded();
        this.removePrefix = from.isRemovePrefix();
        this.componentId = from.getComponentId();
        this.selectionValues = from.getSelectionValues();
    }

    public MemberSelectionImpl clone() {
        return new MemberSelectionImpl(this);
    }
}
