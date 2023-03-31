package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * All classes derived from the abstract class AnnotableArtefact may have Annotations (or
 * notes): this supports the need to add notes to all SDMX-ML elements. The Annotation is used
 * to convey extra information to describe any SDMX construct. This information may be in the
 * form of a URL reference and/or a multilingual text (represented by the association to
 * InternationalString).
 */
public interface AnnotableArtefact {

    /**
     * @return Collection of {@link Annotation} attached to this artefact.
     */
    List<Annotation> getAnnotations();

}
