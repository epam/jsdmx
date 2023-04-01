package com.epam.jsdmx.infomodel.sdmx30;

import java.util.HashMap;
import java.util.Map;

/**
 * The FacetValueType enumeration is used to specify the valid format of
 * the content of a non-enumerated Concept or the usage of a Concept when specified for use
 * on a Component on a Structure (such as a Dimension in a
 * DataStructureDefinition). The description of the various types can be found in the
 * chapter on ConceptScheme.
 */
public enum FacetValueType {
    STRING("String"),
    ALPHA("Alpha"),
    ALPHA_NUMERIC("AlphaNumeric"),
    NUMERIC("Numeric"),
    BIG_INTEGER("BigInteger"),
    INTEGER("Integer"),
    LONG("Long"),
    SHORT("Short"),
    DECIMAL("Decimal"),
    FLOAT("Float"),
    DOUBLE("Double"),
    BOOLEAN("Boolean"),
    URI("URI"),
    COUNT("Count"),
    INCLUSIVE_VALUE_RANGE("InclusiveValueRange"),
    EXCLUSIVE_VALUE_RANGE("ExclusiveValueRange"),
    INCREMENTAL("Incremental"),
    OBSERVATIONAL_TIME_PERIOD("ObservationalTimePeriod"),
    STANDARD_TIME_PERIOD("StandardTimePeriod"),
    BASIC_TIME_PERIOD("BasicTimePeriod"),
    GREGORIAN_TIME_PERIOD("GregorianTimePeriod"),
    GREGORIAN_YEAR("GregorianYear"),
    GREGORIAN_YEAR_MONTH("GregorianYearMonth"),
    GREGORIAN_DAY("GregorianDay"),
    REPORTING_TIME_PERIOD("ReportingTimePeriod"),
    REPORTING_YEAR("ReportingYear"),
    REPORTING_SEMESTER("ReportingSemester"),
    REPORTING_TRIMESTER("ReportingTrimester"),
    REPORTING_QUARTER("ReportingQuarter"),
    REPORTING_MONTH("ReportingMonth"),
    REPORTING_WEEK("ReportingWeek"),
    REPORTING_DAY("ReportingDay"),
    DATE_TIME("DateTime"),
    TIME_RANGE("TimeRange"),
    MONTH("Month"),
    MONTH_DAY("MonthDay"),
    DAY("Day"),
    TIME("Time"),
    DURATION("Duration"),
    GEOSPATIAL_INFORMATION("GeospatialInformation"),
    XHTML("XHTML");

    private final static Map<String, FacetValueType> CONSTANTS = new HashMap<>();

    static {
        for (FacetValueType c : values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private final String value;

    FacetValueType(String value) {
        this.value = value;
    }

    public static FacetValueType fromValue(String value) {
        FacetValueType constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

    public String value() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
