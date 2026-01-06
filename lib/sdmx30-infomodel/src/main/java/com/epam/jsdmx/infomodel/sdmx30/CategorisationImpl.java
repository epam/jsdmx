package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategorisationImpl
    extends MaintainableArtefactImpl
    implements Categorisation {

    private ArtefactReference categorizedArtefact;
    private ArtefactReference categorizedBy;

    private CategorisationImpl(CategorisationImpl from) {
        super(Objects.requireNonNull(from));
        if (from.getCategorizedArtefact() != null) {
            this.categorizedArtefact = new MaintainableArtefactReference(from.getCategorizedArtefact());
        }
        if (from.getCategorizedBy() != null) {
            this.categorizedBy = new IdentifiableArtefactReferenceImpl(from.getCategorizedBy());
        }
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.CATEGORISATION;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        if (categorizedBy == null && categorizedArtefact == null) {
            return Set.of(); // todo: raise discussion about details vs references
        }

        final var schemeReference = toReference(categorizedBy, StructureClassImpl.CATEGORY_SCHEME);

        return Set.of(
            new CrossReferenceImpl(this, schemeReference),
            new CrossReferenceImpl(this, categorizedArtefact)
        );
    }

    @Override
    protected CategorisationImpl createInstance() {
        return new CategorisationImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categorisation)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categorisation)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        Categorisation that = (CategorisationImpl) o;
        return Objects.equals(getCategorizedArtefact(), that.getCategorizedArtefact())
            && Objects.equals(getCategorizedBy(), that.getCategorizedBy());
    }

    @Override
    public Categorisation toStub() {
        return toStub(createInstance());
    }

    @Override
    public Categorisation toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
