package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Specifies format information for the codes at this level in the
 * hierarchy such as whether the codes at the level are
 * alphabetic, numeric or alphanumeric and the code length.
 */
public interface CodingFormat {

    /**
     * @return {@link CodingFormat} information
     */
    Facet getCodingFormat();
}
