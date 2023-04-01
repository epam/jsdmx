package com.epam.jsdmx.infomodel.sdmx30;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GeoFeatureSetCodeImpl extends CodeImpl implements GeoFeatureSetCode {

    private String value;

    public GeoFeatureSetCodeImpl(GeoFeatureSetCodeImpl from) {
        super(from);
        this.value = from.value;
    }

    @Override
    public GeoFeatureSetCodeImpl clone() {
        return new GeoFeatureSetCodeImpl(this);
    }
}
