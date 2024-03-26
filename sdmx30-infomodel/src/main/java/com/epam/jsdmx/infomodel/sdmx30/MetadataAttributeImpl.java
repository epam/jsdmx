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
public class MetadataAttributeImpl
    extends AttributeComponentImpl
    implements MetadataAttribute {

    private boolean isPresentational;
    private int minOccurs = 1;
    private int maxOccurs = 1;
    private List<MetadataAttribute> hierarchy = new ArrayList<>();

    public MetadataAttributeImpl(MetadataAttribute from) {
        super(Objects.requireNonNull(from));
        this.isPresentational = from.isPresentational();
        this.minOccurs = from.getMinOccurs();
        this.maxOccurs = from.getMaxOccurs();
        this.hierarchy = StreamUtils.streamOfNullable(hierarchy)
            .map(MetadataAttributeImpl::new)
            .collect(toList());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.METADATA_ATTRIBUTE;
    }

    @Override
    public boolean isMandatory() {
        return minOccurs > 0;
    }

    @Override
    public MetadataAttributeImpl clone() {
        return new MetadataAttributeImpl(this);
    }
}
