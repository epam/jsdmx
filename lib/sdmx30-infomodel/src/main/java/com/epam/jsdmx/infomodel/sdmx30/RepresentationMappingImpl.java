package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RepresentationMappingImpl
    extends AnnotableArtefactImpl
    implements RepresentationMapping {

    private Instant validFrom;
    private Instant validTo;
    private List<MappedValue> sourceValues = new ArrayList<>();
    private List<TargetValue> targetValues = new ArrayList<>();

    public RepresentationMappingImpl(RepresentationMapping from) {
        super(Objects.requireNonNull(from));
        this.validFrom = from.getValidFrom();
        this.validTo = from.getValidTo();

        this.sourceValues = StreamUtils.streamOfNullable(from.getSourceValues())
            .map(MappedValueImpl::new)
            .collect(toList());

        this.targetValues = StreamUtils.streamOfNullable(from.getTargetValues())
            .map(TargetValueImpl::new)
            .collect(toList());
    }

}
