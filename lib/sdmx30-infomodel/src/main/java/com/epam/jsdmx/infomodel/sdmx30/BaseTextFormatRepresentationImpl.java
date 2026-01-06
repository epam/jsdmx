package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toSet;

import java.util.Objects;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@AllArgsConstructor
public class BaseTextFormatRepresentationImpl implements Representation {

    private final Set<Facet> facets;

    public BaseTextFormatRepresentationImpl(FacetValueType valueType) {
        facets = Set.of(new BaseFacetImpl(valueType));
    }

    public BaseTextFormatRepresentationImpl(BaseTextFormatRepresentationImpl from) {
        Objects.requireNonNull(from);
        this.facets = StreamUtils.streamOfNullable(from.getFacets())
            .map(Facet::clone)
            .collect(toSet());
    }

    @Override
    public boolean isEnumerated() {
        return false;
    }

    @Override
    public ArtefactReference enumerated() {
        return null;
    }

    @Override
    public Set<Facet> nonEnumerated() {
        return this.facets;
    }

    @Override
    public BaseTextFormatRepresentationImpl clone() {
        return new BaseTextFormatRepresentationImpl(this);
    }
}
