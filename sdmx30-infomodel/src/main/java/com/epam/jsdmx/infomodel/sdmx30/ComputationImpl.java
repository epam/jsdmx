package com.epam.jsdmx.infomodel.sdmx30;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ComputationImpl extends AnnotableArtefactImpl implements Computation {

    private String localId;
    private String softwareLanguage;
    private String softwarePackage;
    private String softwareVersion;
    private InternationalString description;

    public ComputationImpl(Computation other) {
        super(other);
        this.localId = other.getLocalId();
        this.softwareLanguage = other.getSoftwareLanguage();
        this.softwarePackage = other.getSoftwarePackage();
        this.softwareVersion = other.getSoftwareVersion();
        this.description = other.getDescription();
    }

    @Override
    public Computation clone() {
        return new ComputationImpl(this);
    }
}

