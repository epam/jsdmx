package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuperBuilder
public class CrossReferenceImpl
    implements CrossReference {

    @Getter
    private final MaintainableArtefact referencedFrom;
    @Getter
    private final ArtefactReference referencedTo;

    public CrossReferenceImpl(MaintainableArtefact referencedFrom, ArtefactReference ref) {
        this.referencedFrom = referencedFrom;
        this.referencedTo = ref;
    }

    public CrossReferenceImpl(CrossReferenceImpl from) {
        this.referencedFrom = from.referencedFrom;
        this.referencedTo = from.referencedTo;
    }

    @Override
    public String toString() {
        final var builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.append("from", referencedFrom.getUrn());
        builder.append("to", referencedTo.getUrn());
        return builder.build();
    }

    @Override
    public String getId() {
        return referencedTo.getId();
    }

    @Override
    public String getOrganisationId() {
        return referencedTo.getOrganisationId();
    }

    @Override
    public String getVersionString() {
        return referencedTo.getVersionString();
    }

    @Override
    public String getItemId() {
        return referencedTo.getItemId();
    }

    @Override
    public StructureClass getStructureClass() {
        return referencedTo.getStructureClass();
    }

    @Override
    public StructureClass getMaintainableStructureClass() {
        return referencedTo.getMaintainableStructureClass();
    }

    @Override
    public ArtefactReference getMaintainableArtefactReference() {
        return referencedTo.getMaintainableArtefactReference();
    }

    @Override
    public String getUrn() {
        return referencedTo.getUrn();
    }

    @Override
    public VersionReference getVersion() {
        return referencedTo.getVersion();
    }

    @Override
    public boolean isItemReference() {
        return referencedTo.isItemReference();
    }

    @Override
    public ArtefactReference clone() {
        return new CrossReferenceImpl(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CrossReferenceImpl)) {
            return false;
        }

        CrossReferenceImpl that = (CrossReferenceImpl) o;

        if (!Objects.equals(referencedFrom.toReference(), that.referencedFrom.toReference())) {
            return false;
        }
        return Objects.equals(referencedTo, that.referencedTo);
    }

    @Override
    public int hashCode() {
        int result = referencedFrom.toReference() != null ? referencedFrom.toReference().hashCode() : 0;
        result = 31 * result + (referencedTo != null ? referencedTo.hashCode() : 0);
        return result;
    }
}
