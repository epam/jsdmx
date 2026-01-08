package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getBooleanJsonField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnit;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnitImpl;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationUnitSchemeImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.collections.CollectionUtils;

public class OrganisationUnitSchemeReader extends MaintainableReader<OrganisationUnitSchemeImpl> {

    private final OrganisationReader organisationReader;
    private final Map<OrganisationUnitImpl, String> organisationUnitStringMap = new HashMap<>();

    public OrganisationUnitSchemeReader(VersionableReader versionableArtefact,
                                        OrganisationReader organisationReader) {
        super(versionableArtefact);
        this.organisationReader = organisationReader;
    }

    @Override
    protected OrganisationUnitSchemeImpl createMaintainableArtefact() {
        return new OrganisationUnitSchemeImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, OrganisationUnitSchemeImpl organisationUnitScheme) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.IS_PARTIAL:
                organisationUnitScheme.setPartial(getBooleanJsonField(parser));
                break;
            case StructureUtils.ORGANISATION_UNITS:
                List<OrganisationUnit> organisationUnits = ReaderUtils.getArray(parser, (this::getOrganisationUnit));
                if (CollectionUtils.isNotEmpty(organisationUnits)) {
                    Map<OrganisationUnitImpl, List<OrganisationUnitImpl>> organisationListMap = organisationReader.formHierarchy(organisationUnitStringMap);
                    for (Map.Entry<OrganisationUnitImpl, List<OrganisationUnitImpl>> organisationHier : organisationListMap.entrySet()) {
                        List<OrganisationUnitImpl> organisationUnitImplList = organisationHier.getValue();
                        List<OrganisationUnit> organisationUnitList = new ArrayList<>(organisationUnitImplList);
                        organisationHier.getKey().setHierarchy(organisationUnitList);
                    }
                    organisationUnitScheme.setItems(organisationUnits);
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "OrganisationUnitScheme: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.ORGANISATION_UNIT_SCHEMES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<OrganisationUnitSchemeImpl> artefacts) {
        artefact.getOrganisationUnitSchemes().addAll(artefacts);
    }

    private OrganisationUnit getOrganisationUnit(JsonParser parser) {
        return organisationReader.getOrganisation(parser, new OrganisationUnitImpl(), organisationUnitStringMap);
    }

    @Override
    protected void clean() {
        organisationUnitStringMap.clear();
    }
}
