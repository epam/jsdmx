package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.writer.XmlWriterUtils.writeCharacters;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Computation;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.Process;
import com.epam.jsdmx.infomodel.sdmx30.ProcessArtefact;
import com.epam.jsdmx.infomodel.sdmx30.ProcessStep;
import com.epam.jsdmx.infomodel.sdmx30.Transition;

import org.apache.commons.collections.CollectionUtils;

public class ProcessWriter extends XmlWriter<Process> {

    public ProcessWriter(NameableWriter nameableWriter,
                         AnnotableWriter annotableWriter,
                         CommonAttributesWriter commonAttributesWriter,
                         LinksWriter linksWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
    }

    @Override
    protected void writeAttributes(Process artefact, XMLStreamWriter writer) throws XMLStreamException {
        commonAttributesWriter.writeAttributes(artefact, writer);
    }

    @Override
    protected void writeCustomAttributeElements(Process process, XMLStreamWriter writer) throws XMLStreamException {
        List<ProcessStep> processSteps = process.getSteps();
        writeProcessSteps(writer, processSteps);
    }

    private void writeProcessSteps(XMLStreamWriter writer, List<ProcessStep> processSteps) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(processSteps)) {
            for (ProcessStep processStep : processSteps) {
                if (processStep != null) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.PROCESS_STEP);
                    XmlWriterUtils.writeIdUriAttributes(writer, processStep.getId(), processStep.getUri());
                    if (processStep.getContainer() != null) {
                        writer.writeAttribute(XmlConstants.URN, processStep.getUrn());
                    }

                    annotableWriter.write(processStep, writer);
                    nameableWriter.write(processStep, writer);

                    List<ProcessArtefact> inputs = processStep.getInputs();
                    writeInputOutput(writer, inputs, XmlConstants.INPUT);

                    List<ProcessArtefact> outputs = processStep.getOutputs();
                    writeInputOutput(writer, outputs, XmlConstants.OUTPUT);

                    Computation computation = processStep.getComputation();
                    writeComputation(computation, writer);

                    List<Transition> transitions = processStep.getTransitions();
                    writeTransitions(writer, transitions);

                    writeProcessSteps(writer, processStep.getChildren());
                    writer.writeEndElement();
                }
            }
        }
    }

    private void writeTransitions(XMLStreamWriter writer, List<Transition> transitions) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(transitions)) {
            for (Transition transition : transitions) {
                if (transition != null) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.TRANSITION);
                    XmlWriterUtils.writeIdUriAttributes(writer, transition.getId(), transition.getUri());
                    if (transition.getContainer() != null) {
                        writer.writeAttribute(XmlConstants.URN, transition.getUrn());
                    }

                    String localId = transition.getLocalId();
                    if (localId != null) {
                        writer.writeAttribute(XmlConstants.LOCAL_ID, localId);
                    }

                    annotableWriter.write(transition, writer);

                    writeTargetProcessStep(writer, transition);

                    writeCondition(writer, transition);

                    writer.writeEndElement();
                }
            }
        }
    }

    private void writeCondition(XMLStreamWriter writer, Transition transition) throws XMLStreamException {
        InternationalString condition = transition.getCondition();
        XmlWriterUtils.writeInternationalString(condition, writer, XmlConstants.STRUCTURE + XmlConstants.CONDITION);
    }

    private void writeTargetProcessStep(XMLStreamWriter writer, Transition transition) throws XMLStreamException {
        String targetProcessStep = transition.getTargetProcessStep();
        if (targetProcessStep != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.TARGET_STEP);
            writeCharacters(targetProcessStep, writer);
            writer.writeEndElement();
        }
    }

    private void writeInputOutput(XMLStreamWriter writer, List<ProcessArtefact> inputOutputs, String inputOutputName) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(inputOutputs)) {
            for (ProcessArtefact inputOutput : inputOutputs) {
                if (inputOutput != null) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + inputOutputName);

                    String localId = inputOutput.getLocalId();
                    if (localId != null) {
                        writer.writeAttribute(XmlConstants.LOCAL_ID, localId);
                    }

                    annotableWriter.write(inputOutput, writer);

                    ArtefactReference artefact = inputOutput.getArtefact();
                    if (artefact != null) {
                        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.OBJECT_REFERENCE);
                        writeCharacters(artefact.getUrn(), writer);
                        writer.writeEndElement();
                    }

                    writer.writeEndElement();
                }
            }
        }
    }

    private void writeComputation(Computation computation, XMLStreamWriter writer) throws XMLStreamException {
        if (computation != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.COMPUTATION);

            String localId = computation.getLocalId();
            if (localId != null) {
                writer.writeAttribute(XmlConstants.LOCAL_ID, localId);
            }

            String softwareLanguage = computation.getSoftwareLanguage();
            if (softwareLanguage != null) {
                writer.writeAttribute(XmlConstants.SOFTWARE_LANGUAGE, softwareLanguage);
            }

            String softwareVersion = computation.getSoftwareVersion();
            if (softwareVersion != null) {
                writer.writeAttribute(XmlConstants.SOFTWARE_VERSION, softwareVersion);
            }

            String softwarePackage = computation.getSoftwarePackage();
            if (softwarePackage != null) {
                writer.writeAttribute(XmlConstants.SOFTWARE_PACKAGE, softwarePackage);
            }

            annotableWriter.write(computation, writer);
            XmlWriterUtils.writeInternationalString(computation.getDescription(), writer, XmlConstants.COMMON + XmlConstants.DESCRIPTION);
            writer.writeEndElement();
        }
    }

    @Override
    protected String getName() {
        return XmlConstants.PROCESS;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.PROCESSES;
    }

    @Override
    protected Set<Process> extractArtefacts(Artefacts artefacts) {
        return artefacts.getProcesses();
    }
}
