package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Computation;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.Process;
import com.epam.jsdmx.infomodel.sdmx30.ProcessArtefact;
import com.epam.jsdmx.infomodel.sdmx30.ProcessStep;
import com.epam.jsdmx.infomodel.sdmx30.Transition;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections.CollectionUtils;

public class ProcessWriter extends MaintainableWriter<Process> {

    private final NameableWriter nameableWriter;
    private final AnnotableWriter annotableWriter;
    private final IdentifiableWriter identifiableWriter;


    public ProcessWriter(VersionableWriter versionableWriter,
                         LinksWriter linksWriter,
                         NameableWriter nameableWriter,
                         AnnotableWriter annotableWriter,
                         IdentifiableWriter identifiableWriter) {
        super(versionableWriter, linksWriter);
        this.nameableWriter = nameableWriter;
        this.annotableWriter = annotableWriter;
        this.identifiableWriter = identifiableWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, Process process) throws IOException {
        super.writeFields(jsonGenerator, process);
        List<ProcessStep> steps = process.getSteps();
        writeProcessSteps(jsonGenerator, steps);
    }

    @Override
    protected Set<Process> extractArtefacts(Artefacts artefacts) {
        return artefacts.getProcesses();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.PROCESSES;
    }

    private void writeProcessSteps(JsonGenerator jsonGenerator, List<ProcessStep> steps) throws IOException {
        if (CollectionUtils.isNotEmpty(steps)) {
            jsonGenerator.writeFieldName(StructureUtils.PROCESS_STEPS);
            jsonGenerator.writeStartArray();
            for (ProcessStep processStep : steps) {
                jsonGenerator.writeStartObject();

                nameableWriter.write(jsonGenerator, processStep);

                Computation computation = processStep.getComputation();
                writeComputation(jsonGenerator, computation);

                List<Transition> transitions = processStep.getTransitions();
                writeTransitions(jsonGenerator, transitions);

                List<ProcessArtefact> outputs = processStep.getOutputs();
                writeInputOutput(jsonGenerator, outputs, StructureUtils.OUTPUTS);

                List<ProcessArtefact> inputs = processStep.getInputs();
                writeInputOutput(jsonGenerator, inputs, StructureUtils.INPUTS);

                List<ProcessStep> children = processStep.getChildren();
                writeProcessSteps(jsonGenerator, children);
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }
    }

    private void writeInputOutput(JsonGenerator jsonGenerator, List<ProcessArtefact> processArtefacts, String inputOutputName) throws IOException {
        if (CollectionUtils.isNotEmpty(processArtefacts)) {
            jsonGenerator.writeFieldName(inputOutputName);
            jsonGenerator.writeStartArray();
            for (ProcessArtefact processArtefact : processArtefacts) {
                jsonGenerator.writeStartObject();

                String localId = processArtefact.getLocalId();
                if (localId != null) {
                    jsonGenerator.writeStringField(StructureUtils.LOCAL_ID, localId);
                }

                annotableWriter.write(jsonGenerator, processArtefact);

                ArtefactReference artefact = processArtefact.getArtefact();
                if (artefact != null) {
                    jsonGenerator.writeStringField(StructureUtils.OBJECT_REFERENCE, artefact.getUrn());
                }

                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }
    }

    private void writeTransitions(JsonGenerator jsonGenerator, List<Transition> transitions) throws IOException {
        if (CollectionUtils.isNotEmpty(transitions)) {
            jsonGenerator.writeFieldName(StructureUtils.TRANSITIONS);
            jsonGenerator.writeStartArray();
            for (Transition transition : transitions) {
                jsonGenerator.writeStartObject();

                identifiableWriter.write(jsonGenerator, transition);

                String localId = transition.getLocalId();
                if (localId != null) {
                    jsonGenerator.writeStringField(StructureUtils.LOCAL_ID, localId);
                }

                InternationalString condition = transition.getCondition();
                if (condition != null) {
                    jsonGenerator.writeStringField(
                        StructureUtils.CONDITION,
                        condition.getForDefaultLocale()
                    );
                    StructureUtils.writeInternationalString(jsonGenerator, condition, StructureUtils.CONDITIONS);
                }

                String targetProcessStep = transition.getTargetProcessStep();
                if (targetProcessStep != null) {
                    jsonGenerator.writeStringField(StructureUtils.TARGET_STEP, targetProcessStep);
                }

                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }
    }

    private void writeComputation(JsonGenerator jsonGenerator, Computation computation) throws IOException {
        if (computation != null) {
            jsonGenerator.writeFieldName(StructureUtils.COMPUTATION);
            jsonGenerator.writeStartObject();

            if (computation.getLocalId() != null) {
                jsonGenerator.writeStringField(StructureUtils.LOCAL_ID, computation.getLocalId());
            }
            if (computation.getSoftwareLanguage() != null) {
                jsonGenerator.writeStringField(StructureUtils.SOFTWARE_LANGUAGE, computation.getSoftwareLanguage());
            }
            if (computation.getSoftwarePackage() != null) {
                jsonGenerator.writeStringField(StructureUtils.SOFTWARE_PACKAGE, computation.getSoftwarePackage());
            }
            if (computation.getSoftwareVersion() != null) {
                jsonGenerator.writeStringField(StructureUtils.SOFTWARE_VERSION, computation.getSoftwareVersion());
            }

            annotableWriter.write(jsonGenerator, computation);

            if (computation.getDescription() != null) {
                jsonGenerator.writeStringField(
                    StructureUtils.DESCRIPTION,
                    computation.getDescription().getForDefaultLocale()
                );
                StructureUtils.writeInternationalString(jsonGenerator, computation.getDescription(), StructureUtils.DESCRIPTIONS);
            }
            jsonGenerator.writeEndObject();
        }
    }
}
