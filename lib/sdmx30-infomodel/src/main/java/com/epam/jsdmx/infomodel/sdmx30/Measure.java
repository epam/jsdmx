package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * The metadata concept that is the phenomenon to be measured in a data set. In a
 * data set the instance of the measure is often called the observation.
 */
public interface Measure extends Component {

    /**
     * @return The minimum required occurrences for the Measure. When equals to zero, the
     * Measure is conditional.
     */
    int getMinOccurs();

    /**
     * @return The maximum allowed occurrences for the Measure
     */
    int getMaxOccurs();

    /**
     * @return Reference to a concept role
     */
    List<ArtefactReference> getConceptRoles();
}
