package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

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
public class MetadataTargetRegionImpl implements MetadataTargetRegion {

    private boolean included;
    private List<MemberSelection> memberSelections = new ArrayList<>();
    private Instant validTo;
    private Instant validFrom;

    public MetadataTargetRegionImpl(MetadataTargetRegion from) {
        this.included = from.isIncluded();
        this.memberSelections = StreamUtils.streamOfNullable(from.getMemberSelections())
            .map(selection -> (MemberSelection) selection.clone())
            .collect(toList());
        this.validTo = from.getValidTo();
        this.validFrom = from.getValidFrom();
    }

    @Override
    public MetadataTargetRegionImpl clone() {
        return new MetadataTargetRegionImpl(this);
    }
}
