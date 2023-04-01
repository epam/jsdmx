package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Describes in textual form the computations involved in the process.
 */
public interface Computation extends AnnotableArtefact, Copyable {
    /**
     * Distinguishes between Computations in the same Process.
     */
    String getLocalId();

    /**
     * Information about the Software Language that is used to perform the computation.
     */
    String getSoftwareLanguage();

    /**
     * Information about the Software Package that is used to perform the computation.
     */
    String getSoftwarePackage();

    /**
     * Information about the Software Version that is used to perform the computation.
     */
    String getSoftwareVersion();

    /**
     * Text describing or giving additional information about the computation.
     * This can be in multiple language variants.
     */
    InternationalString getDescription();
}
