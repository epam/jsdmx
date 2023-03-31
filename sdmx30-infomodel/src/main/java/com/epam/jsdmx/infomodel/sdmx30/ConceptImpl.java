package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConceptImpl
    extends ItemImpl<Concept>
    implements Concept {

    private Representation coreRepresentation;
    private IsoConceptReferenceImpl isoConceptReference;

    public ConceptImpl(ConceptImpl from) {
        super(Objects.requireNonNull(from));
        this.coreRepresentation = Optional.ofNullable(from.getCoreRepresentation())
            .map(a -> (Representation) a.clone())
            .orElse(null);
        if (from.getIsoConceptReference() != null) {
            this.isoConceptReference = new IsoConceptReferenceImpl(from.getIsoConceptReference());
        }
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.CONCEPT;
    }

    @Override
    public ConceptImpl clone() {
        return new ConceptImpl(this);
    }
}
