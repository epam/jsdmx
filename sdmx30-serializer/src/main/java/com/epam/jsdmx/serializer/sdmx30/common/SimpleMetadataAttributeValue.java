package com.epam.jsdmx.serializer.sdmx30.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.InternationalString;

import lombok.Data;
import org.apache.commons.collections4.ListUtils;

@Data
public class SimpleMetadataAttributeValue implements MetadataAttributeValue {
    private String id;
    private List<String> values;
    private List<MetadataAttributeValue> children;

    public SimpleMetadataAttributeValue() {
        values = new ArrayList<>();
        children = new ArrayList<>();
    }

    public SimpleMetadataAttributeValue(String id,
                                        List<String> values,
                                        List<MetadataAttributeValue> children) {
        this.id = id;
        this.values = values;
        this.children = children;
    }

    public boolean isEmpty(SimpleMetadataAttributeValue metadataAttributeValue) {
        return metadataAttributeValue.getValues().isEmpty();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<String> getStringValues() {
        return ListUtils.emptyIfNull(values);
    }

    @Override
    public List<InternationalString> getLocalisedValues() {
        return getStringValues().stream()
            .map(InternationalString::new)
            .collect(Collectors.toList());
    }
}
