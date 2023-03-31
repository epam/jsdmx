package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class IdentifiableArtefactReferenceImpl
    extends MaintainableArtefactReference
    implements ArtefactReference {

    private static final Map<StructureClass, StructureClass> CHILD_TO_PARENT_MAPPING = Map.of(
        StructureClassImpl.CONCEPT, StructureClassImpl.CONCEPT_SCHEME,
        StructureClassImpl.CATEGORY, StructureClassImpl.CATEGORY_SCHEME,
        StructureClassImpl.CODE, StructureClassImpl.CODELIST,
        StructureClassImpl.HIERARCHICAL_CODE, StructureClassImpl.HIERARCHY
    );

    private static final Map<StructureClass, StructureClass> PARENT_TO_CHILD_MAP =
        CHILD_TO_PARENT_MAPPING
            .entrySet()
            .stream()
            .collect(toMap(Map.Entry::getValue, Map.Entry::getKey));

    private String itemId;

    public IdentifiableArtefactReferenceImpl(String id,
                                             String agencyId,
                                             VersionReference version,
                                             StructureClass type,
                                             String itemId) {
        super(id, agencyId, version, PARENT_TO_CHILD_MAP.getOrDefault(type, type)); // if ConceptScheme arrives - desired type is Concept
        this.itemId = itemId;
    }

    public IdentifiableArtefactReferenceImpl(String id,
                                             String agencyId,
                                             String version,
                                             StructureClass type,
                                             String itemId) {
        super(id, agencyId, version, PARENT_TO_CHILD_MAP.getOrDefault(type, type)); // if ConceptScheme arrives - desired type is Concept
        this.itemId = itemId;
    }

    public IdentifiableArtefactReferenceImpl(String urn) {
        super(urn);
        final UrnComponents urnComponents = SdmxUrn.getUrnComponents(urn);
        if (urnComponents.getItemId() != null) {
            this.itemId = urnComponents.getItemId();
        }
    }

    public IdentifiableArtefactReferenceImpl(ArtefactReference structureReference, String itemId) {
        super(
            structureReference.getId(),
            structureReference.getOrganisationId(),
            structureReference.getVersionString(),
            // if ConceptScheme arrives - desired type is Concept
            PARENT_TO_CHILD_MAP.getOrDefault(structureReference.getStructureClass(), structureReference.getStructureClass())
        );
        this.itemId = itemId;
    }

    public IdentifiableArtefactReferenceImpl(ArtefactReference from) {
        super(Objects.requireNonNull(from));
        this.itemId = Objects.requireNonNull(from.getItemId());
    }

    @Override
    public boolean isItemReference() {
        return StringUtils.isNotEmpty(itemId);
    }

    @Override
    public StructureClass getStructureClass() {
        StructureClass type = super.getStructureClass();
        return PARENT_TO_CHILD_MAP.getOrDefault(type, type);
    }

    @Override
    public StructureClass getMaintainableStructureClass() {
        return parentType();
    }

    public String getUrn() {
        final String urn = super.getUrn();
        if (itemId != null) {
            return urn + "." + itemId;
        }
        return urn;
    }

    public ArtefactReference getMaintainableArtefactReference() {
        return new MaintainableArtefactReference(
            getId(),
            getOrganisationId(),
            getVersion(),
            parentType()
        ); // if Concept arrives - desired type is ConceptScheme
    }

    protected StructureClass parentType() {
        StructureClass type = getStructureClass();
        return CHILD_TO_PARENT_MAPPING.getOrDefault(type, type);
    }

    @Override
    public IdentifiableArtefactReferenceImpl clone() {
        return new IdentifiableArtefactReferenceImpl(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
