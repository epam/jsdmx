package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Set;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MetadataProviderSchemeImpl
    extends OrganisationSchemeImpl<MetadataProvider>
    implements MetadataProviderScheme {

    public MetadataProviderSchemeImpl(MetadataProviderScheme from) {
        super(Objects.requireNonNull(from));
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.METADATA_PROVIDER_SCHEME;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return Set.of();
    }

    @Override
    protected MetadataProviderSchemeImpl createInstance() {
        return new MetadataProviderSchemeImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetadataProviderScheme)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetadataProviderScheme)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }

    @Override
    public MetadataProviderScheme toStub() {
        return toStub(createInstance());
    }

    @Override
    public MetadataProviderScheme toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
