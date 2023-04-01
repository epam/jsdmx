package com.epam.jsdmx.infomodel.sdmx30;

/**
 * A metadata concept that identifies the component in the
 * key structure that has the role of 'time'.
 */
public interface TimeDimension extends DimensionComponent {
    @Override
    default boolean isTimeDimension() {
        return true;
    }
}
