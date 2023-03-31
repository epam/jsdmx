package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Base class to support artefacts referencing specific versions of other artefacts.
 */
abstract class AbstractVersionReference {

    protected final Version base;
    protected final WildcardScope scope;

    protected AbstractVersionReference(Version version, WildcardScope scope) {
        this.base = version;
        this.scope = scope;
    }

    /**
     * non-wildcarded version which is the base for this version reference, i.e. given the reference
     * 1.2+.0, the base version is 1.2.0
     */
    public Version getBase() {
        return base;
    }

    /**
     * type (or in other words, placement with regards to the version components) of the wildcard, see {@link WildcardScope}.
     */
    public WildcardScope getScope() {
        return scope;
    }

    /**
     * Whether this version reference is wildcarded.
     *
     * @return <code>true</code> if version ref contains wildcard (e.g. 1.1+.0) or <code>false</code> otherwise
     */
    public boolean isWildcarded() {
        return !isSpecific();
    }

    /**
     * symmetrical method to {@link AbstractVersionReference#isWildcarded()}
     *
     * @return <code>true</code> if the version ref is specific, i.e. 4.20.0
     */
    public boolean isSpecific() {
        return scope == WildcardScope.NONE;
    }

    protected static <T extends AbstractVersionReference> T createFromString(List<Pair<Pattern, Function<String, T>>> creators, String version) {
        return creators.stream()
            .filter(p -> p.getLeft().matcher(version).matches())
            .findFirst()
            .map(p -> p.getRight().apply(version))
            .orElseThrow(() -> new IllegalArgumentException("Invalid version: " + version));
    }
}
