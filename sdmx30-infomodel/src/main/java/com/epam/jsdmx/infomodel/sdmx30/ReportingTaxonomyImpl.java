package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Set;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReportingTaxonomyImpl extends ItemSchemeImpl<ReportingCategory> implements ReportingTaxonomy {

    public ReportingTaxonomyImpl(ReportingTaxonomy from) {
        super(Objects.requireNonNull(from));
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.REPORTING_TAXONOMY;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return Set.of();
    }

    @Override
    protected ReportingTaxonomyImpl createInstance() {
        return new ReportingTaxonomyImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportingTaxonomy)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportingTaxonomy)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }

    @Override
    public ReportingTaxonomy toStub() {
        return toStub(createInstance());
    }

    @Override
    public ReportingTaxonomy toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
