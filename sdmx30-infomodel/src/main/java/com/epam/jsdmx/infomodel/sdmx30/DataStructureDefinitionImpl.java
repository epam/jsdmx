package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataStructureDefinitionImpl
    extends StructureImpl
    implements DataStructureDefinition {

    private static final StructureClassImpl CS = StructureClassImpl.CONCEPT_SCHEME;

    private MeasureDescriptorImpl measureDescriptor;
    private DimensionDescriptorImpl dimensionDescriptor;
    private AttributeDescriptorImpl attributeDescriptor;
    private List<GroupDimensionDescriptorImpl> groupDimensionDescriptor = new ArrayList<>();

    private ArtefactReference metadataStructure;

    public DataStructureDefinitionImpl(DataStructureDefinition from) {
        super(from);

        this.setMeasureDescriptor(new MeasureDescriptorImpl(from.getMeasureDescriptor()));
        this.setDimensionDescriptor(new DimensionDescriptorImpl(from.getDimensionDescriptor()));
        this.setAttributeDescriptor(new AttributeDescriptorImpl(from.getAttributeDescriptor()));
        this.setGroupDimensionDescriptor(from.getGroupDimensionDescriptors().stream().map(GroupDimensionDescriptorImpl::new).collect(toList()));
        this.setMetadataStructure(from.getMetadataStructure());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.DATA_STRUCTURE;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        var references = new HashSet<CrossReference>();

        if (metadataStructure != null) {
            references.add(new CrossReferenceImpl(this, metadataStructure));
        }

        getDescriptorReferences()
            .forEach(conceptScheme -> references.add(new CrossReferenceImpl(this, conceptScheme)));

        return references;
    }

    private Set<ArtefactReference> getDescriptorReferences() {
        return getComponentListsStream()
            .map(ComponentList::getComponents)
            .flatMap(List::stream)
            .flatMap(this::getComponentReferences)
            .collect(toSet());
    }

    private Stream<ArtefactReference> getComponentReferences(Component component) {
        final var builder = Stream.<ArtefactReference>builder();

        builder.add(toReference(component.getConceptIdentity(), CS));
        builder.add(ofNullable(component.getLocalRepresentation()).map(Representation::enumerated).orElse(null));
        getConceptRoles(component).forEach(role -> builder.add(toReference(role, CS)));

        return builder
            .build()
            .filter(Objects::nonNull);
    }

    private List<ArtefactReference> getConceptRoles(Component component) {
        if (component instanceof Measure) {
            return emptyIfNull(((Measure) component).getConceptRoles());
        } else if (component instanceof DataAttribute) {
            return emptyIfNull(((DataAttribute) component).getConceptRoles());
        } else if (component instanceof Dimension) {
            return emptyIfNull(((Dimension) component).getConceptRoles());
        }
        return List.of();
    }

    private Stream<ComponentList<? extends Component>> getComponentListsStream() {
        return Stream.<ComponentList<? extends Component>>builder()
            .add(measureDescriptor)
            .add(dimensionDescriptor)
            .add(attributeDescriptor)
            .build()
            .filter(Objects::nonNull);
    }

    @Override
    protected DataStructureDefinitionImpl createInstance() {
        return new DataStructureDefinitionImpl();
    }

    @Override
    public Map<String, Component> getComponentsGroupedById() { //rename getComponentsGroupedById
        return getComponents()
            .stream()
            .collect(toMap(Component::getId, identity()));
    }

    @Override
    public DataStructureDefinition toStub() {
        return toStub(createInstance());
    }

    @Override
    public DataStructureDefinition toCompleteStub() {
        return toCompleteStub(createInstance());
    }

    @Override
    public List<GroupDimensionDescriptor> getGroupDimensionDescriptors() {
        return new ArrayList<>(groupDimensionDescriptor);
    }

    @Override
    public List<DataAttribute> getSeriesAttributes() {
        return getAttributeDescriptor().getComponents()
            .stream()
            .filter(component -> component.getAttributeRelationship() instanceof DimensionRelationship
                && isSeriesRelationship((DimensionRelationship) component.getAttributeRelationship())
            )
            .collect(toList());
    }

    private boolean isSeriesRelationship(DimensionRelationship relationship) {
        final List<String> relatedDimensions = relationship.getDimensions();
        final Set<String> seriesKeyDimensionIds = getSeriesKey();
        return relatedDimensions.size() == seriesKeyDimensionIds.size()
            && seriesKeyDimensionIds.containsAll(relatedDimensions);
    }

    @Override
    public List<DataAttribute> getObservationAttributes() {
        return getAttributeDescriptor()
            .getComponents().stream()
            .filter(component -> component.getAttributeRelationship() instanceof ObservationRelationship)
            .collect(toList());
    }

    @Override
    public List<DataAttribute> getDimensionGroupAttributes() {
        return getAttributeDescriptor()
            .getComponents().stream()
            .filter(component -> component.getAttributeRelationship() instanceof DimensionRelationship
                && !isSeriesRelationship((DimensionRelationship) component.getAttributeRelationship())
            )
            .collect(toList());
    }

    @Override
    public List<DataAttribute> getDataSetAttributes() {
        return getAttributeDescriptor()
            .getComponents().stream()
            .filter(component -> component.getAttributeRelationship() == null)
            .collect(toList());
    }

    private Set<String> getSeriesKey() {
        return getDimensionDescriptor()
            .getComponents()
            .stream()
            .filter(not(DimensionComponent::isTimeDimension))
            .map(Component::getId)
            .collect(toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataStructureDefinition)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataStructureDefinition)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        DataStructureDefinitionImpl that = (DataStructureDefinitionImpl) o;
        return Objects.equals(getMeasureDescriptor(), that.getMeasureDescriptor())
            && Objects.equals(getDimensionDescriptor(), that.getDimensionDescriptor())
            && Objects.equals(getAttributeDescriptor(), that.getAttributeDescriptor())
            && Objects.equals(getGroupDimensionDescriptor(), that.getGroupDimensionDescriptor())
            && Objects.equals(getMetadataStructure(), that.getMetadataStructure());
    }
}
