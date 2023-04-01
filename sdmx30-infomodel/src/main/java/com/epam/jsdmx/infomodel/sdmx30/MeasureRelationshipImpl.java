package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MeasureRelationshipImpl implements MeasureRelationship {

    private List<String> measures = new ArrayList<>();

    public MeasureRelationshipImpl(MeasureRelationship from) {
        Objects.requireNonNull(from);
        this.measures = new ArrayList<>(from.getMeasures());
    }
}
