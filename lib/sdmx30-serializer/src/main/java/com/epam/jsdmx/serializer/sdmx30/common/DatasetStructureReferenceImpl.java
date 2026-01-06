package com.epam.jsdmx.serializer.sdmx30.common;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DatasetStructureReferenceImpl implements DatasetStructureReference {
    private final ArtefactReference dataStructureReference;
    private final ActionType action;
    private final String dimensionAtObservation;
}
