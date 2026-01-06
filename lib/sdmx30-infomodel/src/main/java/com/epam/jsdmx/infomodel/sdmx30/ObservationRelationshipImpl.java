package com.epam.jsdmx.infomodel.sdmx30;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ObservationRelationshipImpl implements ObservationRelationship {

    @Override
    public ObservationRelationshipImpl clone() {
        return new ObservationRelationshipImpl();
    }

}
