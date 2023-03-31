package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MaintainableArtefactReference implements ArtefactReference {

    private String id;
    private String organisationId;
    private StructureClass structureClass;
    private VersionReference version;

    public MaintainableArtefactReference(String id, String organisationId, VersionReference version, StructureClass structureClass) {
        this.id = id;
        this.organisationId = organisationId;
        this.version = version;
        this.structureClass = structureClass;
    }

    public MaintainableArtefactReference(String id, String organisationId, Version version, StructureClass type) {
        this(id, organisationId, VersionReference.createFromVersion(version), type);
    }

    public MaintainableArtefactReference(String id, String organisationId, String versionString, StructureClass type) {
        this(id, organisationId, VersionReference.createFromString(versionString), type);
    }

    public MaintainableArtefactReference(String urn) {
        final UrnComponents urnComponents = SdmxUrn.getUrnComponents(urn);
        this.organisationId = urnComponents.getAgency();
        this.id = urnComponents.getId();
        this.version = VersionReference.createFromString(urnComponents.getVersion());
        this.structureClass = getType(urn);
    }

    public MaintainableArtefactReference(ArtefactReference from) {
        Objects.requireNonNull(from);
        this.id = from.getId();
        this.organisationId = from.getOrganisationId();
        this.version = from.getVersion();
        this.structureClass = from.getStructureClass();
    }

    @Override
    public MaintainableArtefactReference clone() {
        return new MaintainableArtefactReference(this);
    }

    private StructureClass getType(String urn) {
        return SdmxUrn.getType(urn, getTypeResolver())
            .orElseThrow(() -> new IllegalArgumentException("No such artefact type: " + urn));
    }

    protected Function<String, Optional<? extends StructureClass>> getTypeResolver() {
        return StructureClassImpl::getByFullyQualifiedName;
    }

    @Override
    public String getItemId() {
        return null;
    }

    @Override
    public StructureClass getMaintainableStructureClass() {
        return structureClass;
    }

    @Override
    public ArtefactReference getMaintainableArtefactReference() {
        return this;
    }

    public String getUrn() {
        return SdmxUrn.toFullUrnString(structureClass, organisationId, id, version.toString());
    }

    @Override
    public VersionReference getVersion() {
        return version;
    }

    @Override
    public boolean isItemReference() {
        return false;
    }

    @Override
    public String toString() {
        return (structureClass != null ? structureClass.getSimpleName() : "null") + "=" + organisationId + ":" + id + "(" + getVersionString() + ")";
    }
}
