package com.epam.jsdmx.infomodel.sdmx30;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;

public class TextFormatImpl implements TextFormat {

    private final Map<FacetType, Object> facetToFacetValueMap;
    private List<SentinelValue> sentinelValues;
    private FacetValueType valueType;

    public TextFormatImpl(Set<Facet> facets) {
        facetToFacetValueMap = new HashMap<>(CollectionUtils.size(facets));
        for (Facet f : SetUtils.emptyIfNull(facets)) {
            facetToFacetValueMap.put(f.getType(), f.getValue());

            if (f.getType() == FacetType.SENTINEL_VALUES) {
                sentinelValues = f.getSentinelValues();
            }

            final FacetValueType newValueType = f.getValueType();

            if (newValueType == null) {
                continue;
            }

            if (valueType == null) {
                valueType = newValueType;
            } else {
                if (valueType != newValueType) {
                    throw new IllegalStateException("Inconsistent text format, multiple value types present: " + valueType + " and " + newValueType);
                }
            }
        }
    }

    @Override
    public List<FacetType> getDefinedFacets() {
        return List.copyOf(facetToFacetValueMap.keySet());
    }

    @Override
    public boolean hasFacet(FacetType facetType) {
        return facetToFacetValueMap.containsKey(facetType);
    }

    @Override
    public Optional<FacetValueType> getValueType() {
        return Optional.ofNullable(valueType);
    }

    @Override
    public boolean hasValueType() {
        return valueType != null;
    }

    @Override
    public Optional<Instant> getStartTime() {
        return getStringValueOptional(FacetType.START_TIME)
            .map(Instant::parse);
    }

    @Override
    public boolean hasStartTime() {
        return facetToFacetValueMap.containsKey(FacetType.START_TIME);
    }

    @Override
    public Optional<Instant> getEndTime() {
        return getStringValueOptional(FacetType.END_TIME)
            .map(Instant::parse);
    }

    @Override
    public boolean hasEndTime() {
        return facetToFacetValueMap.containsKey(FacetType.END_TIME);
    }

    @Override
    public Optional<Boolean> getIsSequence() {
        return getStringValueOptional(FacetType.IS_SEQUENCE)
            .map(Boolean::parseBoolean);
    }

    @Override
    public boolean hasIsSequence() {
        return facetToFacetValueMap.containsKey(FacetType.IS_SEQUENCE);
    }

    @Override
    public Optional<Boolean> getIsMultiLingual() {
        return getStringValueOptional(FacetType.IS_MULTILINGUAL)
            .map(Boolean::parseBoolean);
    }

    @Override
    public boolean hasIsMultiLingual() {
        return facetToFacetValueMap.containsKey(FacetType.IS_MULTILINGUAL);
    }

    @Override
    public Optional<BigInteger> getMinLength() {
        return getStringValueOptional(FacetType.MIN_LENGTH)
            .map(BigInteger::new);
    }

    @Override
    public boolean hasMinLength() {
        return facetToFacetValueMap.containsKey(FacetType.MIN_LENGTH);
    }

    @Override
    public Optional<BigInteger> getMaxLength() {
        return getStringValueOptional(FacetType.MAX_LENGTH)
            .map(BigInteger::new);
    }

    @Override
    public boolean hasMaxLength() {
        return facetToFacetValueMap.containsKey(FacetType.MAX_LENGTH);
    }

    @Override
    public Optional<BigDecimal> getStartValue() {
        return getStringValueOptional(FacetType.START_VALUE)
            .map(BigDecimal::new);
    }

    @Override
    public boolean hasStartValue() {
        return facetToFacetValueMap.containsKey(FacetType.START_VALUE);
    }

    @Override
    public Optional<BigDecimal> getEndValue() {
        return getStringValueOptional(FacetType.END_VALUE)
            .map(BigDecimal::new);
    }

    @Override
    public boolean hasEndValue() {
        return facetToFacetValueMap.containsKey(FacetType.END_VALUE);
    }

    @Override
    public Optional<BigDecimal> getMaxValue() {
        return getStringValueOptional(FacetType.MAX_VALUE)
            .map(BigDecimal::new);
    }

    @Override
    public boolean hasMaxValue() {
        return facetToFacetValueMap.containsKey(FacetType.MAX_VALUE);
    }

    @Override
    public Optional<BigDecimal> getMinValue() {
        return getStringValueOptional(FacetType.MIN_VALUE)
            .map(BigDecimal::new);
    }

    @Override
    public boolean hasMinValue() {
        return facetToFacetValueMap.containsKey(FacetType.MIN_VALUE);
    }

    @Override
    public Optional<BigDecimal> getInterval() {
        return getStringValueOptional(FacetType.INTERVAL)
            .map(BigDecimal::new);
    }

    @Override
    public boolean hasInterval() {
        return facetToFacetValueMap.containsKey(FacetType.INTERVAL);
    }

    @Override
    public Optional<String> getTimeInterval() {
        return getStringValueOptional(FacetType.TIME_INTERVAL);
    }

    @Override
    public boolean hasTimeInterval() {
        return facetToFacetValueMap.containsKey(FacetType.TIME_INTERVAL);
    }

    @Override
    public Optional<BigInteger> getDecimals() {
        return getStringValueOptional(FacetType.DECIMALS)
            .map(BigInteger::new);
    }

    @Override
    public boolean hasDecimals() {
        return facetToFacetValueMap.containsKey(FacetType.DECIMALS);
    }

    @Override
    public Optional<String> getPattern() {
        return getStringValueOptional(FacetType.PATTERN);
    }

    @Override
    public boolean hasPattern() {
        return facetToFacetValueMap.containsKey(FacetType.PATTERN);
    }

    @Override
    public Optional<List<SentinelValue>> getSentinelValues() {
        return Optional.ofNullable(sentinelValues);
    }

    @Override
    public boolean hasSentinelValues() {
        return sentinelValues != null;
    }

    private Optional<String> getStringValueOptional(FacetType type) {
        return Optional.ofNullable(facetToFacetValueMap.get(type))
            .map(String.class::cast);
    }
}
