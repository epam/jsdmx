package com.epam.jsdmx.infomodel.sdmx30;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GridCodeImpl extends CodeImpl implements GridCode {

    private String geoCell;

    public GridCodeImpl(GridCodeImpl from) {
        super(from);
        this.geoCell = from.geoCell;
    }

    @Override
    public String getGeoCell() {
        return geoCell;
    }

    @Override
    public GridCodeImpl clone() {
        return new GridCodeImpl(this);
    }
}
