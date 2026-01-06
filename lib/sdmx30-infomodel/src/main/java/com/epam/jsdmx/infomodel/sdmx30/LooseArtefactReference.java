package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Similar to {@link ArtefactReference}, but assumes that the version is specified via
 * {@link LooseVersionReference} instead of {@link VersionReference}.
 */
public interface LooseArtefactReference {
    /**
     * @return referenced artefact's id
     */
    String getId();

    /**
     * @return id of the maintaining organisation, a.k.a. agency
     */
    String getOrganisationId();

    /**
     * @return version of the referenced artefact, which might be wildcarded accroding to {@link LooseVersionReference} rules
     */
    LooseVersionReference getVersion();

    /**
     * @return id of the referenced identifiable item which is contained in some maintainable sturcture
     */
    String getItemId();

    /**
     * @return structure class of the referenced artefact
     */
    StructureClass getStructureClass();

    /**
     * @return structure class of the referenced artefact's maintainable structure
     */
    StructureClass getMaintainableStructureClass();

    /**
     * @return reference to maintainable structure that contains the referenced artefact when item is referenced, or returns itself otherwise
     */
    ArtefactReference getMaintainableArtefactReference();

    /**
     * @return this reference in the format of SDMX URN
     */
    String getUrn();

    /**
     * @return true if this reference points to an identifiable item inside some maintainable structure,
     * i.e. {@link LooseArtefactReference#getItemId()} != null, and false otherwise
     */
    boolean isItemReference();
}
