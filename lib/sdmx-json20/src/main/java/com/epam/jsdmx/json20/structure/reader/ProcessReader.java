package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getLocalizedField;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getStringJsonField;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Computation;
import com.epam.jsdmx.infomodel.sdmx30.ComputationImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.ProcessArtefact;
import com.epam.jsdmx.infomodel.sdmx30.ProcessArtefactImpl;
import com.epam.jsdmx.infomodel.sdmx30.ProcessImpl;
import com.epam.jsdmx.infomodel.sdmx30.ProcessStep;
import com.epam.jsdmx.infomodel.sdmx30.ProcessStepImpl;
import com.epam.jsdmx.infomodel.sdmx30.Transition;
import com.epam.jsdmx.infomodel.sdmx30.TransitionImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class ProcessReader extends MaintainableReader<ProcessImpl> {

    private final NameableReader nameableReader;
    private final AnnotableReader annotableReader;
    private final IdentifiableReader identifiableReader;

    public ProcessReader(VersionableReader versionableArtefact,
                         NameableReader nameableReader,
                         AnnotableReader annotableReader,
                         IdentifiableReader identifiableReader) {
        super(versionableArtefact);
        this.nameableReader = nameableReader;
        this.annotableReader = annotableReader;
        this.identifiableReader = identifiableReader;
    }

    @Override
    protected ProcessImpl createMaintainableArtefact() {
        return new ProcessImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, ProcessImpl process) throws IOException {
        String fieldName = parser.getCurrentName();
        if (StructureUtils.PROCESS_STEPS.equals(fieldName)) {
            List<ProcessStep> processSteps = ReaderUtils.getArray(parser, (this::getProcessStep));
            if (CollectionUtils.isNotEmpty(processSteps)) {
                process.setSteps(processSteps);
            }
        } else {
            throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "Process: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.PROCESSES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<ProcessImpl> artefacts) {
        artefact.getProcesses().addAll(artefacts);
    }

    private ProcessStep getProcessStep(JsonParser parser) {
        ProcessStepImpl processStep = new ProcessStepImpl();
        try {
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.COMPUTATION:
                        processStep.setComputation(getComputation(parser));
                        break;
                    case StructureUtils.TRANSITIONS:
                        setTransitions(parser, processStep);
                        break;
                    case StructureUtils.OUTPUTS:
                        List<ProcessArtefact> outputs = ReaderUtils.getArray(parser, (this::getInputOutput));
                        if (CollectionUtils.isNotEmpty(outputs)) {
                            processStep.setOutputs(outputs);
                        }
                        break;
                    case StructureUtils.INPUTS:
                        List<ProcessArtefact> inputs = ReaderUtils.getArray(parser, (this::getInputOutput));
                        if (CollectionUtils.isNotEmpty(inputs)) {
                            processStep.setInputs(inputs);
                        }
                        break;
                    case StructureUtils.PROCESS_STEPS:
                        List<ProcessStep> processSteps = ReaderUtils.getArray(parser, (this::getProcessStep));
                        if (CollectionUtils.isNotEmpty(processSteps)) {
                            processStep.setChildren(processSteps);
                        }
                        break;
                    default:
                        nameableReader.read(processStep, parser);
                        break;
                }
            }
            return processStep;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private void setTransitions(JsonParser parser, ProcessStepImpl processStep) throws IOException {
        List<Transition> transitions = ReaderUtils.getArray(parser, (this::getTransitions));
        if (CollectionUtils.isNotEmpty(transitions)) {
            processStep.setTransitions(transitions);
        }
    }

    private Transition getTransitions(JsonParser parser) {
        TransitionImpl transition = new TransitionImpl();
        try {
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.LOCAL_ID:
                        String localID = getStringJsonField(parser);
                        if (localID != null) {
                            transition.setLocalId(localID);
                        }
                        break;
                    case StructureUtils.CONDITION:
                        parser.nextToken();
                        parser.skipChildren();
                        break;
                    case StructureUtils.CONDITIONS:
                        Map<String, String> conditions = getLocalizedField(parser);
                        if (conditions != null) {
                            transition.setCondition(new InternationalString(conditions));
                        }
                        break;
                    case StructureUtils.TARGET_STEP:
                        String targetStep = getStringJsonField(parser);
                        if (targetStep != null) {
                            transition.setTargetProcessStep(targetStep);
                        }
                        break;
                    default:
                        identifiableReader.read(transition, parser);
                        break;
                }
            }
            return transition;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ProcessArtefact getInputOutput(JsonParser parser) {
        ProcessArtefactImpl processArtefact = new ProcessArtefactImpl();
        try {
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.LOCAL_ID:
                        String localID = getStringJsonField(parser);
                        if (localID != null) {
                            processArtefact.setLocalId(localID);
                        }
                        break;
                    case StructureUtils.OBJECT_REFERENCE:
                        String objectRef = getStringJsonField(parser);
                        if (objectRef != null) {
                            processArtefact.setArtefact(new MaintainableArtefactReference(objectRef));
                        }
                        break;
                    default:
                        annotableReader.read(processArtefact, parser);
                        break;
                }
            }
            return processArtefact;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Computation getComputation(JsonParser parser) throws IOException {
        ComputationImpl computation = new ComputationImpl();
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.LOCAL_ID:
                    setLocalId(parser, computation);
                    break;
                case StructureUtils.SOFTWARE_LANGUAGE:
                    setSoftwareLanguage(parser, computation);
                    break;
                case StructureUtils.SOFTWARE_PACKAGE:
                    setSoftwarePackage(parser, computation);
                    break;
                case StructureUtils.SOFTWARE_VERSION:
                    setSoftwareVersion(parser, computation);
                    break;
                case StructureUtils.DESCRIPTION:
                    parser.nextToken();
                    parser.skipChildren();
                    break;
                case StructureUtils.DESCRIPTIONS:
                    setDescription(parser, computation);
                    break;
                default:
                    annotableReader.read(computation, parser);
                    break;
            }
        }
        return computation;
    }

    private void setSoftwareLanguage(JsonParser parser, ComputationImpl computation) throws IOException {
        String softwareLanguage = getStringJsonField(parser);
        if (softwareLanguage != null) {
            computation.setSoftwareLanguage(softwareLanguage);
        }
    }

    private void setSoftwarePackage(JsonParser parser, ComputationImpl computation) throws IOException {
        String softwarePackage = getStringJsonField(parser);
        if (softwarePackage != null) {
            computation.setSoftwarePackage(softwarePackage);
        }
    }

    private void setSoftwareVersion(JsonParser parser, ComputationImpl computation) throws IOException {
        String softwareVersion = getStringJsonField(parser);
        if (softwareVersion != null) {
            computation.setSoftwareVersion(softwareVersion);
        }
    }

    private void setDescription(JsonParser parser, ComputationImpl computation) throws IOException {
        Map<String, String> localizedDescriptions = getLocalizedField(parser);
        if (localizedDescriptions != null) {
            computation.setDescription(new InternationalString(localizedDescriptions));
        }
    }

    private void setLocalId(JsonParser parser, ComputationImpl computation) throws IOException {
        String localID = getStringJsonField(parser);
        if (localID != null) {
            computation.setLocalId(localID);
        }
    }
}
