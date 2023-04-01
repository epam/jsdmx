package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MetadataAttributeRefImpl
    extends AttributeComponentImpl
    implements MetadataAttributeRef {

    // MetadataAttributeRef.id is ref to MetadataAttribute in attached msd
    private AttributeRelationship metadataRelationship;

    public MetadataAttributeRefImpl(MetadataAttributeRef from) {
        super(Objects.requireNonNull(from));
        this.metadataRelationship = (AttributeRelationship) from.getMetadataRelationship().clone();
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.METADATA_ATTRIBUTE;
    }

    @Override
    public MetadataAttributeRefImpl clone() {
        return new MetadataAttributeRefImpl(this);
    }
}
