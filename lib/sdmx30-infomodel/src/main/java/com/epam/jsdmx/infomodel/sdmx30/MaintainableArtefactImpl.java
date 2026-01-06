package com.epam.jsdmx.infomodel.sdmx30;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@NoArgsConstructor
public abstract class MaintainableArtefactImpl
    extends VersionableArtefactImpl
    implements MaintainableArtefact {

    private ArtefactReference maintainer;
    private boolean isExternalReference;
    private URL serviceUrl;
    private URL structureUrl;
    private boolean isStub;

    @SneakyThrows
    protected MaintainableArtefactImpl(MaintainableArtefact from) {
        super(Objects.requireNonNull(from));
        if (from.getMaintainer() != null) {
            this.maintainer = new MaintainableArtefactReference(from.getMaintainer());
        }
        this.isExternalReference = from.isExternalReference();
        if (from.getServiceUrl() != null) {
            this.serviceUrl = new URL(from.getServiceUrl().toString());
        }
        if (from.getStructureUrl() != null) {
            this.structureUrl = new URL(from.getStructureUrl().toString());
        }
    }

    protected <T extends MaintainableArtefactImpl> T toStub(T emptyArtefact) {
        emptyArtefact.setId(getId());
        emptyArtefact.setVersion(getVersion());
        emptyArtefact.setMaintainer(getMaintainer());
        emptyArtefact.setName(getName());
        emptyArtefact.setStub(true);
        return emptyArtefact;
    }

    protected <T extends MaintainableArtefactImpl> T toCompleteStub(T emptyArtefact) {
        T completeStub = toStub(emptyArtefact);
        completeStub.setDescription(getDescription());
        completeStub.setAnnotations(getAnnotations());
        return completeStub;
    }

    protected abstract MaintainableArtefactImpl createInstance();

    public ArtefactReference toReference() {
        return new MaintainableArtefactReference(
            getId(),
            getOrganizationId(),
            getVersion().toString(),
            getStructureClass()
        );
    }

    /**
     * convenience method to create copy a reference substituting structure class. May be handy
     * when declaring a list of referenced maintainable artefacts when given artefact holds references
     * to items, like reference to Code in a Codelist.
     *
     * @param itemReference  - full reference of an element inside parent
     * @param structureClass - parent structureClass
     * @return ArtefactReference (parent form)
     */
    protected ArtefactReference toReference(ArtefactReference itemReference, StructureClass structureClass) {
        return new MaintainableArtefactReference(
            itemReference.getId(),
            itemReference.getOrganisationId(),
            itemReference.getVersionString(),
            structureClass
        );
    }

    public String getOrganizationId() {
        return maintainer != null ? maintainer.getId() : null;
    }

    public void setOrganizationId(String organizationId) {
        MaintainableArtefactReference maintainerRef = new MaintainableArtefactReference();
        maintainerRef.setId(organizationId);
        this.maintainer = maintainerRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaintainableArtefact)) {
            return false;
        }
        MaintainableArtefact that = (MaintainableArtefact) o;
        return Objects.equals(getId(), that.getId())
            && Objects.equals(getOrganizationId(), that.getOrganizationId())
            && Objects.equals(getVersion(), that.getVersion())
            && Objects.equals(getStructureClass(), that.getStructureClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrganizationId(), getVersion(), getStructureClass());
    }

    @Override
    public boolean deepEquals(Object o) {
        return deepEquals(o, Set.of(MaintainableExclusions.ANNOTATIONS));
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaintainableArtefact)) {
            return false;
        }
        MaintainableArtefact that = (MaintainableArtefact) o;
        var result = Objects.equals(getId(), that.getId())
            && Objects.equals(getVersion(), that.getVersion())
            && isExternalReference() == that.isExternalReference()
            && Objects.equals(getMaintainer(), that.getMaintainer())
            && Objects.equals(getServiceUrl(), that.getServiceUrl())
            && Objects.equals(isStub(), that.isStub())
            && Objects.equals(getStructureUrl(), that.getStructureUrl());
        if (!exclusions.contains(MaintainableExclusions.ANNOTATIONS)) {
            result = result && Objects.equals(getAnnotations(), that.getAnnotations());
        }
        return result;
    }

    @Override
    public String getUrn() {
        return SdmxUrn.toFullUrnString(getStructureClass(), getOrganizationId(), getId(), getVersion());
    }

    @Override
    public String toString() {
        return toStringWithUrn();
    }

    @Override
    protected String toStringWithUrn() {
        return String.format("%s=%s", getStructureClass().getSimpleName(), SdmxUrn.parseToShortUrnString(getUrn()));
    }

    @Override
    public List<Link> getLinks() {
        // return self-link by default
        return List.of(
            new LinkImpl(
                null,
                "self",
                getUrn(),
                null,
                null,
                null,
                getStructureClass().getSimpleName().toLowerCase(),
                null
            )
        );
    }
}