package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Set;

/**
 * Aggregate response of the structure registry containing maintainable artefacts
 */
public interface Artefacts {

    /**
     * @return Collection of {@link MetadataStructureDefinition}
     */
    Set<MetadataStructureDefinition> getMetadataStructureDefinitions();

    /**
     * @return Collection of {@link Metadataflow}
     */
    Set<Metadataflow> getMetadataflows();

    /**
     * @return Collection of {@link CategoryScheme}
     */
    Set<CategoryScheme> getCategorySchemes();

    /**
     * @return Collection of {@link Categorisation}
     */
    Set<Categorisation> getCategorisations();

    /**
     * @return Collection of {@link Dataflow}
     */
    Set<Dataflow> getDataflows();

    /**
     * @return Collection of {@link Codelist}
     */
    Set<Codelist> getCodelists();

    /**
     * @return Collection of {@link GeographicCodelist}
     */
    Set<GeographicCodelist> getGeographicCodelists();

    /**
     * @return Collection of {@link GeoGridCodelist}
     */
    Set<GeoGridCodelist> getGeoGridCodelists();

    /**
     * @return Collection of {@link ValueList}
     */
    Set<ValueList> getValueLists();

    /**
     * @return Collection of {@link HierarchyAssociation}
     */
    Set<HierarchyAssociation> getHierarchyAssociations();

    /**
     * @return Collection of {@link AgencyScheme}
     */
    Set<AgencyScheme> getAgencySchemes();

    /**
     * @return Collection of {@link DataProviderScheme}
     */
    Set<DataProviderScheme> getDataProviderSchemes();

    /**
     * @return Collection of {@link DataConsumerScheme}
     */
    Set<DataConsumerScheme> getDataConsumerSchemes();

    /**
     * @return Collection of {@link MetadataProviderScheme}
     */
    Set<MetadataProviderScheme> getMetadataProviderSchemes();

    /**
     * @return Collection of {@link OrganisationUnitScheme}
     */
    Set<OrganisationUnitScheme> getOrganisationUnitSchemes();

    /**
     * @return Collection of {@link ReportingTaxonomy}
     */
    Set<ReportingTaxonomy> getReportingTaxonomies();

    /**
     * @return Collection of {@link ProvisionAgreement}
     */
    Set<ProvisionAgreement> getProvisionAgreements();

    /**
     * @return Collection of {@link MetadataProvisionAgreement}
     */
    Set<MetadataProvisionAgreement> getMetadataProvisionAgreements();

    /**
     * @return Collection of {@link ConceptSchemeMap}
     */
    Set<ConceptSchemeMap> getConceptSchemeMaps();

    /**
     * @return Collection of {@link CategorySchemeMap}
     */
    Set<CategorySchemeMap> getCategorySchemeMaps();

    /**
     * @return Collection of {@link OrganisationSchemeMap}
     */
    Set<OrganisationSchemeMap> getOrganisationSchemeMaps();

    /**
     * @return Collection of {@link ReportingTaxonomyMap}
     */
    Set<ReportingTaxonomyMap> getReportingTaxonomyMaps();

    /**
     * @return Collection of {@link Process}
     */
    Set<Process> getProcesses();

    /**
     * @return Collection of {@link MetadataConstraint}
     */
    Set<MetadataConstraint> getMetadataConstraints();

    /**
     * @return Collection of {@link ConceptScheme}
     */
    Set<ConceptScheme> getConceptSchemes();

    /**
     * @return Collection of {@link DataStructureDefinition}
     */
    Set<DataStructureDefinition> getDataStructures();

    /**
     * @return Collection of {@link DataConstraint}
     */
    Set<DataConstraint> getDataConstraints();

    /**
     * @return Collection of {@link Hierarchy}
     */
    Set<Hierarchy> getHierarchies();

    /**
     * @return Collection of {@link StructureMap}
     */
    Set<StructureMap> getStructureMaps();

    /**
     * @return Collection of {@link RepresentationMap}
     */
    Set<RepresentationMap> getRepresentationMaps();

    /**
     * @return All {@link MaintainableArtefact MaintainableArtefacts}
     */
    Set<MaintainableArtefact> getAll();

    /**
     * @return Presence of {@link MetadataStructureDefinition}
     */
    boolean hasMetadataStructureDefinitions();

    /**
     * @return Presence of {@link Metadataflow}
     */
    boolean hasMetadataflows();

    /**
     * @return Presence of {@link CategoryScheme}
     */
    boolean hasCategorySchemes();

    /**
     * @return Presence of {@link Categorisation}
     */
    boolean hasCategorisations();

    /**
     * @return Presence of {@link Dataflow}
     */
    boolean hasDataflows();

    /**
     * @return Presence of {@link Codelist}
     */
    boolean hasCodelists();

    /**
     * @return Presence of {@link ConceptScheme}
     */
    boolean hasConceptSchemes();

    /**
     * @return Presence of {@link DataStructureDefinition}
     */
    boolean hasDataStructures();

    /**
     * @return Presence of {@link Hierarchy}
     */
    boolean hasHierarchies();

    /**
     * @return Presence of {@link StructureMap}
     */
    boolean hasStructureMaps();

    /**
     * @return Presence of {@link RepresentationMap}
     */
    boolean hasRepresentationMaps();

    /**
     * @return Presence of {@link GeographicCodelist}
     */
    boolean hasGeographicCodelists();

    /**
     * @return Presence of {@link GeoGridCodelist}
     */
    boolean hasGeoGridCodelists();

    /**
     * @return Presence of {@link ValueList}
     */
    boolean hasValueLists();

    /**
     * @return Presence of {@link HierarchyAssociation}
     */
    boolean hasHierarchyAssociations();

    /**
     * @return Presence of {@link AgencyScheme}
     */
    boolean hasAgencySchemes();

    /**
     * @return Presence of {@link DataProviderScheme}
     */
    boolean hasDataProviderSchemes();

    /**
     * @return Presence of {@link DataConsumerScheme}
     */
    boolean hasDataConsumerSchemes();

    /**
     * @return Presence of {@link MetadataProviderScheme}
     */
    boolean hasMetadataProviderSchemes();

    /**
     * @return Presence of {@link OrganisationUnitScheme}
     */
    boolean hasOrganisationUnitSchemes();

    /**
     * @return Presence of {@link ReportingTaxonomy}
     */
    boolean hasReportingTaxonomies();

    /**
     * @return Presence of {@link ProvisionAgreement}
     */
    boolean hasProvisionAgreements();

    /**
     * @return Presence of {@link MetadataProvisionAgreement}
     */
    boolean hasMetadataProvisionAgreements();

    /**
     * @return Presence of {@link ConceptSchemeMap}
     */
    boolean hasConceptSchemeMaps();

    /**
     * @return Presence of {@link CategorySchemeMap}
     */
    boolean hasCategorySchemeMaps();

    /**
     * @return Presence of {@link OrganisationSchemeMap}
     */
    boolean hasOrganisationSchemeMaps();

    /**
     * @return Presence of {@link ReportingTaxonomyMap}
     */
    boolean hasReportingTaxonomyMaps();

    /**
     * @return Presence of {@link Process}
     */
    boolean hasProcesses();

    /**
     * @return Presence of {@link MetadataConstraint}
     */
    boolean hasMetadataConstraints();

    /**
     * @return Presence of {@link DataConstraint}
     */
    boolean hasDataConstraints();

    int size();
}
