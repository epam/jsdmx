package com.epam.jsdmx.serializer.sdmx30.common;

import java.time.Instant;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DatasetHeaderImpl implements DatasetHeader {
    private final String datasetId;
    private final ActionType action;
    private final DatasetStructureReference datasetStructureReference;
    private final ArtefactReference providerReference;
    private final Instant reportingBeginDate;
    private final Instant reportingEndDate;
    private final Instant validFrom;
    private final Instant validTo;
    private int publicationYear = -1;
    private String publicationPeriod;
    private String reportingYearStartDate;
}
