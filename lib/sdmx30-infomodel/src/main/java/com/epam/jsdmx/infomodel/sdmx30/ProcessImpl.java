package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProcessImpl extends MaintainableArtefactImpl implements Process {
    private List<ProcessStep> steps = new ArrayList<>();

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.PROCESS;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return Set.of();
    }

    @Override
    protected ProcessImpl createInstance() {
        return new ProcessImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Process)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Process)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        ProcessImpl process = (ProcessImpl) o;
        return Objects.equals(getSteps(), process.getSteps());
    }

    @Override
    public Process toStub() {
        return toStub(createInstance());
    }

    @Override
    public Process toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
