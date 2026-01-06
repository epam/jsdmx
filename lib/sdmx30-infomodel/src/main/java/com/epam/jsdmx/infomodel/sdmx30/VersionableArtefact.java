package com.epam.jsdmx.infomodel.sdmx30;

import java.time.Instant;

/**
 * VersionableArtefact is an abstract class which inherits from NameableArtefact and
 * adds versioning ability to all classes derived from it, as explained in the SDMX versioning rules
 * in SDMX Standards Section 6 "Technical Notes", paragraph "4.3 Versioning".
 */
public interface VersionableArtefact extends NameableArtefact {
    /**
     * A version following SDMX versioning rules.
     */
    Version getVersion();

    /**
     * Date from which the version is valid parsed as {@link Instant}.
     *
     * @deprecated migrate to sting version of this accessor to ensure support of all variations of ISO 8601 syntax
     */
    @Deprecated(forRemoval = true)
    Instant getValidFrom();

    /**
     * Date from which version is superseded parsed as {@link Instant}.
     *
     * @deprecated migrate to sting version of this accessor to ensure support of all variations of ISO 8601 syntax
     */
    @Deprecated(forRemoval = true)
    Instant getValidTo();

    /**
     * Date from which the version is valid as string.
     */
    String getValidFromString();

    /**
     * Date from which version is superseded.
     */
    String getValidToString();
}
