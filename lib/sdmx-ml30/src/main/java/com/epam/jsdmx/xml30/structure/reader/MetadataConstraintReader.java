package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setCommonAttributes;

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
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.infomodel.sdmx30.MetadataConstraintImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataTargetRegion;
import com.epam.jsdmx.infomodel.sdmx30.MetadataTargetRegionImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class MetadataConstraintReader extends XmlReader<MetadataConstraintImpl> {

    private final List<ArtefactReference> constrainedArtefacts = new ArrayList<>();
    private final ReleaseCalendarReader releaseCalendarReader;
    private final MemberSelectionReader memberSelectionReader;
    List<MetadataTargetRegion> metadataTargetRegions = new ArrayList<>();

    public MetadataConstraintReader(AnnotableReader annotableReader,
                                    NameableReader nameableReader,
                                    MemberSelectionReader memberSelectionReader,
                                    ReleaseCalendarReader releaseCalendarReader) {
        super(annotableReader, nameableReader);
        this.memberSelectionReader = memberSelectionReader;
        this.releaseCalendarReader = releaseCalendarReader;
    }

    @Override
    protected MetadataConstraintImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        MetadataConstraintImpl metadataConstraint = super.read(reader);
        metadataConstraint.setConstrainedArtefacts(new ArrayList<>(constrainedArtefacts));
        metadataConstraint.setMetadataTargetRegions(new ArrayList<>(metadataTargetRegions));
        return metadataConstraint;
    }

    @Override
    protected void read(XMLStreamReader reader, MetadataConstraintImpl maintainableArtefact) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.CONSTRAINT_ATTACHMENT:
                readConstraintsAttachments(reader);
                break;
            case XmlConstants.RELEASE_CALENDAR:
                maintainableArtefact.setReleaseCalendar(releaseCalendarReader.getReleaseCalendar(reader));
                break;
            case XmlConstants.METADATA_TARGET_REGION:
                metadataTargetRegions.add(getMetadataTargetRegion(reader));
                break;
            default:
                throw new IllegalArgumentException("MetadataConstraint " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
    }

    @Override
    protected MetadataConstraintImpl createMaintainableArtefact() {
        return new MetadataConstraintImpl();
    }

    private MetadataTargetRegion getMetadataTargetRegion(XMLStreamReader reader) throws XMLStreamException {
        var metadataTargetRegion = new MetadataTargetRegionImpl();

        List<MemberSelection> memberSelections = new ArrayList<>();
        metadataTargetRegion.setIncluded(XmlReaderUtils.getIncluded(reader));

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALID_TO))
            .map(Instant::parse)
            .ifPresent(metadataTargetRegion::setValidTo);

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALID_FROM))
            .map(Instant::parse)
            .ifPresent(metadataTargetRegion::setValidFrom);

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.METADATA_TARGET_REGION)) {
            String localName = reader.getLocalName();
            if (!XmlConstants.COMPONENT.equals(localName)) {
                throw new IllegalArgumentException("MetadataTargetRegion " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            memberSelections.add(memberSelectionReader.getMemberSelection(reader));

            moveToNextTag(reader);
        }
        metadataTargetRegion.setMemberSelections(memberSelections);
        return metadataTargetRegion;
    }

    private void readConstraintsAttachments(XMLStreamReader reader) throws XMLStreamException {
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.CONSTRAINT_ATTACHMENT)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.METADATA_PROVIDER:
                case XmlConstants.METADATA_SET:
                case XmlConstants.METADATA_STRUCTURE:
                case XmlConstants.METADATAFLOW:
                case XmlConstants.METADATA_PROVISION_AGREEMENT:
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
    protected void setAttributes(XMLStreamReader reader, MetadataConstraintImpl maintainableArtefact) throws XMLStreamException {
        setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.METADATA_CONSTRAINT;
    }

    @Override
    protected String getNames() {
        return XmlConstants.METADATA_CONSTRAINTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<MetadataConstraintImpl> artefacts) {
        artefact.getMetadataConstraints().addAll(artefacts);
    }

    @Override
    protected void clean() {
        constrainedArtefacts.clear();
        metadataTargetRegions.clear();
    }
}
