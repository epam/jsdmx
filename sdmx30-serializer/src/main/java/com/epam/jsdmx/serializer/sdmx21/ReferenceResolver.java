package com.epam.jsdmx.serializer.sdmx21;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;

/**
 * Resolves wildcarded references, i.e. having wildcarded version (1.0.0+, 1.0+.0, 1+.0.0, etc.)
 */
@FunctionalInterface
public interface ReferenceResolver {
    ArtefactReference resolve(ArtefactReference reference);
}
