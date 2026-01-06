package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HierarchyAssociationImpl
    extends MaintainableArtefactImpl
    implements HierarchyAssociation {

    private ArtefactReference linkedHierarchy;
    private ArtefactReference linkedObject;
    private ArtefactReference contextObject;

    public HierarchyAssociationImpl(HierarchyAssociation from) {
        super(requireNonNull(from));
        linkedHierarchy = from.getLinkedHierarchy();
        linkedObject = from.getLinkedObject();
        contextObject = from.getContextObject();
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.HIERARCHY_ASSOCIATION;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return Set.of(
            new CrossReferenceImpl(this, linkedHierarchy),
            new CrossReferenceImpl(this, linkedObject),
            new CrossReferenceImpl(this, contextObject)
        );
    }

    @Override
    public MaintainableArtefact toStub() {
        return toStub(createInstance());
    }

    @Override
    public MaintainableArtefact toCompleteStub() {
        return toCompleteStub(createInstance());
    }

    @Override
    protected HierarchyAssociationImpl createInstance() {
        return new HierarchyAssociationImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HierarchyAssociationImpl)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object artefact, Set<DeepEqualsExclusion> exclusions) {
        if (this == artefact) {
            return true;
        }
        if (!(artefact instanceof HierarchyAssociationImpl)) {
            return false;
        }
        if (!super.deepEquals(artefact, exclusions)) {
            return false;
        }

        var that = (HierarchyAssociationImpl) artefact;

        if (!Objects.equals(linkedHierarchy, that.linkedHierarchy)) {
            return false;
        }
        if (!Objects.equals(linkedObject, that.linkedObject)) {
            return false;
        }
        return Objects.equals(contextObject, that.contextObject);
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + (linkedHierarchy != null ? linkedHierarchy.hashCode() : 0);
        result = PRIME * result + (linkedObject != null ? linkedObject.hashCode() : 0);
        result = PRIME * result + (contextObject != null ? contextObject.hashCode() : 0);
        return result;
    }
}
