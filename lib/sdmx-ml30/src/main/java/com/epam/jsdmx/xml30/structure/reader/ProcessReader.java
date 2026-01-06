package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEmptyOrNullElementText;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setCommonAttributes;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
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
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class ProcessReader extends XmlReader<ProcessImpl> {

    private final List<ProcessStep> processStepList = new ArrayList<>();

    public ProcessReader(AnnotableReader annotableReader,
                         NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected ProcessImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        ProcessImpl process = super.read(reader);
        process.setSteps(new ArrayList<>(processStepList));
        return process;
    }

    @Override
    protected void read(XMLStreamReader reader, ProcessImpl maintainableArtefact) throws URISyntaxException, XMLStreamException {
        String name = reader.getLocalName();
        if (!XmlConstants.PROCESS_STEP.equals(name)) {
            throw new IllegalArgumentException("Process " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
        }
        ProcessStepImpl processStep = addProcessStep(reader);
        processStepList.add(processStep);
    }


    @Override
    protected ProcessImpl createMaintainableArtefact() {
        return new ProcessImpl();
    }

    private ProcessStepImpl addProcessStep(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        var processStep = new ProcessStepImpl();
        List<ProcessArtefact> inputs = new ArrayList<>();
        List<ProcessArtefact> outputs = new ArrayList<>();
        List<ProcessStep> children = new ArrayList<>();
        List<Transition> transitions = new ArrayList<>();
        Map<String, String> names = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
            .ifPresent(processStep::setId);

        String uriString = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (isNotEmptyOrNullElementText(uriString)) {
            processStep.setUri(new URI(uriString));
        }

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.PROCESS_STEP)) {
            String name = reader.getLocalName();
            switch (name) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, processStep);
                    break;
                case XmlConstants.NAME:
                    nameableReader.setNameable(reader, names);
                    break;
                case XmlConstants.DESCRIPTION:
                    nameableReader.setNameable(reader, descriptions);
                    break;
                case XmlConstants.COMPUTATION:
                    processStep.setComputation(getComputation(reader));
                    break;
                case XmlConstants.INPUT:
                    inputs.add(getInputOutput(reader));
                    break;
                case XmlConstants.OUTPUT:
                    outputs.add(getInputOutput(reader));
                    break;
                case XmlConstants.TRANSITION:
                    transitions.add(getTransition(reader));
                    break;
                case XmlConstants.PROCESS_STEP:
                    children.add(addProcessStep(reader));
                    break;
                default:
                    throw new IllegalArgumentException("ProcessStep " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
            }
            moveToNextTag(reader);
        }
        processStep.setInputs(inputs);
        processStep.setOutputs(outputs);
        processStep.setChildren(children);
        processStep.setTransitions(transitions);
        processStep.setName(new InternationalString(names));
        processStep.setDescription(new InternationalString(descriptions));
        return processStep;
    }

    private Transition getTransition(XMLStreamReader reader) throws URISyntaxException, XMLStreamException {
        var transition = new TransitionImpl();

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
            .ifPresent(transition::setId);

        String uriString = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (isNotEmptyOrNullElementText(uriString)) {
            transition.setUri(new URI(uriString));
        }

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.LOCAL_ID))
            .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
            .ifPresent(transition::setLocalId);

        Map<String, String> conditions = new HashMap<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.TRANSITION)) {
            String name = reader.getLocalName();
            switch (name) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, transition);
                    break;
                case XmlConstants.TARGET_STEP:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(transition::setTargetProcessStep);
                    break;
                case XmlConstants.CONDITION:
                    nameableReader.setNameable(reader, conditions);
                    break;
                default:
                    throw new IllegalArgumentException("Transition " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
            }
            moveToNextTag(reader);
        }
        transition.setCondition(new InternationalString(conditions));
        return transition;
    }

    private ProcessArtefact getInputOutput(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        var processArtefact = new ProcessArtefactImpl();

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.LOCAL_ID))
            .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
            .ifPresent(processArtefact::setLocalId);

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.INPUT, XmlConstants.OUTPUT)) {
            String name = reader.getLocalName();
            switch (name) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, processArtefact);
                    break;
                case XmlConstants.OBJECT_REFERENCE:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .map(MaintainableArtefactReference::new)
                        .ifPresent(processArtefact::setArtefact);
                    break;
                default:
                    throw new IllegalArgumentException("ProcessArtefact " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
            }
            moveToNextTag(reader);
        }
        return processArtefact;
    }

    private ComputationImpl getComputation(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        var computation = new ComputationImpl();
        String localId = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.LOCAL_ID);
        if (isNotEmptyOrNullElementText(localId)) {
            computation.setLocalId(localId);
        }

        String softwareLang = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.SOFTWARE_LANGUAGE);
        if (isNotEmptyOrNullElementText(softwareLang)) {
            computation.setSoftwareLanguage(softwareLang);
        }

        String softwareVersion = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.SOFTWARE_VERSION);
        if (isNotEmptyOrNullElementText(softwareLang)) {
            computation.setSoftwareVersion(softwareVersion);
        }

        String softwarePackage = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.SOFTWARE_PACKAGE);
        if (isNotEmptyOrNullElementText(softwareLang)) {
            computation.setSoftwarePackage(softwarePackage);
        }

        Map<String, String> descriptions = new HashMap<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.COMPUTATION)) {
            String name = reader.getLocalName();
            switch (name) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, computation);
                    break;
                case XmlConstants.DESCRIPTION:
                    nameableReader.setNameable(reader, descriptions);
                    break;
                default:
                    throw new IllegalArgumentException("Computation " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
            }
            moveToNextTag(reader);
        }
        computation.setDescription(new InternationalString(descriptions));
        return computation;
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, ProcessImpl maintainableArtefact) throws XMLStreamException {
        setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.PROCESS;
    }

    @Override
    protected String getNames() {
        return XmlConstants.PROCESSES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<ProcessImpl> artefacts) {
        artefact.getProcesses().addAll(artefacts);
    }

    @Override
    protected void clean() {
        processStepList.clear();
    }
}
