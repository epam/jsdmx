package com.epam.jsdmx.infomodel.sdmx30;

/**
 * The link to the Concept that defines its name and semantic
 */
public interface DimensionComponent extends Component {

    /**
     * @return the order of dimension in {@link DataStructureDefinition}
     */
    int getOrder();

    /**
     * @return whether the implementation is {@link TimeDimension} or not
     */
    boolean isTimeDimension();
}
