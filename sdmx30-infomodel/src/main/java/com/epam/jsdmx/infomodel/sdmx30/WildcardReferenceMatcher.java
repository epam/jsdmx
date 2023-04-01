package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Comparator;

/**
 * Allows to compare wildcarded {@link VersionReference} (i.e. {@link VersionReference#isWildcarded()} == true)
 * against specific {@link VersionReference} (i.e. {@link VersionReference#isSpecific()} == true) to tell if wildcard
 * matches the version or not.
 * <p>
 * For example, if we have a wildcarded reference to version like 1.2+.0, then it will match versions like 1.2.0, 1.2.1, 1.2.42 etc.
 * but not 1.1.0, 1.1.1, 1.1.69, etc.
 * </p>
 */
public class WildcardReferenceMatcher {

    private final short majorFrom;
    private final short minorFrom;
    private final short patchFrom;

    private final short majorTo;
    private final short minorTo;
    private final short patchTo;

    private final boolean isStable;

    public WildcardReferenceMatcher(VersionReference versionRef) {
        this(versionRef, false);
    }

    public WildcardReferenceMatcher(VersionReference versionRef, boolean isStableMatch) {
        validateVersionReferenceSpecific(versionRef);

        this.isStable = isStableMatch;

        final Version base = versionRef.getBase();
        this.majorFrom = base.getMajor();
        this.minorFrom = base.getMinor();
        this.patchFrom = base.getPatch()
            .orElseThrow(IllegalStateException::new);

        final WildcardScope scope = versionRef.getScope();
        switch (scope) {
            case MAJOR:
                this.majorTo = Short.MAX_VALUE;
                this.minorTo = Short.MAX_VALUE;
                this.patchTo = Short.MAX_VALUE;
                break;
            case MINOR:
                this.majorTo = majorFrom;
                this.minorTo = Short.MAX_VALUE;
                this.patchTo = Short.MAX_VALUE;
                break;
            case PATCH:
                this.majorTo = majorFrom;
                this.minorTo = minorFrom;
                this.patchTo = Short.MAX_VALUE;
                break;
            default:
                throw new IllegalArgumentException("Missing support for wildcard scope: " + scope);
        }
    }

    private void validateVersionReferenceSpecific(VersionReference versionRef) {
        if (versionRef.isSpecific()) {
            throw new IllegalArgumentException("Reference must be wildcarded");
        }
    }

    /**
     * Checks if the given version matches which this instance is initialized with.
     *
     * @param candidateVersion version to check
     * @return true if the version matches the wildcard, false otherwise
     */
    public boolean matches(VersionReference candidateVersion) {
        if (candidateVersion.isWildcarded()) {
            return false;
        }

        if (this.isStable && !candidateVersion.getBase().isStable()) {
            return false;
        }

        Comparator<VersionReference> versionComparator = VersionReference.getComparator();
        int comparedWithFrom = versionComparator.compare(candidateVersion, from());
        int comparedWithTo = versionComparator.compare(candidateVersion, to());

        return comparedWithFrom >= 0 && comparedWithTo <= 0;
    }

    private VersionReference from() {
        return VersionReference.createFromString(majorFrom + "." + minorFrom + "." + patchFrom + (isStable ? "" : "-draft"));
    }

    private VersionReference to() {
        return VersionReference.createFromString(majorTo + "." + minorTo + "." + patchTo);
    }
}