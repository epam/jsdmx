package com.epam.jsdmx.infomodel.sdmx30;

/**
 * The NamableArtefact is an abstract class that inherits from IdentifiableArtefact and
 * in addition the +description and +name roles support multilingual descriptions and names
 * for all objects based on NameableArtefact. The InternationalString supports the
 * representation of a description in multiple locales (locale is similar to language but includes
 * geographic variations such as Canadian French, US English etc.). The LocalisedString
 * supports the representation of a description in one locale.
 */
public interface NameableArtefact extends IdentifiableArtefact {

    /**
     * A multi-lingual name of the artefact.
     */
    InternationalString getName();

    /**
     * A multi-lingual description of the artefact.
     */
    InternationalString getDescription();

    /**
     * Localised name value which corresponds to user's default locale specified in the configuration.
     * See {@link InternationalObject} for more details.
     */
    String getNameInDefaultLocale();

    /**
     * Localised description value which corresponds to user's default locale specified in the configuration.
     * See {@link InternationalObject} for more details.
     */
    String getDescriptionInDefaultLocale();

}
