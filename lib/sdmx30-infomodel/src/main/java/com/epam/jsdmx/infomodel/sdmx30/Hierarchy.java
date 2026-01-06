package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * <br> A classification structure arranged in levels of detail from the broadest to the most detailed level.<br>
 * <p>
 * The basic principles of the Hierarchy are:
 * <ul>
 * <li> The Hierarchy is a specification of the structure of the Codes.</li>
 * <li> The Codes in the Hierarchy are not themselves a part of the artefact, rather they are
 * references to Codes in one or more external Codelists.</li>
 * <li> The hierarchy of Codes is specified in HierarchicalCode. This references the Code
 * and its immediate child HierarchicalCodes.</li>
 * </ul>
 */
public interface Hierarchy extends MaintainableArtefact {

    @Override
    Hierarchy toStub();

    @Override
    Hierarchy toCompleteStub();

    /**
     * @return Format level presence
     * <ul>
     * <li>If 'true', this indicates a hierarchy where the structure is arranged in levels of detail from
     * the broadest to the most detailed level.</li>
     * <li>If 'false', this indicates a hierarchy structure where the items in the hierarchy have no
     * formal level structure.</li>
     * </ul>
     */
    boolean isHasFormalLevels();

    /**
     * @return Association to the top Level in the Hierarchy
     */
    Level getLevel();

    /**
     * @return Association to the top-level Hierarchical Codes in the Hierarchy
     */
    List<HierarchicalCode> getCodes();
}
