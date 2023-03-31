package com.epam.jsdmx.infomodel.sdmx30;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProcessArtefactImpl implements ProcessArtefact {

    private String localId;
    private ArtefactReference artefact;

    public ProcessArtefactImpl(ProcessArtefact from) {
        this.localId = from.getLocalId();
        this.artefact = from.getArtefact();
    }
}
