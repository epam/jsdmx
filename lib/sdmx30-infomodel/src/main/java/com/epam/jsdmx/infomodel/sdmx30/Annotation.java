package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Additional descriptive information attached to an object.
 */
public interface Annotation {
    /**
     * Identifier for the Annotation. It can be used to disambiguate one Annotation from another where
     * there are several Annotations for the same annotated object.
     */
    String getId();

    /**
     * A title used to identify an annotation.
     */
    String getTitle();

    /**
     * Specifies how the annotation is to be processed.
     */
    String getType();

    /**
     * A non-localised version of the Annotation content.
     */
    String getValue();

    /**
     * An International String provides the multilingual text content of the annotation via this role.
     */
    InternationalString getText();

    /**
     * A link to external descriptive text.
     */
    InternationalUri getUrl(); // todo: remove?

    List<Link> getLinks(); // this is what the spec tells
}
