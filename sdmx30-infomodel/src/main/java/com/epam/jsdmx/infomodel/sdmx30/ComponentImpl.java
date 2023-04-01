package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public abstract class ComponentImpl
    extends IdentifiableArtefactImpl
    implements Component {

    private ArtefactReference conceptIdentity;
    private Representation localRepresentation;

    public ComponentImpl(Component from) {
        super(Objects.requireNonNull(from));
        this.conceptIdentity = new IdentifiableArtefactReferenceImpl(from.getConceptIdentity());
        if (from.getLocalRepresentation() != null) {
            this.localRepresentation = (Representation) from.getLocalRepresentation().clone();
        }
    }

    public abstract Component clone();

}