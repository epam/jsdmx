package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Set;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrganisationUnitSchemeImpl
    extends OrganisationSchemeImpl<OrganisationUnit>
    implements OrganisationUnitScheme {

    public OrganisationUnitSchemeImpl(OrganisationUnitScheme from) {
        super(Objects.requireNonNull(from));
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.ORGANISATION_UNIT_SCHEME;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return Set.of();
    }

    @Override
    protected OrganisationUnitSchemeImpl createInstance() {
        return new OrganisationUnitSchemeImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganisationUnitScheme)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganisationUnitScheme)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }

    @Override
    public OrganisationUnitScheme toStub() {
        return toStub(createInstance());
    }

    @Override
    public OrganisationUnitScheme toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
