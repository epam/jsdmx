package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataAttributeImpl
    extends AttributeComponentImpl
    implements DataAttribute {

    private int minOccurs;
    private int maxOccurs = Integer.MAX_VALUE;
    private AttributeRelationship attributeRelationship;
    private MeasureRelationship measureRelationship;
    private List<ArtefactReference> conceptRoles = new ArrayList<>();

    public DataAttributeImpl(DataAttribute from) {
        super(Objects.requireNonNull(from));
        this.minOccurs = from.getMinOccurs();
        this.maxOccurs = from.getMaxOccurs();

        this.attributeRelationship = Optional.ofNullable(from.getAttributeRelationship())
            .map(relationship -> (AttributeRelationship) relationship.clone())
            .orElse(null);

        this.measureRelationship = Optional.ofNullable(from.getMeasureRelationship())
            .map(MeasureRelationshipImpl::new)
            .orElse(null);

        this.conceptRoles = StreamUtils.streamOfNullable(from.getConceptRoles())
            .map(ArtefactReference::clone)
            .collect(toList());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.DATA_ATTRIBUTE;
    }

    public boolean isMandatory() {
        return minOccurs > 0;
    }

    @Override
    public DataAttributeImpl clone() {
        return new DataAttributeImpl(this);
    }
}
