package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * The IdentifiableObjectSelection acts like a reference, but it may also include wildcarding
 * part of the reference terms.
 */
public interface IdentifiableObjectSelection extends Copyable {

    /**
     * @return IdentifiableObjectSelection resolves into the IdentifiableArtefacts that the Metadatasets will refer to
     */
    List<ArtefactReference> getResolvesTo();
}
