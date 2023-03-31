package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReportingCategoryImpl extends ItemImpl<ReportingCategory> implements ReportingCategory {

    private List<ArtefactReference> flows = new ArrayList<>();
    private List<ArtefactReference> structures = new ArrayList<>();

    public ReportingCategoryImpl(ReportingCategoryImpl from) {
        super(Objects.requireNonNull(from));
        structures = from.getStructures();
        flows = from.getFlows();
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.REPORTING_CATEGORY;
    }

    @Override
    public ReportingCategoryImpl clone() {
        return new ReportingCategoryImpl(this);
    }
}
