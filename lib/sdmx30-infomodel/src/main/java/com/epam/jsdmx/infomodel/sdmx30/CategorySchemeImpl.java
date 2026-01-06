package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategorySchemeImpl
    extends ItemSchemeImpl<Category>
    implements CategoryScheme {

    public CategorySchemeImpl(CategoryScheme from) {
        super(Objects.requireNonNull(from));
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.CATEGORY_SCHEME;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return Set.of();
    }

    @Override
    protected CategorySchemeImpl createInstance() {
        return new CategorySchemeImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryScheme)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryScheme)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }

    @Override
    public CategoryScheme toStub() {
        return toStub(createInstance());
    }

    @Override
    public CategoryScheme toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
