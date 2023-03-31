package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

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
public class DimensionImpl
    extends DimensionComponentImpl
    implements Dimension {

    private List<ArtefactReference> conceptRoles = new ArrayList<>();

    public DimensionImpl(Dimension from) {
        super(Objects.requireNonNull(from));
        this.conceptRoles = StreamUtils.streamOfNullable(from.getConceptRoles())
            .map(IdentifiableArtefactReferenceImpl::new)
            .collect(toList());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.DIMENSION;
    }

    public List<ArtefactReference> getConceptRoles() {
        return conceptRoles;
    }

    @Override
    public DimensionImpl clone() {
        return new DimensionImpl(this);
    }
}
