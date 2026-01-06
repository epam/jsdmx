package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataKeySetImpl implements DataKeySet {

    private boolean included;
    private List<DataKey> keys = new ArrayList<>();

    public DataKeySetImpl(DataKeySetImpl from) {
        this.included = from.isIncluded();
        this.keys = from.getKeys();
    }

    @Override
    public DataKeySetImpl clone() {
        return new DataKeySetImpl(this);
    }
}
