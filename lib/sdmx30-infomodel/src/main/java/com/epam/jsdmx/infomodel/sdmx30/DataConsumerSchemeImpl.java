package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Set;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataConsumerSchemeImpl
    extends OrganisationSchemeImpl<DataConsumer>
    implements DataConsumerScheme {

    public DataConsumerSchemeImpl(DataConsumerScheme from) {
        super(Objects.requireNonNull(from));
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.DATA_CONSUMER_SCHEME;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return Set.of();
    }

    @Override
    protected DataConsumerSchemeImpl createInstance() {
        return new DataConsumerSchemeImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataConsumerScheme)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataConsumerScheme)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }

    @Override
    public DataConsumerScheme toStub() {
        return toStub(createInstance());
    }

    @Override
    public DataConsumerScheme toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
