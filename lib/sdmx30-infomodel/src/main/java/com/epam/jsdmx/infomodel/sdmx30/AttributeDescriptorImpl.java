package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AttributeDescriptorImpl
    extends ComponentListImpl<DataAttribute>
    implements AttributeDescriptor {

    private List<MetadataAttributeRef> metadataAttributes = new ArrayList<>();

    public AttributeDescriptorImpl() {
    }

    public AttributeDescriptorImpl(AttributeDescriptor from) {
        super(from);
        this.metadataAttributes.addAll(from.getMetadataAttributes());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.ATTRIBUTE_DESCRIPTOR;
    }

    public List<MetadataAttributeRef> getMetadataAttributes() {
        return metadataAttributes;
    }
}
