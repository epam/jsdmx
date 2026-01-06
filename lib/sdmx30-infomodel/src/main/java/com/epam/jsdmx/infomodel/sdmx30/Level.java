package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * <ul>
 * <li>In a 'level based' hierarchy this describes a group of Codes which are characterised by homogeneous
 * coding, and where the parent of each Code in the group is at the same higher level of the Hierarchy.</li>
 *
 * <li>In a 'value based' hierarchy this describes information about the Hierarchical Codes at the
 * specified nesting level.</li>
 * </ul>
 */
public interface Level extends NameableArtefact {

    /**
     * @return Association to the Coding Format.
     */
    List<CodingFormat> getCodeFormat();

    /**
     * @return Association to a child Level of Level.
     */
    Level getChild();
}
