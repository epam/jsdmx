package com.epam.jsdmx.serializer.sdmx30.common;

import java.time.Instant;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;

/**
 * Data collected in order to write dataset information
 */
public interface DatasetHeader {

    /**
     * @return Corresponding ID of a Dataset
     */
    String getDatasetId();

    /**
     * @return Defines the action to be taken by the recipient system
     * (information, append, replace, delete)
     */
    ActionType getAction();

    /**
     * @return Reference to data structure
     */
    DatasetStructureReference getDatasetStructureReference();

    /**
     * @return Associates the Data Provider that reports/publishes the data
     */
    ArtefactReference getProviderReference();

    /**
     * @return A specific time period in a known system of time periods
     * that identifies the start period of a report.
     */
    Instant getReportingBeginDate();

    /**
     * @return A specific time period in a known system of time periods
     * that identifies the end period of a report.
     */
    Instant getReportingEndDate();

    /**
     * @return Date from which the DatasetHeader is valid.
     */
    Instant getValidFrom();

    /**
     * @return Date from which DatasetHeader is superseded.
     */
    Instant getValidTo();

    /**
     * @return Specifies the year of publication of the data
     */
    int getPublicationYear();

    /**
     * @return Specifies the period of publication of the data
     */
    String getPublicationPeriod();

    /**
     * @return Specifies the year of publication of the data
     */
    String getReportingYearStartDate();
}
