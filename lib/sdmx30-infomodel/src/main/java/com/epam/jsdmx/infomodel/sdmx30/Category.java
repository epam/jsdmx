package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * An item at any level within a classification, typically tabulation
 * categories, sections, subsections, divisions, subdivisions, groups,
 * subgroups, classes and subclasses.
 */
public interface Category extends Item {
    /**
     * @return List of categories. Associates the parent and the child Category
     */
    @Override
    List<? extends Category> getHierarchy();
}
