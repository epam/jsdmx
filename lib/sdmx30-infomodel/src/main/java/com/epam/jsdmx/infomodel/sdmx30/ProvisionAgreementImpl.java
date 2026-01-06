package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProvisionAgreementImpl extends MaintainableArtefactImpl implements ProvisionAgreement {

    private ArtefactReference dataProvider;
    private ArtefactReference controlledStructureUsage;
    private String dataSource;

    public ProvisionAgreementImpl(MetadataProvisionAgreement from) {
        if (from.getMetadataProvider() != null) {
            this.dataProvider = new MaintainableArtefactReference(from.getMetadataProvider());
        }
        if (from.getControlledStructureUsage() != null) {
            this.controlledStructureUsage = new MaintainableArtefactReference(from.getControlledStructureUsage());
        }
        this.dataSource = from.getDataSource();
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.PROVISION_AGREEMENT;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return Set.of(
            new CrossReferenceImpl(this, dataProvider),
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
    protected ProvisionAgreementImpl createInstance() {
        return new ProvisionAgreementImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProvisionAgreement)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProvisionAgreementImpl)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        ProvisionAgreementImpl that = (ProvisionAgreementImpl) o;
        return Objects.equals(getDataProvider(), that.getDataProvider())
            && Objects.equals(getControlledStructureUsage(), that.getControlledStructureUsage())
            && Objects.equals(getDataSource(), that.getDataSource());
    }
}
