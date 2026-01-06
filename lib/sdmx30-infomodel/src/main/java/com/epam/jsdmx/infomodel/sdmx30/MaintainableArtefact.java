package com.epam.jsdmx.infomodel.sdmx30;

import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * MaintainableArtefact further adds the ability for derived classes to be maintained via its
 * association to an {@link Organisation}, and adds locational information (i.e., from where the object
 * can be retrieved).
 * <p>
 * The inheritance chain from {@link AnnotableArtefact} through to {@link MaintainableArtefact}
 * allows SDMX classes to inherit the features they need, from simple annotation, through identity,
 * naming, to versioning and maintenance.
 */
public interface MaintainableArtefact extends VersionableArtefact {

    /**
     * If set to "true" it indicates that the content of the object is held externally.
     */
    boolean isExternalReference();

    /**
     * The URL of an SDMX-compliant web service from which the external object can be retrieved.
     */
    URL getServiceUrl();

    /**
     * The URL of an SDMX-ML document containing the external object.
     */
    URL getStructureUrl();

    /**
     * References to other maintainable artefacts that this artefact depends on.
     */
    Set<CrossReference> getReferencedArtefacts();

    /**
     * Association to the {@link Organisation} responsible for maintaining the artefact.
     */
    ArtefactReference getMaintainer();

    /**
     * A shortcut to get maintainer's (see {@link MaintainableArtefact#getMaintainer}) organisation id.
     * Previously referred to as the "Agency".
     */
    String getOrganizationId();

    /**
     * Stub of this artefact, i.e. only containing identification information and the artefacts' name.
     */
    MaintainableArtefact toStub();

    /**
     * Complete stub of this artefact, i.e. only containing identification information, the artefacts' name, description and annotations.
     */
    MaintainableArtefact toCompleteStub();

    /**
     * Reference object to this artefact.
     */
    ArtefactReference toReference();

    /**
     * Indicates that this artefact is a stub - either complete one or not, i.e. only containing identification information.
     *
     * @return true if this artefact is a stub, false otherwise
     */
    boolean isStub();

    /**
     * Same as {@link #deepEquals(Object, Set)} with exclusions set to <code>Set.of({@link MaintainableExclusions#ANNOTATIONS})</code>.
     * Since annotations most often convey some metadata, it seems like a sensible default to provide dedicated method with
     * annotations excluded from comparison.
     */
    boolean deepEquals(Object o);

    /**
     * Deep comparison of this artefact with another one, meaning that all fields are compared up the inheritance chain as opposed
     * to {@link Object#equals(Object)} which only compares identification information (id, agency, version), optionally including
     * annotations.
     *
     * @param o,          the object to compare with
     * @param exclusions, set of {@link DeepEqualsExclusion} that mark fields to be excluded from the comparison
     * @return true if the objects are equal, false otherwise
     */
    boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions);

    /**
     * Retrurns collection of links to additional resources, including external resources.
     */
    List<Link> getLinks();

}
