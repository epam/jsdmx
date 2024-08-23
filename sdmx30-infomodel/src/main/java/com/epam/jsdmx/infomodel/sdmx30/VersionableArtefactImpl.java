package com.epam.jsdmx.infomodel.sdmx30;

import java.time.Instant;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public abstract class VersionableArtefactImpl
    extends NameableArtefactImpl
    implements VersionableArtefact {

    @EqualsAndHashCode.Include
    private Version version;
    private String validFrom;
    private String validTo;

    protected VersionableArtefactImpl(VersionableArtefact artefact) {
        super(Objects.requireNonNull(artefact));
        this.version = artefact.getVersion();
        this.validFrom = artefact.getValidFromString();
        this.validTo = artefact.getValidToString();
    }

    @Override
    public String getValidFromString() {
        return validFrom;
    }

    @Override
    public String getValidToString() {
        return validTo;
    }

    @Override
    public Instant getValidFrom() {
        return validFrom == null ? null : Instant.parse(validFrom);
    }

    @Override
    public Instant getValidTo() {
        return validTo == null ? null : Instant.parse(validTo);
    }
}
