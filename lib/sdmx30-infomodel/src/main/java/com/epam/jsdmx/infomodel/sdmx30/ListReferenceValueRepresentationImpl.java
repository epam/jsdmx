package com.epam.jsdmx.infomodel.sdmx30;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListReferenceValueRepresentationImpl implements ListReferenceValueRepresentation {

    private ArtefactReference reference;

    public ListReferenceValueRepresentationImpl(ListReferenceValueRepresentation other) {
        this.reference = other.getListReference();
    }

    @Override
    public Object clone() {
        return new ListReferenceValueRepresentationImpl(this);
    }

    @Override
    public ArtefactReference getListReference() {
        return reference;
    }
}
