package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportingTaxonomyMapImpl extends ItemSchemeMapImpl implements ReportingTaxonomyMap {
    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.REPORTING_TAXONOMY_MAP;
    }

    @Override
    protected ReportingTaxonomyMapImpl createInstance() {
        return new ReportingTaxonomyMapImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportingTaxonomyMap)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportingTaxonomyMap)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }

    @Override
    public ReportingTaxonomyMap toStub() {
        return toStub(createInstance());
    }

    @Override
    public ReportingTaxonomyMap toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
