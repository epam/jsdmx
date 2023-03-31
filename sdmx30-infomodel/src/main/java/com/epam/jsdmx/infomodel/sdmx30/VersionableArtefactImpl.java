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
    private Instant validFrom;
    private Instant validTo;

    protected VersionableArtefactImpl(VersionableArtefact artefact) {
        super(Objects.requireNonNull(artefact));
        this.version = artefact.getVersion();
        this.validFrom = artefact.getValidFrom();
        this.validTo = artefact.getValidTo();
    }
}
