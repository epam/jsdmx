package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Set;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class OrganisationSchemeImpl<T extends Organisation<T>>
    extends ItemSchemeImpl<T>
    implements OrganisationScheme<T> {

    public OrganisationSchemeImpl(OrganisationScheme<T> from) {
        super(Objects.requireNonNull(from));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganisationScheme)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganisationScheme)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }
}
