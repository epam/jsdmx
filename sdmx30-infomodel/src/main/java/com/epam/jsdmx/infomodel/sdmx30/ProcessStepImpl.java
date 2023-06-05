package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcessStepImpl extends NameableArtefactImpl implements ProcessStep {

    private List<ProcessArtefact> inputs = new ArrayList<>();
    private List<ProcessArtefact> outputs = new ArrayList<>();
    private List<Transition> transitions = new ArrayList<>();
    private List<ProcessStep> children = new ArrayList<>();
    private Computation computation;

    public ProcessStepImpl(ProcessStep from) {
        super(from);
        this.inputs.addAll(from.getInputs());
        this.outputs.addAll(from.getOutputs());
        this.transitions.addAll(from.getTransitions());
        this.children.addAll(from.getChildren());
        this.computation = from.getComputation();
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.PROCESS_STEP;
    }

    @Override
    public ProcessStep clone() {
        return new ProcessStepImpl(this);
    }
}
