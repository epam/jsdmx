package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DimensionRelationshipImpl implements DimensionRelationship {

    private List<String> dimensions = new ArrayList<>();
    private List<String> groupKeys = new ArrayList<>(); // refs to dimension group descriptor

    public DimensionRelationshipImpl(DimensionRelationship from) {
        Objects.requireNonNull(from);
        this.dimensions = new ArrayList<>(from.getDimensions());
        this.groupKeys = StreamUtils.streamOfNullable(from.getGroupKeys())
            .collect(toList());
    }

    @Override
    public List<String> getDimensions() {
        return dimensions;
    }

    @Override
    public List<String> getGroupKeys() {
        return groupKeys;
    }

    @Override
    public DimensionRelationshipImpl clone() {
        return new DimensionRelationshipImpl(this);
    }
}
