package com.epam.jsdmx.infomodel.sdmx30;


import java.util.List;

/**
 * The subset of Codes to be included/excluded when extending a {@link Codelist}.
 */
public interface CodeSelection {

    /**
     * @return Collection of values based on {@link Code Codes} and their children.
     */
    List<MemberValue> getMembers();
}
