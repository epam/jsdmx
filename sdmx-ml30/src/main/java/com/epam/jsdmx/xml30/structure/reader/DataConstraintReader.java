package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ComponentValue;
import com.epam.jsdmx.infomodel.sdmx30.ComponentValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegion;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionImpl;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionKey;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionKeyImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraintImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataKey;
import com.epam.jsdmx.infomodel.sdmx30.DataKeyImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataKeySet;
import com.epam.jsdmx.infomodel.sdmx30.DataKeySetImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.infomodel.sdmx30.SelectionValue;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class DataConstraintReader extends XmlReader<DataConstraintImpl> {

    private final List<ArtefactReference> constrainedArtefacts = new ArrayList<>();
    private final ReleaseCalendarReader releaseCalendarReader;
    private final MemberSelectionReader memberSelectionReader;
    List<CubeRegion> cubeRegions = new ArrayList<>();
    List<DataKeySet> dataKeySets = new ArrayList<>();

    public DataConstraintReader(AnnotableReader annotableReader,
                                NameableReader nameableReader,
                                ReleaseCalendarReader releaseCalendarReader,
                                MemberSelectionReader memberSelectionReader) {
        super(annotableReader, nameableReader);
        this.releaseCalendarReader = releaseCalendarReader;
        this.memberSelectionReader = memberSelectionReader;
    }

    @Override
    protected DataConstraintImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        DataConstraintImpl dataConstraint = super.read(reader);
        dataConstraint.setCubeRegions(new ArrayList<>(cubeRegions));
        dataConstraint.setDataContentKeys(new ArrayList<>(dataKeySets));
        dataConstraint.setConstrainedArtefacts(new ArrayList<>(constrainedArtefacts));
        return dataConstraint;
    }

    @Override
    protected void read(XMLStreamReader reader, DataConstraintImpl maintainableArtefact) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.CONSTRAINT_ATTACHMENT:
                readConstraintsAttachments(reader);
                break;
            case XmlConstants.RELEASE_CALENDAR:
                maintainableArtefact.setReleaseCalendar(releaseCalendarReader.getReleaseCalendar(reader));
                break;
            case XmlConstants.CUBE_REGION:
                cubeRegions.add(getCubeRegion(reader));
                break;
            case XmlConstants.DATA_KEY_SET:
                dataKeySets.add(getDataKeySet(reader));
                break;
            default:
                throw new IllegalArgumentException("DataConstraint " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
    }

    @Override
    protected DataConstraintImpl createMaintainableArtefact() {
        return new DataConstraintImpl();
    }

    private DataKeySet getDataKeySet(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        var dataKeySet = new DataKeySetImpl();
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_INCLUDED))
            .map(Boolean::parseBoolean)
            .ifPresent(dataKeySet::setIncluded);

        List<DataKey> keys = new ArrayList<>();

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.DATA_KEY_SET)) {
            String localName = reader.getLocalName();
            if (!XmlConstants.KEY.equals(localName)) {
                throw new IllegalArgumentException("DataKeySet " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            keys.add(getKey(reader));
            moveToNextTag(reader);
        }
        dataKeySet.setKeys(keys);
        return dataKeySet;
    }

    private DataKey getKey(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        var dataKey = new DataKeyImpl();
        List<ComponentValue> keyValues = new ArrayList<>();
        List<MemberSelection> memberSelections = new ArrayList<>();
        String validTo = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALID_TO);
        String validFrom = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALID_FROM);
        dataKey.setValidFrom(validFrom == null ? null : Instant.parse(validFrom));
        dataKey.setValidTo(validTo == null ? null : Instant.parse(validTo));
        dataKey.setIncluded(XmlReaderUtils.getIncluded(reader));

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.KEY)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, dataKey);
                    break;
                case XmlConstants.COMPONENT:
                    memberSelections.add(memberSelectionReader.getMemberSelection(reader));
                    break;
                case XmlConstants.KEY_VALUE:
                    keyValues.add(getComponentValues(reader));
                    break;
                default:
                    throw new IllegalArgumentException("DataKey " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        dataKey.setKeyValues(keyValues);
        dataKey.setMemberSelections(memberSelections);
        return dataKey;
    }

    private ComponentValue getComponentValues(XMLStreamReader reader) throws XMLStreamException {
        var componentValue = new ComponentValueImpl();

        componentValue.setIncluded(XmlReaderUtils.getIncluded(reader));
        componentValue.setRemovePrefix(XmlReaderUtils.getRemovePrefix(reader));
        componentValue.setComponentId(XmlReaderUtils.getId(reader));

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.COMPONENT, XmlConstants.KEY_VALUE)) {
            String localName = reader.getLocalName();
            if (!XmlConstants.VALUE.equals(localName)) {
                throw new IllegalArgumentException("ComponentValue " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }

            Optional.ofNullable(reader.getElementText())
                .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                .ifPresent(componentValue::setValue);

            moveToNextTag(reader);
        }
        return componentValue;
    }

    private CubeRegion getCubeRegion(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        var cubeRegion = new CubeRegionImpl();
        List<MemberSelection> memberSelections = new ArrayList<>();
        List<CubeRegionKey> keyValues = new ArrayList<>();
        cubeRegion.setIncluded(XmlReaderUtils.getIncluded(reader));

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.CUBE_REGION)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, cubeRegion);
                    break;
                case XmlConstants.COMPONENT:
                    memberSelections.add(memberSelectionReader.getMemberSelection(reader));
                    break;
                case XmlConstants.KEY_VALUE:
                    keyValues.add(getCubeRegionKeys(reader));
                    break;
                default:
                    throw new IllegalArgumentException("CubeRegion " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        cubeRegion.setMemberSelections(memberSelections);
        cubeRegion.setCubeRegionKeys(keyValues);
        return cubeRegion;
    }

    private CubeRegionKey getCubeRegionKeys(XMLStreamReader reader) throws XMLStreamException {
        var cubeRegion = new CubeRegionKeyImpl();
        String validTo = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALID_TO);
        String validFrom = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALID_FROM);

        if (XmlReaderUtils.isNotEmptyOrNullElementText(validTo)) {
            cubeRegion.setValidTo(Instant.parse(validTo));
        }

        if (XmlReaderUtils.isNotEmptyOrNullElementText(validFrom)) {
            cubeRegion.setValidFrom(Instant.parse(validFrom));
        }
        cubeRegion.setIncluded(XmlReaderUtils.getIncluded(reader));
        cubeRegion.setRemovePrefix(XmlReaderUtils.getRemovePrefix(reader));
        cubeRegion.setComponentId(XmlReaderUtils.getId(reader));

        List<SelectionValue> selectionValues = memberSelectionReader.getSelectionValues(reader);
        cubeRegion.setSelectionValues(selectionValues);
        return cubeRegion;
    }

    private void readConstraintsAttachments(XMLStreamReader reader) throws XMLStreamException {
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.CONSTRAINT_ATTACHMENT)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.DATA_PROVIDER:
                case XmlConstants.DATA_STRUCTURE:
                case XmlConstants.DATAFLOW:
                case XmlConstants.PROVISION_AGREEMENT:
                    addArtefactReference(reader);
                    break;
                default:
                    throw new IllegalArgumentException("ConstraintsAttachments " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
    }

    private void addArtefactReference(XMLStreamReader reader) throws XMLStreamException {
        Optional.ofNullable(reader.getElementText())
            .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
            .map(IdentifiableArtefactReferenceImpl::new)
            .ifPresent(constrainedArtefacts::add);
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, DataConstraintImpl dataConstraint) throws XMLStreamException {
        XmlReaderUtils.setCommonAttributes(reader, dataConstraint);
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ROLE_ATTR))
            .map(XmlConstants.STRING_CONSTRAINT_ROLE::get)
            .ifPresent(dataConstraint::setConstraintRoleType);
    }

    @Override
    protected String getName() {
        return XmlConstants.DATA_CONSTRAINT;
    }

    @Override
    protected String getNames() {
        return XmlConstants.DATA_CONSTRAINTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<DataConstraintImpl> artefacts) {
        artefact.getDataConstraints().addAll(artefacts);
    }

    @Override
    protected void clean() {
        constrainedArtefacts.clear();
        cubeRegions.clear();
        dataKeySets.clear();
    }
}
