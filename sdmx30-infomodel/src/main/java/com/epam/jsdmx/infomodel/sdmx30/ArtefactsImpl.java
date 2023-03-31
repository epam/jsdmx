package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtefactsImpl implements Artefacts {

    private Set<MetadataStructureDefinition> metadataStructureDefinitions = new HashSet<>();
    private Set<Metadataflow> metadataflows = new HashSet<>();
    private Set<CategoryScheme> categorySchemes = new HashSet<>();
    private Set<Categorisation> categorisations = new HashSet<>();
    private Set<Dataflow> dataflows = new HashSet<>();
    private Set<Codelist> codelists = new HashSet<>();
    private Set<ConceptScheme> conceptSchemes = new HashSet<>();
    private Set<DataStructureDefinition> dataStructures = new HashSet<>();
    private Set<Hierarchy> hierarchies = new HashSet<>();
    private Set<StructureMap> structureMaps = new HashSet<>();
    private Set<RepresentationMap> representationMaps = new HashSet<>();
    private Set<DataConstraint> dataConstraints = new HashSet<>();
    private Set<GeographicCodelist> geographicCodelists = new HashSet<>();
    private Set<GeoGridCodelist> geoGridCodelists = new HashSet<>();
    private Set<ValueList> valueLists = new HashSet<>();
    private Set<HierarchyAssociation> hierarchyAssociations = new HashSet<>();
    private Set<AgencyScheme> agencySchemes = new HashSet<>();
    private Set<DataProviderScheme> dataProviderSchemes = new HashSet<>();
    private Set<DataConsumerScheme> dataConsumerSchemes = new HashSet<>();
    private Set<MetadataProviderScheme> metadataProviderSchemes = new HashSet<>();
    private Set<OrganisationUnitScheme> organisationUnitSchemes = new HashSet<>();
    private Set<ReportingTaxonomy> reportingTaxonomies = new HashSet<>();
    private Set<Process> processes = new HashSet<>();
    private Set<ProvisionAgreement> provisionAgreements = new HashSet<>();
    private Set<MetadataProvisionAgreement> metadataProvisionAgreements = new HashSet<>();
    private Set<MetadataConstraint> metadataConstraints = new HashSet<>();
    private Set<ConceptSchemeMap> conceptSchemeMaps = new HashSet<>();
    private Set<CategorySchemeMap> categorySchemeMaps = new HashSet<>();
    private Set<OrganisationSchemeMap> organisationSchemeMaps = new HashSet<>();
    private Set<ReportingTaxonomyMap> reportingTaxonomyMaps = new HashSet<>();

    public ArtefactsImpl(Collection<MaintainableArtefact> artefacts) {
        this.addMaintainables(artefacts);
    }

    protected boolean has(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public boolean hasMetadataStructureDefinitions() {
        return has(metadataStructureDefinitions);
    }

    public boolean hasMetadataflows() {
        return has(metadataflows);
    }

    public boolean hasCategorySchemes() {
        return has(categorySchemes);
    }

    public boolean hasCategorisations() {
        return has(categorisations);
    }

    public boolean hasDataflows() {
        return has(dataflows);
    }

    public boolean hasCodelists() {
        return has(codelists);
    }

    public boolean hasConceptSchemes() {
        return has(conceptSchemes);
    }

    public boolean hasDataStructures() {
        return has(dataStructures);
    }

    public boolean hasHierarchies() {
        return has(hierarchies);
    }

    public boolean hasStructureMaps() {
        return has(structureMaps);
    }

    public boolean hasRepresentationMaps() {
        return has(representationMaps);
    }

    @Override
    public boolean hasGeographicCodelists() {
        return has(geographicCodelists);
    }

    @Override
    public boolean hasGeoGridCodelists() {
        return has(geoGridCodelists);
    }

    @Override
    public boolean hasValueLists() {
        return has(valueLists);
    }

    @Override
    public boolean hasHierarchyAssociations() {
        return has(hierarchyAssociations);
    }

    @Override
    public boolean hasAgencySchemes() {
        return has(agencySchemes);
    }

    @Override
    public boolean hasDataProviderSchemes() {
        return has(dataProviderSchemes);
    }

    @Override
    public boolean hasDataConsumerSchemes() {
        return has(dataConsumerSchemes);
    }

    @Override
    public boolean hasMetadataProviderSchemes() {
        return has(metadataProviderSchemes);
    }

    @Override
    public boolean hasOrganisationUnitSchemes() {
        return has(organisationUnitSchemes);
    }

    @Override
    public boolean hasReportingTaxonomies() {
        return has(reportingTaxonomies);
    }

    @Override
    public boolean hasProvisionAgreements() {
        return has(provisionAgreements);
    }

    @Override
    public boolean hasMetadataProvisionAgreements() {
        return has(metadataProvisionAgreements);
    }

    @Override
    public boolean hasConceptSchemeMaps() {
        return has(conceptSchemeMaps);
    }

    @Override
    public boolean hasCategorySchemeMaps() {
        return has(categorySchemeMaps);
    }

    @Override
    public boolean hasOrganisationSchemeMaps() {
        return has(organisationSchemeMaps);
    }

    @Override
    public boolean hasReportingTaxonomyMaps() {
        return has(reportingTaxonomyMaps);
    }

    @Override
    public boolean hasProcesses() {
        return has(processes);
    }

    @Override
    public boolean hasMetadataConstraints() {
        return has(metadataConstraints);
    }

    @Override
    public boolean hasDataConstraints() {
        return has(dataConstraints);
    }

    public Set<MaintainableArtefact> getAll() {
        //noinspection unchecked
        return merge(
            metadataStructureDefinitions,
            metadataflows,
            categorySchemes,
            categorisations,
            dataflows,
            codelists,
            conceptSchemes,
            dataStructures,
            hierarchies,
            structureMaps,
            representationMaps,
            dataConstraints,
            geographicCodelists,
            geoGridCodelists,
            valueLists,
            hierarchyAssociations,
            agencySchemes,
            dataProviderSchemes,
            dataConsumerSchemes,
            metadataProviderSchemes,
            organisationUnitSchemes,
            reportingTaxonomies,
            processes,
            provisionAgreements,
            metadataProvisionAgreements,
            metadataConstraints,
            conceptSchemeMaps,
            categorySchemeMaps,
            organisationSchemeMaps,
            reportingTaxonomyMaps
        );
    }

    public void addMaintainables(Iterable<MaintainableArtefact> maintainables) {
        if (maintainables != null) {
            maintainables.forEach(this::addMaintainable);
        }
    }

    public void removeMaintainables(Iterable<MaintainableArtefact> maintainables) {
        if (maintainables != null) {
            maintainables.forEach(this::removeMaintainable);
        }
    }

    public void addMaintainable(MaintainableArtefact maintainable) {
        final StructureClass type = maintainable.getStructureClass();

        if (type == StructureClassImpl.METADATA_STRUCTURE) {
            metadataStructureDefinitions.add((MetadataStructureDefinition) maintainable);
        } else if (type == StructureClassImpl.METADATAFLOW) {
            metadataflows.add((Metadataflow) maintainable);
        } else if (type == StructureClassImpl.CATEGORY_SCHEME) {
            categorySchemes.add((CategoryScheme) maintainable);
        } else if (type == StructureClassImpl.CATEGORISATION) {
            categorisations.add((Categorisation) maintainable);
        } else if (type == StructureClassImpl.DATAFLOW) {
            dataflows.add((Dataflow) maintainable);
        } else if (type == StructureClassImpl.CODELIST) {
            codelists.add((Codelist) maintainable);
        } else if (type == StructureClassImpl.CONCEPT_SCHEME) {
            conceptSchemes.add((ConceptScheme) maintainable);
        } else if (type == StructureClassImpl.DATA_STRUCTURE) {
            dataStructures.add((DataStructureDefinition) maintainable);
        } else if (type == StructureClassImpl.HIERARCHY) {
            hierarchies.add((Hierarchy) maintainable);
        } else if (type == StructureClassImpl.STRUCTURE_MAP) {
            structureMaps.add((StructureMap) maintainable);
        } else if (type == StructureClassImpl.REPRESENTATION_MAP) {
            representationMaps.add((RepresentationMap) maintainable);
        } else if (type == StructureClassImpl.DATA_CONSTRAINT) {
            dataConstraints.add((DataConstraint) maintainable);
        } else if (type == StructureClassImpl.GEOGRAPHIC_CODELIST) {
            geographicCodelists.add((GeographicCodelist) maintainable);
        } else if (type == StructureClassImpl.GEO_GRID_CODELIST) {
            geoGridCodelists.add((GeoGridCodelist) maintainable);
        } else if (type == StructureClassImpl.VALUE_LIST) {
            valueLists.add((ValueList) maintainable);
        } else if (type == StructureClassImpl.HIERARCHY_ASSOCIATION) {
            hierarchyAssociations.add((HierarchyAssociation) maintainable);
        } else if (type == StructureClassImpl.AGENCY_SCHEME) {
            agencySchemes.add((AgencyScheme) maintainable);
        } else if (type == StructureClassImpl.DATA_PROVIDER_SCHEME) {
            dataProviderSchemes.add((DataProviderScheme) maintainable);
        } else if (type == StructureClassImpl.DATA_CONSUMER_SCHEME) {
            dataConsumerSchemes.add((DataConsumerScheme) maintainable);
        } else if (type == StructureClassImpl.METADATA_PROVIDER_SCHEME) {
            metadataProviderSchemes.add((MetadataProviderScheme) maintainable);
        } else if (type == StructureClassImpl.ORGANISATION_UNIT_SCHEME) {
            organisationUnitSchemes.add((OrganisationUnitScheme) maintainable);
        } else if (type == StructureClassImpl.REPORTING_TAXONOMY) {
            reportingTaxonomies.add((ReportingTaxonomy) maintainable);
        } else if (type == StructureClassImpl.PROCESS) {
            processes.add((Process) maintainable);
        } else if (type == StructureClassImpl.PROVISION_AGREEMENT) {
            provisionAgreements.add((ProvisionAgreement) maintainable);
        } else if (type == StructureClassImpl.METADATA_PROVISION_AGREEMENT) {
            metadataProvisionAgreements.add((MetadataProvisionAgreement) maintainable);
        } else if (type == StructureClassImpl.METADATA_CONSTRAINT) {
            metadataConstraints.add((MetadataConstraint) maintainable);
        } else if (type == StructureClassImpl.CONCEPT_SCHEME_MAP) {
            conceptSchemeMaps.add((ConceptSchemeMap) maintainable);
        } else if (type == StructureClassImpl.CATEGORY_SCHEME_MAP) {
            categorySchemeMaps.add((CategorySchemeMap) maintainable);
        } else if (type == StructureClassImpl.ORGANISATION_SCHEME_MAP) {
            organisationSchemeMaps.add((OrganisationSchemeMap) maintainable);
        } else if (type == StructureClassImpl.REPORTING_TAXONOMY_MAP) {
            reportingTaxonomyMaps.add((ReportingTaxonomyMap) maintainable);
        }
    }

    public void removeMaintainable(MaintainableArtefact maintainable) {
        final StructureClass type = maintainable.getStructureClass();

        if (type == StructureClassImpl.METADATA_STRUCTURE) {
            metadataStructureDefinitions.remove((MetadataStructureDefinition) maintainable);
        } else if (type == StructureClassImpl.METADATAFLOW) {
            metadataflows.remove((Metadataflow) maintainable);
        } else if (type == StructureClassImpl.CATEGORY_SCHEME) {
            categorySchemes.remove((CategoryScheme) maintainable);
        } else if (type == StructureClassImpl.CATEGORISATION) {
            categorisations.remove((Categorisation) maintainable);
        } else if (type == StructureClassImpl.DATAFLOW) {
            dataflows.remove((Dataflow) maintainable);
        } else if (type == StructureClassImpl.CODELIST) {
            codelists.remove((Codelist) maintainable);
        } else if (type == StructureClassImpl.CONCEPT_SCHEME) {
            conceptSchemes.remove((ConceptScheme) maintainable);
        } else if (type == StructureClassImpl.DATA_STRUCTURE) {
            dataStructures.remove((DataStructureDefinition) maintainable);
        } else if (type == StructureClassImpl.HIERARCHY) {
            hierarchies.remove((Hierarchy) maintainable);
        } else if (type == StructureClassImpl.STRUCTURE_MAP) {
            structureMaps.remove((StructureMap) maintainable);
        } else if (type == StructureClassImpl.REPRESENTATION_MAP) {
            representationMaps.remove((RepresentationMap) maintainable);
        } else if (type == StructureClassImpl.DATA_CONSTRAINT) {
            dataConstraints.remove((DataConstraint) maintainable);
        } else if (type == StructureClassImpl.GEOGRAPHIC_CODELIST) {
            geographicCodelists.remove((GeographicCodelist) maintainable);
        } else if (type == StructureClassImpl.GEO_GRID_CODELIST) {
            geoGridCodelists.remove((GeoGridCodelist) maintainable);
        } else if (type == StructureClassImpl.VALUE_LIST) {
            valueLists.remove((ValueList) maintainable);
        } else if (type == StructureClassImpl.HIERARCHY_ASSOCIATION) {
            hierarchyAssociations.remove((HierarchyAssociation) maintainable);
        } else if (type == StructureClassImpl.AGENCY_SCHEME) {
            agencySchemes.remove((AgencyScheme) maintainable);
        } else if (type == StructureClassImpl.DATA_PROVIDER_SCHEME) {
            dataProviderSchemes.remove((DataProviderScheme) maintainable);
        } else if (type == StructureClassImpl.DATA_CONSUMER_SCHEME) {
            dataConsumerSchemes.remove((DataConsumerScheme) maintainable);
        } else if (type == StructureClassImpl.METADATA_PROVIDER_SCHEME) {
            metadataProviderSchemes.remove((MetadataProviderScheme) maintainable);
        } else if (type == StructureClassImpl.ORGANISATION_UNIT_SCHEME) {
            organisationUnitSchemes.remove((OrganisationUnitScheme) maintainable);
        } else if (type == StructureClassImpl.REPORTING_TAXONOMY) {
            reportingTaxonomies.remove((ReportingTaxonomy) maintainable);
        } else if (type == StructureClassImpl.PROCESS) {
            processes.remove((Process) maintainable);
        } else if (type == StructureClassImpl.PROVISION_AGREEMENT) {
            provisionAgreements.remove((ProvisionAgreement) maintainable);
        } else if (type == StructureClassImpl.METADATA_PROVISION_AGREEMENT) {
            metadataProvisionAgreements.remove((MetadataProvisionAgreement) maintainable);
        } else if (type == StructureClassImpl.METADATA_CONSTRAINT) {
            metadataConstraints.remove((MetadataConstraint) maintainable);
        } else if (type == StructureClassImpl.CONCEPT_SCHEME_MAP) {
            conceptSchemeMaps.remove((ConceptSchemeMap) maintainable);
        } else if (type == StructureClassImpl.CATEGORY_SCHEME_MAP) {
            categorySchemeMaps.remove((CategorySchemeMap) maintainable);
        } else if (type == StructureClassImpl.ORGANISATION_SCHEME_MAP) {
            organisationSchemeMaps.remove((OrganisationSchemeMap) maintainable);
        } else if (type == StructureClassImpl.REPORTING_TAXONOMY_MAP) {
            reportingTaxonomyMaps.remove((ReportingTaxonomyMap) maintainable);
        }
    }

    @SuppressWarnings("unchecked")
    protected Set<MaintainableArtefact> merge(Set<? extends MaintainableArtefact>... sets) {
        final Set<MaintainableArtefact> result = new HashSet<>();
        for (Set<? extends MaintainableArtefact> set : sets) {
            if (set != null) {
                result.addAll(set);
            }
        }
        return result;
    }
}
