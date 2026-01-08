package com.epam.jsdmx.serializer.sdmx30.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.InternationalString;

import lombok.Data;
import org.apache.commons.collections4.ListUtils;

@Data
public class MultilingualMetadataAttributeValue implements MetadataAttributeValue {

    private String id;
    private List<InternationalString> values;
    private List<MetadataAttributeValue> children;

    public MultilingualMetadataAttributeValue() {
        values = new ArrayList<>();
        children = new ArrayList<>();
    }

    public MultilingualMetadataAttributeValue(String id,
                                              List<InternationalString> values,
                                              List<MetadataAttributeValue> children) {
        this.id = id;
        this.values = values;
        this.children = children;
    }

    @Override
    public List<String> getStringValues() {
        return getLocalisedValues().stream()
            .map(InternationalString::getForDefaultLocale)
            .collect(Collectors.toList());
    }

    @Override
    public List<InternationalString> getLocalisedValues() {
        return ListUtils.emptyIfNull(values);
    }

    @Override
    public List<MetadataAttributeValue> getChildren() {
        return children;
    }
}
