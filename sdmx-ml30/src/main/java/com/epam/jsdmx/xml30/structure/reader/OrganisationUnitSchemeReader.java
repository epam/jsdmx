package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setAgencyId;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setExternalReference;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setId;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setServiceUrl;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setStructureUrl;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setUri;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setValidFrom;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setValidTo;

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
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnit;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnitImpl;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnitSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class OrganisationUnitSchemeReader extends XmlReader<OrganisationUnitSchemeImpl> {

    private final List<OrganisationUnit> organisationUnitList = new ArrayList<>();
    private final OrganisationReader organisationReader;
    private final Map<OrganisationUnitImpl, String> organisationUnitStringMap = new HashMap<>();


    public OrganisationUnitSchemeReader(AnnotableReader annotableReader,
                                        NameableReader nameableReader, OrganisationReader organisationReader) {
        super(annotableReader, nameableReader);
        this.organisationReader = organisationReader;
    }

    @Override
    protected OrganisationUnitSchemeImpl createMaintainableArtefact() {
        return new OrganisationUnitSchemeImpl();
    }

    @Override
    protected OrganisationUnitSchemeImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        OrganisationUnitSchemeImpl organisationUnitScheme = super.read(reader);
        if (CollectionUtils.isNotEmpty(organisationUnitList)) {
            Map<OrganisationUnitImpl, List<OrganisationUnitImpl>> organisationListMap = organisationReader.formHierarchy(organisationUnitStringMap);
            for (Map.Entry<OrganisationUnitImpl, List<OrganisationUnitImpl>> organisationHier : organisationListMap.entrySet()) {
                List<OrganisationUnitImpl> organisationUnitImplList = organisationHier.getValue();
                List<OrganisationUnit> hierarchy = new ArrayList<>(organisationUnitImplList);
                organisationHier.getKey().setHierarchy(hierarchy);
            }
        }
        organisationUnitScheme.setItems(new ArrayList<>(organisationUnitList));
        return organisationUnitScheme;
    }

    @Override
    protected void read(XMLStreamReader reader, OrganisationUnitSchemeImpl organisationUnitScheme) throws URISyntaxException, XMLStreamException {
        String name = reader.getLocalName();
        if (!XmlConstants.ORGANISATION_UNIT.equals(name)) {
            throw new IllegalArgumentException("OrganisationUnitScheme " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
        }
        OrganisationUnit organisationUnit = organisationReader.addOrganisation(reader, new OrganisationUnitImpl(), organisationUnitStringMap);
        organisationUnitList.add(organisationUnit);
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, OrganisationUnitSchemeImpl artefact) throws XMLStreamException {
        setUri(reader, artefact);

        setStructureUrl(reader, artefact);

        setServiceUrl(reader, artefact);

        setId(reader, artefact);

        artefact.setVersion(Version.createFromString(XmlConstants.DEFAULT_VERSION));

        setAgencyId(reader, artefact);

        setExternalReference(reader, artefact);

        setValidTo(reader, artefact);

        setValidFrom(reader, artefact);

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_PARTIAL))
            .map(Boolean::parseBoolean)
            .ifPresent(artefact::setPartial);
    }

    @Override
    protected String getName() {
        return XmlConstants.ORGANISATION_UNIT_SCHEME;
    }

    @Override
    protected String getNames() {
        return XmlConstants.ORGANISATION_UNIT_SCHEMES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<OrganisationUnitSchemeImpl> artefacts) {
        artefact.getOrganisationUnitSchemes().addAll(artefacts);
    }

    @Override
    protected void clean() {
        organisationUnitList.clear();
    }
}
