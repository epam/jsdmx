package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Set;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataProviderSchemeImpl
    extends OrganisationSchemeImpl<DataProvider>
    implements DataProviderScheme {

    public DataProviderSchemeImpl(DataProviderScheme from) {
        super(Objects.requireNonNull(from));
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.DATA_PROVIDER_SCHEME;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return Set.of();
    }

    @Override
    protected DataProviderSchemeImpl createInstance() {
        return new DataProviderSchemeImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataProviderScheme)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataProviderScheme)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }

    @Override
    public DataProviderScheme toStub() {
        return toStub(createInstance());
    }

    @Override
    public DataProviderScheme toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
