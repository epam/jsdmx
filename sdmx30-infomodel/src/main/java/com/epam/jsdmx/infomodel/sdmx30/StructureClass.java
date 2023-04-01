package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Type of the sdmx Artefact
 */
public interface StructureClass {
    /**
     * @return name of the artefact class, similarly to {@link Class#getSimpleName()}
     */
    String getSimpleName();

    /**
     * @return The fully qualified SDMX-IM class name of the artefact type which includes package
     */
    String getFullyQualifiedName();

    /**
     * @return human readable name of the class, which may include spaces and other characters illegal for Java class names
     */
    String getPrintableName();

}
