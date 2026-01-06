package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ComponentMapImpl
    extends AnnotableArtefactImpl
    implements ComponentMap {

    private List<String> source;
    private List<String> target;
    // here must be representationMap, epochMap or datePatternMap
    private ArtefactReference representationMap;

    public ComponentMapImpl(ComponentMap from) {
        super(Objects.requireNonNull(from));
        this.source = StreamUtils.streamOfNullable(from.getSource()).collect(toList());
        this.target = StreamUtils.streamOfNullable(from.getTarget()).collect(toList());
        this.representationMap = Optional.ofNullable(from.getRepresentationMap())
            .map(ArtefactReference::clone)
            .orElse(null);
    }

}
