package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class EnumeratedListImpl<T extends EnumeratedItem>
    extends MaintainableArtefactImpl
    implements EnumeratedList<T> {

    private List<T> items = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnumeratedList)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnumeratedList)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        EnumeratedList<?> that = (EnumeratedListImpl<?>) o;
        return Objects.equals(getItems(), that.getItems());
    }
}
