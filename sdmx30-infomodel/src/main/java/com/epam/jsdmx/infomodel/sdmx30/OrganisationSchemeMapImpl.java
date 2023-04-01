package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrganisationSchemeMapImpl extends ItemSchemeMapImpl implements OrganisationSchemeMap {
    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.ORGANISATION_SCHEME_MAP;
    }

    @Override
    protected OrganisationSchemeMapImpl createInstance() {
        return new OrganisationSchemeMapImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganisationSchemeMap)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganisationSchemeMap)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }

    @Override
    public OrganisationSchemeMap toStub() {
        return toStub(createInstance());
    }

    @Override
    public OrganisationSchemeMap toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
