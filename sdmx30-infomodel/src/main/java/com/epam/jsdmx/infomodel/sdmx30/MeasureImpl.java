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
public class MeasureImpl
    extends ComponentImpl
    implements Measure {

    private int minOccurs = 1;
    private int maxOccurs = 1;
    private List<ArtefactReference> conceptRoles = new ArrayList<>();

    public MeasureImpl(Measure from) {
        super(Objects.requireNonNull(from));
        this.minOccurs = from.getMinOccurs();
        this.maxOccurs = from.getMaxOccurs();
        this.conceptRoles = StreamUtils.streamOfNullable(from.getConceptRoles())
            .map(IdentifiableArtefactReferenceImpl::new)
            .collect(toList());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.MEASURE;
    }

    @Override
    public MeasureImpl clone() {
        return new MeasureImpl(this);
    }
}
