package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * A language independent set of letters, numbers or symbols that represent
 * a concept whose meaning is described in a natural language.
 */
public interface Code extends Item {

    /**
     * @return the id of associated parent
     */
    String getParentId();

    /**
     * @return List of child codes. Associates the parent and the child codes.
     */
    @Override
    List<? extends Code> getHierarchy();
}
