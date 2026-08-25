package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LazyMaintainableArtefactImpl extends MaintainableArtefactImpl {

    private boolean targetArtefact;
    private ArtefactReference artefactReference;
    private Set<CrossReference> referencedArtefacts;

    public LazyMaintainableArtefactImpl(ArtefactReference reference) {
        setId(reference.getId());
        setVersion(Version.createFromString(reference.getVersionString()));
        setOrganizationId(reference.getOrganisationId());
        this.artefactReference = reference;
    }

    public LazyMaintainableArtefactImpl(MaintainableArtefact maintainableArtefact) {
        super(maintainableArtefact);
    }

    @Override
    public StructureClass getStructureClass() {
        return artefactReference.getStructureClass();
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return referencedArtefacts;
    }

    @Override
    public ArtefactReference toReference() {
        return this.artefactReference;
    }

    @Override
    public MaintainableArtefact toStub() {
        return this;
    }

    @Override
    public MaintainableArtefact toCompleteStub() {
        return this;
    }

    @Override
    protected MaintainableArtefactImpl createInstance() {
        return this;
    }
}