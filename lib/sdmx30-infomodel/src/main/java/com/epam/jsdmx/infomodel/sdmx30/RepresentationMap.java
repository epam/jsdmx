package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * The RepresentationMap maps information from one or more sources, where the values for
 * each source are used in combination to derive the output value for one or more targets.
 */
public interface RepresentationMap extends MaintainableArtefact {

    @Override
    RepresentationMap toStub();

    @Override
    RepresentationMap toCompleteStub();

    /**
     * @return Collection of {@link RepresentationMapping}
     */
    List<RepresentationMapping> getRepresentationMappings();

    /**
     * @return Collection of associations to one or more Codelist, Valuelist,
     * or FacetValue – mixed types are permissible
     */
    List<ValueRepresentation> getSource();

    /**
     * @return Collection of associations to one or more Codelist, Valuelist,
     * or FacetValue – mixed types are permissible
     */
    List<ValueRepresentation> getTarget();
}
