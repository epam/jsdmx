package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataKeyImpl extends AnnotableArtefactImpl implements DataKey {

    private boolean included;
    private List<ComponentValue> keyValues = new ArrayList<>();
    private List<MemberSelection> memberSelections = new ArrayList<>();
    private Instant validFrom;
    private Instant validTo;

    public DataKeyImpl(DataKey from) {
        this.included = from.isIncluded();
        this.validFrom = from.getValidFrom();
        this.validTo = from.getValidTo();
        this.keyValues = StreamUtils.streamOfNullable(from.getKeyValues())
            .map(ComponentValue::clone)
            .collect(toList());
        this.memberSelections = StreamUtils.streamOfNullable(from.getMemberSelections())
            .map(selection -> (MemberSelection) selection.clone())
            .collect(toList());
    }

    @Override
    public DataKey clone() {
        return new DataKeyImpl(this);
    }
}
