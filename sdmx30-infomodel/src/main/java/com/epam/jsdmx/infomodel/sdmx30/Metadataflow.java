package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Abstract concept (i.e., the structure without any metadata) of a flow of metadata that providers will provide
 * for different reference periods. Specifies possible targets for metadata, via the Identifiable Object Selection.
 */
public interface Metadataflow extends StructureUsage, ConstrainableArtefact {

    @Override
    Metadataflow toStub();

    @Override
    Metadataflow toCompleteStub();

    /**
     * @return Collection of {@link IdentifiableObjectSelection} constructs, which resolve into the IdentifiableArtefacts
     * that the Metadatasets will refer to. The IdentifiableObjectSelection acts like a reference, but it may also
     * include wildcarding part of the reference terms.
     */
    List<IdentifiableObjectSelection> getSelections();
}
