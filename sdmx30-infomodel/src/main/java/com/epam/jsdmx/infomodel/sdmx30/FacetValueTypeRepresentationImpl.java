package com.epam.jsdmx.infomodel.sdmx30;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FacetValueTypeRepresentationImpl implements FacetValueTypeRepresentation {

    private FacetValueType type;

    public FacetValueTypeRepresentationImpl(FacetValueTypeRepresentation other) {
        this.type = other.getType();
    }

    @Override
    public FacetValueTypeRepresentation clone() {
        return new FacetValueTypeRepresentationImpl(this);
    }

    @Override
    public FacetValueType getType() {
        return type;
    }
}
