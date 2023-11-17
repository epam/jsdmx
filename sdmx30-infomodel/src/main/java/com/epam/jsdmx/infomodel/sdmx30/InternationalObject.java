package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Base class for objects objects which need to be internationalised.
 * Provides a base for creation and retrieval of values for different locales (see {@link Locale}),
 * including one specified as the default - see {@link DefaultLocaleHolder}.
 *
 * @param <T>
 */
public abstract class InternationalObject<T> {

    private Localisation _default;
    private List<Localisation> others;

    public InternationalObject() {
    }

    /**
     * Copy constructor.
     */
    public InternationalObject(InternationalObject<T> from) {
        this._default = from._default;
        this.others = from.others == null ? null : new ArrayList<>(from.others);
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
        _default = new Localisation(DefaultLocaleHolder.INSTANCE.getLanguageTag(), value);
    }

    /**
     * Adds or updates a value for the given locale.
     *
     * @param language language tag to parse locale from
     * @param value    localised value for given locale
     */
    public void add(String language, T value) {
        if (Objects.equals(language, DefaultLocaleHolder.INSTANCE.getLanguageTag())) {
            addForDefaultLocale(value);
        } else {
            if (others == null) {
                others = new ArrayList<>();
            }
            others.add(new Localisation(language, value));
        }
    }

    /**
     * Adds or updates values for a set of locales represented by their language tags.
     *
     * @param values language tag to localised value mapping
     */
    public void addAll(Map<String, T> values) {
        if (values == null) {
            return;
        }

        values.forEach((language, value) -> {
            if (Objects.equals(language, DefaultLocaleHolder.INSTANCE.getLanguageTag())) {
                addForDefaultLocale(value);
            } else {
                add(language, value);
            }
        });
    }

    /**
     * @return value for the default locale which is hold by {@link DefaultLocaleHolder}.
     */
    public T getForDefaultLocale() {
        return _default != null ? _default.getValue() : null;
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
        if (Objects.equals(language, DefaultLocaleHolder.INSTANCE.getLanguageTag())) {
            return Optional.ofNullable(getForDefaultLocale());
        } else {
            return StreamUtils.streamOfNullable(others)
                .filter(localisation -> Objects.equals(localisation.getLanguage(), language))
                .findFirst()
                .map(Localisation::getValue);
        }
    }

    /**
     * @return value for the given locale.
     */
    public Optional<T> get(Locale locale) {
        return get(locale.toLanguageTag());
    }

    /**
     * @return mapping of language tags to localised values. See {@link Locale#toLanguageTag()}.
     */
    public Map<String, T> getAll() {
        final Map<String, T> result = new HashMap<>();
        if (_default != null) {
            result.put(_default.getLanguage(), _default.getValue());
        }
        if (others != null) {
            others.forEach(localisation -> result.put(localisation.getLanguage(), localisation.getValue()));
        }
        return result;
    }


    /**
     * @return mapping of language tags to localised values represented as stream of entries. See {@link Locale#toLanguageTag()}.
     */
    public Stream<Map.Entry<String, T>> getAllAsStream() {
        return Stream.concat(Stream.ofNullable(_default), StreamUtils.streamOfNullable(others))
            .map(localisation -> Pair.of(localisation.getLanguage(), localisation.getValue()));
    }

    /**
     * @return true if there are no localised values and false otherwise.
     */
    public boolean isEmpty() {
        return (CollectionUtils.isEmpty(others) && _default == null)
            || areAllElementsEmpty();
    }

    protected boolean isEmpty(T value) {
        return value == null;
    }

    private boolean areAllElementsEmpty() {
        return Stream.concat(
                Stream.ofNullable(_default),
                StreamUtils.streamOfNullable(others)
            )
            .allMatch(localisation -> isEmpty(localisation.getValue()));
    }

    @RequiredArgsConstructor
    @Getter
    private class Localisation {
        private final String language;
        private final T value;
    }

}
