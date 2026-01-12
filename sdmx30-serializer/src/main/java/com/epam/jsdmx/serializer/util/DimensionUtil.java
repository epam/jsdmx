package com.epam.jsdmx.serializer.util;

import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.DimensionComponent;
import com.epam.jsdmx.infomodel.sdmx30.DimensionComponentImpl;

public final class DimensionUtil {
    private DimensionUtil() { }

    public static void populateZeroBasedOrder(List<DimensionComponent> dimensionComponents) {
        for (int i = 0; i < dimensionComponents.size(); i++) {
            var dimensionComponent = (DimensionComponentImpl) dimensionComponents.get(i);
            dimensionComponent.setOrder(i);
        }
    }
}
