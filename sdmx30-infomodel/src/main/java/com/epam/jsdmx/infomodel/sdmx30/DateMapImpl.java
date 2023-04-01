package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class DateMapImpl
    extends IdentifiableArtefactImpl
    implements DateMap {

    private String frequencyDimension;
    private String targetFrequencyId;
    private YearStart yearStart;
    private ResolvePeriod resolvePeriod;
    private List<FrequencyFormatMapping> mappedFrequencies = new ArrayList<>();


    public DateMapImpl(DateMap from) {
        super(Objects.requireNonNull(from));
        this.frequencyDimension = from.getFrequencyDimension();
        this.yearStart = from.getYearStart();
        this.resolvePeriod = from.getResolvePeriod();
        this.mappedFrequencies = StreamUtils.streamOfNullable(from.getMappedFrequencies())
            .map(FrequencyFormatMappingImpl::new)
            .collect(toList());
    }

    public List<FrequencyFormatMapping> getMappedFrequencies() {
        return mappedFrequencies;
    }

}
