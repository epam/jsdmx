package com.epam.jsdmx.infomodel.sdmx30;

/**
 * The association between Codelists that may extend other Codelists
 */
public interface CodelistExtension extends Copyable {

    /**
     * @return A prefix to be used for a {@link Codelist} used in a extension,
     * in order to avoid Code Conflicts
     */
    String getPrefix();

    /**
     * @return the order that will be used when extending a Codelist, for resolving
     * Code conflicts. The latest {@link Codelist} used overrides any previous Codelist.
     */
    int getSequence();

    /**
     * @return {@link ArtefactReference} to a {@link Codelist}
     */

    ArtefactReference getCodelist();

    /**
     * @return the subset of {@link Code Codes} to be included/excluded when extending
     * a {@link Codelist}.
     */
    CodeSelection getCodeSelection();

    /**
     * @return the urn of a {@link Codelist}.
     */
    default String getCodelistUrn() {
        ArtefactReference codelist = getCodelist();
        if (codelist == null) {
            throw new IllegalStateException("Codelist reference must be present");
        }
        return codelist.getUrn();
    }
}
