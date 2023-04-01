package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConceptSchemeMapImpl extends ItemSchemeMapImpl implements ConceptSchemeMap {

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.CONCEPT_SCHEME_MAP;
    }

    @Override
    protected ConceptSchemeMapImpl createInstance() {
        return new ConceptSchemeMapImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConceptSchemeMap)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConceptSchemeMap)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }

    @Override
    public ConceptSchemeMap toStub() {
        return toStub(createInstance());
    }

    @Override
    public ConceptSchemeMap toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
