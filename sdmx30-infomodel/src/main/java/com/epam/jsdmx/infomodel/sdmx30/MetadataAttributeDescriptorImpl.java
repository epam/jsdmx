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
public class MetadataAttributeDescriptorImpl
    extends ComponentListImpl<MetadataAttribute>
    implements MetadataAttributeDescriptor {

    public MetadataAttributeDescriptorImpl(MetadataAttributeDescriptor from) {
        super(Objects.requireNonNull(from));
    }

    @Override
    public StructureClassImpl getStructureClass() {
        return StructureClassImpl.METADATA_ATTRIBUTE_DESCRIPTOR;
    }
}
