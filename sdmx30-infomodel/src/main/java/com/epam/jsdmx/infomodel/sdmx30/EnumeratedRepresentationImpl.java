package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@EqualsAndHashCode
public class EnumeratedRepresentationImpl implements Representation {

    private final ArtefactReference codelist;

    public EnumeratedRepresentationImpl(EnumeratedRepresentationImpl from) {
        Objects.requireNonNull(from);
        this.codelist = Optional.ofNullable(from.getCodelist())
            .map(ArtefactReference::clone)
            .orElse(null);
    }

    @Override
    public boolean isEnumerated() {
        return true;
    }

    @Override
    public ArtefactReference enumerated() {
        return codelist;
    }

    @Override
    public Set<Facet> nonEnumerated() {
        return Set.of();
    }

    @Override
    public EnumeratedRepresentationImpl clone() {
        return new EnumeratedRepresentationImpl(this);
    }
}
