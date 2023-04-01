package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * The StructureMap defines how the structure of a source DataStructureDefinition
 * relates to the structure of the target DataStructureDefinition.
 */
public interface StructureMap extends MaintainableArtefact {

    @Override
    StructureMap toStub();

    @Override
    StructureMap toCompleteStub();

    /**
     * @return Association to the source Data Structure.
     */
    ArtefactReference getSource();

    /**
     * @return Association to the target Data Structure
     */
    ArtefactReference getTarget();

    /**
     * @return Collection of {@link ComponentMap}
     */
    List<ComponentMap> getComponentMaps();

    /**
     * @return Collection of {@link FixedValueMap}
     */
    List<FixedValueMap> getFixedComponentMaps();

    /**
     * @return Collection of {@link EpochMap}
     */
    List<EpochMap> getEpochMaps();

    /**
     * @return Collection of {@link DatePatternMap}
     */
    List<DatePatternMap> getDatePatternMaps();
}
