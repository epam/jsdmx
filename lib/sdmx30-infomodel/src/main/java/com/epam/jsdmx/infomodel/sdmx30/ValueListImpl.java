package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ValueListImpl
    extends EnumeratedListImpl<ValueItem>
    implements ValueList {

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.VALUE_LIST;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return Set.of();
    }

    @Override
    protected ValueListImpl createInstance() {
        return new ValueListImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ValueList)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ValueList)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }

    @Override
    public ValueList toStub() {
        return toStub(createInstance());
    }

    @Override
    public ValueList toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}