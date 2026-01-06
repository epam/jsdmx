package com.epam.jsdmx.infomodel.sdmx30;

import java.time.Instant;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EpochMapImpl
    extends DateMapImpl
    implements EpochMap {

    private Instant basePeriod;
    private EpochPeriodType epochPeriod;
    private String targetFrequencyId;

    public EpochMapImpl(EpochMap from) {
        super(Objects.requireNonNull(from));
        this.basePeriod = from.getBasePeriod();
        this.epochPeriod = from.getEpochPeriod();
        this.targetFrequencyId = from.getTargetFrequencyId();
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.EPOCH_MAP;
    }
}