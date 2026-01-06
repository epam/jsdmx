package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MetadataProvisionAgreementImpl
    extends MaintainableArtefactImpl implements MetadataProvisionAgreement {

    private ArtefactReference metadataProvider;
    private ArtefactReference controlledStructureUsage;
    private String dataSource;

    public MetadataProvisionAgreementImpl(MetadataProvisionAgreement from) {
        if (from.getMetadataProvider() != null) {
            this.metadataProvider = new MaintainableArtefactReference(from.getMetadataProvider());
        }
        if (from.getControlledStructureUsage() != null) {
            this.controlledStructureUsage = new MaintainableArtefactReference(from.getControlledStructureUsage());
        }
        this.dataSource = from.getDataSource();
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.METADATA_PROVISION_AGREEMENT;
    }

    @Override
    public MetadataProvisionAgreementImpl createInstance() {
        return new MetadataProvisionAgreementImpl();
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return Set.of(
            new CrossReferenceImpl(this, metadataProvider),
            new CrossReferenceImpl(this, controlledStructureUsage)
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetadataProvisionAgreement)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        if (!(o instanceof MetadataProvisionAgreement)) {
            return false;
        }
        MetadataProvisionAgreement other = (MetadataProvisionAgreement) o;
        return Objects.equals(metadataProvider, other.getMetadataProvider())
            && Objects.equals(controlledStructureUsage, other.getControlledStructureUsage())
            && Objects.equals(dataSource, other.getDataSource());
    }
}
