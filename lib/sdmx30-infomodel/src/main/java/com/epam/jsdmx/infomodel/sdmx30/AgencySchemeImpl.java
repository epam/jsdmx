package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Set;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AgencySchemeImpl
    extends OrganisationSchemeImpl<Agency>
    implements AgencyScheme {

    public AgencySchemeImpl(AgencyScheme from) {
        super(Objects.requireNonNull(from));
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.AGENCY_SCHEME;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return Set.of();
    }

    @Override
    protected AgencySchemeImpl createInstance() {
        return new AgencySchemeImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgencyScheme)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgencyScheme)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }

    @Override
    public AgencyScheme toStub() {
        return toStub(createInstance());
    }

    @Override
    public AgencyScheme toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}