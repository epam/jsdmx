package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Base class for objects objects which need to be internationalised.
 * Provides a base for creation and retrieval of values for different locales (see {@link Locale}),
 * including one specified as the default - see {@link DefaultLocaleHolder}.
 *
 * @param <T>
 */
public abstract class InternationalObject<T> {

    private final LinkedHashMap<Locale, T> localisations = new LinkedHashMap<>();

    public InternationalObject() {
    }

    /**
     * Copy constructor.
     */
    public InternationalObject(InternationalObject<T> from) {
        localisations.putAll(from.localisations);
    }

    /**
     * Creates an instance with a default value for the default locale which is hold by {@link DefaultLocaleHolder}.
     */
    public InternationalObject(T defaultLanguageValue) {
        addForDefaultLocale(defaultLanguageValue);
    }

    /**
     * Creates an instance using a mapping of language tags to localised values. See {@link Locale#forLanguageTag}.
     */
    public InternationalObject(Map<String, T> values) {
        addAll(values);
    }

    /**
     * Adds or updates a value for the default locale which is hold by {@link DefaultLocaleHolder}.
     *
     * @param value localised value for default locale
     */
    public void addForDefaultLocale(T value) {
        localisations.put(DefaultLocaleHolder.INSTANCE.get(), value);
    }

    /**
     * Adds or updates a value for the given locale.
     *
     * @param language language tag to parse locale from
     * @param value    localised value for given locale
     */
    public void add(String language, T value) {
        localisations.put(Locale.forLanguageTag(language), value);
    }

    /**
     * Adds or updates values for a set of locales represented by their language tags.
     *
     * @param values language tag to localised value mapping
     */
    public void addAll(Map<String, T> values) {
        values.forEach((language, value) -> localisations.put(Locale.forLanguageTag(language), value));
    }

    /**
     * @return value for the default locale which is hold by {@link DefaultLocaleHolder}.
     */
    public T getForDefaultLocale() {
        return localisations.get(DefaultLocaleHolder.INSTANCE.get());
    }

    /**
     * @return value which best matches for a provided {@link Locale.LanguageRange} list
     */
    public T getForRanges(List<LanguageRange> ranges) {
        if (CollectionUtils.isNotEmpty(ranges)) {
            for (LanguageRange range : ranges) {
                Optional<T> object = get(range.getRange());
                if (object.isPresent()) {
                    return object.get();
                }
            }
        }
        return getForDefaultLocale();
    }

    /**
     * @return value for the given locale's language tag.
     */
    public Optional<T> get(String language) {
        return Optional.ofNullable(localisations.get(Locale.forLanguageTag(language)));
    }

    /**
     * @return value for the given locale.
     */
    public Optional<T> get(Locale locale) {
        return Optional.ofNullable(localisations.get(locale));
    }

    /**
     * @return mapping of language tags to localised values. See {@link Locale#toLanguageTag()}.
     */
    public Map<String, T> getAll() {
        return localisations.entrySet()
            .stream()
            .reduce(new HashMap<>(), (Map<String, T> map, Map.Entry<Locale, T> entry) -> {
                map.put(entry.getKey().toLanguageTag(), entry.getValue());
                return map;
            }, (lMap, rMap) -> {
                lMap.putAll(rMap);
                return lMap;
            });
    }


    /**
     * @return mapping of language tags to localised values represented as stream of entries. See {@link Locale#toLanguageTag()}.
     */
    public Stream<Map.Entry<String, T>> getAllAsStream() {
        return localisations.entrySet()
            .stream()
            .map(entry -> Pair.of(entry.getKey().toLanguageTag(), entry.getValue()));
    }

    /**
     * @return true if there are no localised values and false otherwise.
     */
    public boolean isEmpty() {
        return MapUtils.isEmpty(localisations)
            || areAllElementsEmpty(localisations.values());
    }

    protected boolean isEmpty(T value) {
        return value == null;
    }

    private boolean areAllElementsEmpty(Collection<T> values) {
        return values.stream().allMatch(this::isEmpty);
    }
}
