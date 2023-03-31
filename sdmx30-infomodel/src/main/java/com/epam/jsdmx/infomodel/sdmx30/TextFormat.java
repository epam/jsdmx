package com.epam.jsdmx.infomodel.sdmx30;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Convenience abstraction used to hold information carried by a set of {@link Facet}s that describe concept representation.
 */
public interface TextFormat {

    /**
     * Returns the list of facet types defined for this text format.
     */
    List<FacetType> getDefinedFacets();

    /**
     * Returns boolean to indicate whether the given facet type is defined for this text format.
     */
    boolean hasFacet(FacetType facetType);

    /**
     * Returns the optional {@link FacetValueType} of the given format, if defined.
     */
    Optional<FacetValueType> getValueType();

    /**
     * Returns boolean to indicate whether the value type is defined for this text format.
     */
    boolean hasValueType();

    /**
     * Returns optional boolean to indicate whether value represented by this format is a sequence, if {@link FacetType#IS_SEQUENCE} facet is defined.
     */
    Optional<Boolean> getIsSequence();

    /**
     * Returns boolean to indicate whether the {@link FacetType#IS_SEQUENCE} facet is defined for this text format.
     */
    boolean hasIsSequence();

    /**
     * Returns optional boolean to indicate whether value represented by this format is multilingual, if {@link FacetType#IS_MULTILINGUAL} facet is defined.
     */
    Optional<Boolean> getIsMultiLingual();

    /**
     * Returns boolean to indicate whether the {@link FacetType#IS_MULTILINGUAL} facet is defined for this text format.
     */
    boolean hasIsMultiLingual();

    /**
     * Returns optional {@link Instant} to indicate the start time of the time interval, if {@link FacetType#START_TIME} facet is defined.
     */
    Optional<Instant> getStartTime();

    /**
     * Returns boolean to indicate whether the {@link FacetType#START_TIME} facet is defined for this text format.
     */
    boolean hasStartTime();

    /**
     * Returns optional {@link Instant} to indicate the end time of the time interval, if {@link FacetType#END_TIME} facet is defined.
     */
    Optional<Instant> getEndTime();

    /**
     * Returns boolean to indicate whether the {@link FacetType#END_TIME} facet is defined for this text format.
     */
    boolean hasEndTime();

    /**
     * Returns optional {@link BigInteger} to indicate the minimum length of the text, if {@link FacetType#MIN_LENGTH} facet is defined.
     */
    Optional<BigInteger> getMinLength();

    /**
     * Returns boolean to indicate whether the {@link FacetType#MIN_LENGTH} facet is defined for this text format.
     */
    boolean hasMinLength();

    /**
     * Returns optional {@link BigInteger} to indicate the maximum length of the text, if {@link FacetType#MAX_LENGTH} facet is defined.
     */
    Optional<BigInteger> getMaxLength();

    /**
     * Returns boolean to indicate whether the {@link FacetType#MAX_LENGTH} facet is defined for this text format.
     */
    boolean hasMaxLength();

    /**
     * Returns optional {@link BigDecimal} to indicate the minimum value of the numeric range, if {@link FacetType#START_VALUE} facet is defined.
     */
    Optional<BigDecimal> getStartValue();

    /**
     * Returns boolean to indicate whether the {@link FacetType#START_VALUE} facet is defined for this text format.
     */
    boolean hasStartValue();

    /**
     * Returns optional {@link BigDecimal} to indicate the maximum value of the numeric range, if {@link FacetType#END_VALUE} facet is defined.
     */
    Optional<BigDecimal> getEndValue();

    /**
     * Returns boolean to indicate whether the {@link FacetType#END_VALUE} facet is defined for this text format.
     */
    boolean hasEndValue();

    /**
     * Returns optional {@link BigDecimal} to indicate the maximum value of the numeric range, if {@link FacetType#MAX_VALUE} facet is defined.
     */
    Optional<BigDecimal> getMaxValue();

    /**
     * Returns boolean to indicate whether the {@link FacetType#MAX_VALUE} facet is defined for this text format.
     */
    boolean hasMaxValue();

    /**
     * Returns optional {@link BigDecimal} to indicate the minimum value of the numeric range, if {@link FacetType#MIN_VALUE} facet is defined.
     */
    Optional<BigDecimal> getMinValue();

    /**
     * Returns boolean to indicate whether the {@link FacetType#MIN_VALUE} facet is defined for this text format.
     */
    boolean hasMinValue();

    /**
     * Returns optional {@link BigDecimal} to indicate the interval between two consecutive values, if {@link FacetType#INTERVAL} facet is defined.
     */
    Optional<BigDecimal> getInterval();

    /**
     * Returns boolean to indicate whether the {@link FacetType#INTERVAL} facet is defined for this text format.
     */
    boolean hasInterval();

    /**
     * Returns optional {@link BigInteger} to indicate the number of decimal places, if {@link FacetType#DECIMALS} facet is defined.
     */
    Optional<BigInteger> getDecimals();

    /**
     * Returns boolean to indicate whether the {@link FacetType#DECIMALS} facet is defined for this text format.
     */
    boolean hasDecimals();

    /**
     * Returns optional {@link String} to indicate the duration of a time interval, if {@link FacetType#TIME_INTERVAL} facet is defined.
     */
    Optional<String> getTimeInterval();

    /**
     * Returns boolean to indicate whether the {@link FacetType#TIME_INTERVAL} facet is defined for this text format.
     */
    boolean hasTimeInterval();

    /**
     * Returns optional {@link String} to indicate the regular expression pattern, if {@link FacetType#PATTERN} facet is defined.
     */
    Optional<String> getPattern();

    /**
     * Returns boolean to indicate whether the {@link FacetType#PATTERN} facet is defined for this text format.
     */
    boolean hasPattern();

    /**
     * Returns optional {@link List} of {@link SentinelValue}s to indicate the special values for the concept,
     * if {@link FacetType#SENTINEL_VALUES} facet is defined.
     */
    Optional<List<SentinelValue>> getSentinelValues();

    /**
     * Returns boolean to indicate whether the {@link FacetType#SENTINEL_VALUES} facet is defined for this text format.
     */
    boolean hasSentinelValues();
}
