package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategorySchemeMapImpl extends ItemSchemeMapImpl implements CategorySchemeMap {

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.CATEGORY_SCHEME_MAP;
    }

    @Override
    protected CategorySchemeMapImpl createInstance() {
        return new CategorySchemeMapImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorySchemeMap)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorySchemeMap)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }

    @Override
    public CategorySchemeMap toStub() {
        return toStub(createInstance());
    }

    @Override
    public CategorySchemeMap toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
