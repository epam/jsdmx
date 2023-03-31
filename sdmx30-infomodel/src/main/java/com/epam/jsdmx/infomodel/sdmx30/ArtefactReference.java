package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Represents a reference to any {@link IdentifiableArtefact}.
 */
public interface ArtefactReference extends Copyable {

    /**
     * Id of the referenced artefact.
     */
    String getId();

    /**
     * Id of the maintaining organisation, a.k.a. agency.
     */
    String getOrganisationId();

    /**
     * Structure that specifies the version of the referenced artefact. Could be either a specific version or
     * a latest available version bounded by the wildcard scope. See section "4.3 Versioning" of the SDMX 3.0
     * <a href="https://sdmx.org/wp-content/uploads/SDMX_3-0-0_SECTION_6_FINAL-1_0.pdf">Technical notes</a>}
     */
    VersionReference getVersion();

    /**
     * String representation of the {@link VersionReference} object.
     */
    default String getVersionString() {
        final VersionReference version = getVersion();
        if (version == null) {
            return null;
        }
        return version.toString();
    }

    /**
     * Returns id of the item contained if this reference points to an item inside some maintainable structure.
     * Returns null if this reference points to a maintainable structure.
     */
    String getItemId();

    /**
     * Returns the structure class of the referenced artefact.
     */
    StructureClass getStructureClass();

    /**
     * Returns the structure class of the maintainable structure that contains the referenced artefact.
     */
    StructureClass getMaintainableStructureClass();

    /**
     * Returns the reference to the maintainable structure that contains the referenced artefact when item is referenced,
     * or returns itself otherwise.
     */
    ArtefactReference getMaintainableArtefactReference();

    /**
     * Returns this reference in the format of SDMX URN.
     */
    String getUrn();

    /**
     * Returns true if this object represents a reference to an item inside some maintainable structure and
     * false if this object represents a reference to a maintainable structure.
     */
    boolean isItemReference();

    @Override
    ArtefactReference clone();
}
