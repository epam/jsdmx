package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * A component that gives structure to the report and links to data and metadata.
 */
public interface ReportingCategory extends Item {
    /**
     * Association to the data and metadata flows that link to metadata about the provisioning and related data and metadata sets,
     * and the structures that define them.
     */
    List<ArtefactReference> getFlows();

    /**
     * Association to the Data Structure Definition and Metadata Structure Definitions
     * which define the structural metadata describing the data and metadata
     * that are contained at this part of the report.
     */
    List<ArtefactReference> getStructures();
}
