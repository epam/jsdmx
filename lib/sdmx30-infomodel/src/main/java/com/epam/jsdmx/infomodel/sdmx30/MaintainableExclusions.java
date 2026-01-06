package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Basic implementation for {@link MaintainableArtefactImpl} which provides ability
 * to exclude annotations from deep equals. Subject to future extension.
 */
public enum MaintainableExclusions implements DeepEqualsExclusion {
    ANNOTATIONS
}
