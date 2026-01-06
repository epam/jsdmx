package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class HierarchicalCodeImpl
    extends IdentifiableArtefactImpl
    implements HierarchicalCode {

    private ArtefactReference code;
    private Instant validFrom;
    private Instant validTo;
    private String levelId;
    private List<HierarchicalCode> hierarchicalCodes = new ArrayList<>();

    public HierarchicalCodeImpl(HierarchicalCode from) {
        super(Objects.requireNonNull(from));
        if (from.getCode() != null) {
            this.code = new IdentifiableArtefactReferenceImpl(from.getCode());
        }
        this.validFrom = from.getValidFrom();
        this.validTo = from.getValidTo();
        this.levelId = from.getLevelId();
        this.hierarchicalCodes = StreamUtils.streamOfNullable(from.getHierarchicalCodes())
            .map(HierarchicalCodeImpl::new)
            .collect(toList());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.HIERARCHICAL_CODE;
    }

    @Override
    public List<HierarchicalCode> getHierarchicalCodes() {
        return hierarchicalCodes;
    }

}
