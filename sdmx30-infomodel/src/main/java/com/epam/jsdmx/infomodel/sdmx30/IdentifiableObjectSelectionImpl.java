package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

@Getter
@Setter
@NoArgsConstructor
public class IdentifiableObjectSelectionImpl implements IdentifiableObjectSelection {

    private List<ArtefactReference> resolvesTo = new ArrayList<>();

    public IdentifiableObjectSelectionImpl(IdentifiableObjectSelection from) {
        resolvesTo = new ArrayList<>(from.getResolvesTo());
    }

    @Override
    public List<ArtefactReference> getResolvesTo() {
        return resolvesTo;
    }

    @Override
    public Object clone() {
        return new IdentifiableObjectSelectionImpl(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IdentifiableObjectSelectionImpl)) {
            return false;
        }

        IdentifiableObjectSelectionImpl that = (IdentifiableObjectSelectionImpl) o;

        if (CollectionUtils.isEmpty(resolvesTo) && CollectionUtils.isEmpty(that.resolvesTo)) {
            return true;
        }

        return Objects.equals(resolvesTo, that.resolvesTo);
    }

    @Override
    public int hashCode() {
        return resolvesTo != null ? resolvesTo.hashCode() : 0;
    }
}
