package com.epam.jsdmx.infomodel.sdmx30;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransitionImpl extends IdentifiableArtefactImpl implements Transition {

    private String localId;
    private ArtefactReference targetProcessStep;
    private InternationalString condition;

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.TRANSITION;
    }
}
