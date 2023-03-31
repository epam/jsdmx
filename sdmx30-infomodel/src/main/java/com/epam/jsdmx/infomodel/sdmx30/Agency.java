package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * Responsible agency for maintaining artefacts such as statistical classifications,
 * glossaries, structural metadata such as Data and Metadata Structure Definitions,
 * Concepts andCode lists.
 */
public interface Agency extends Organisation<Agency> {

    /**
     * @return Hierarchy of Agencies
     */
    @Override
    List<? extends Agency> getHierarchy();
}
