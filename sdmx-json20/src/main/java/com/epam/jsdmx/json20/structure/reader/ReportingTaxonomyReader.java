package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.ReportingCategory;
import com.epam.jsdmx.infomodel.sdmx30.ReportingCategoryImpl;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomyImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class ReportingTaxonomyReader extends MaintainableReader<ReportingTaxonomyImpl> {

    private final NameableReader nameableReader;

    public ReportingTaxonomyReader(VersionableReader versionableArtefact,
                                   NameableReader nameableReader) {
        super(versionableArtefact);
        this.nameableReader = nameableReader;
    }

    @Override
    protected ReportingTaxonomyImpl createMaintainableArtefact() {
        return new ReportingTaxonomyImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, ReportingTaxonomyImpl reportingTaxonomy) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.IS_PARTIAL:
                reportingTaxonomy.setPartial(ReaderUtils.getBooleanJsonField(parser));
                break;
            case StructureUtils.REPORTING_CATEGORIES:
                List<ReportingCategory> reportingCategories = ReaderUtils.getArray(parser, (this::getReportingCategory));
                if (CollectionUtils.isNotEmpty(reportingCategories)) {
                    reportingTaxonomy.setItems(reportingCategories);
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "ReportingTaxonomy: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.REPORTING_TAXONOMIES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<ReportingTaxonomyImpl> artefacts) {
        artefact.getReportingTaxonomies().addAll(artefacts);
    }

    private ReportingCategory getReportingCategory(JsonParser parser) {
        ReportingCategoryImpl reportingCategory = new ReportingCategoryImpl();
        try {
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.REPORTING_CATEGORIES:
                        parser.nextToken();
                        reportingCategory.setHierarchy(ReaderUtils.getArray(parser, (this::getReportingCategory)));
                        break;
                    case StructureUtils.STRUCTURAL_METADATA:
                        List<String> structureReferenceUris = ReaderUtils.getListStrings(parser);
                        List<ArtefactReference> structureRef = structureReferenceUris.stream().filter(Objects::nonNull)
                            .map(MaintainableArtefactReference::new)
                            .collect(Collectors.toList());
                        reportingCategory.setStructures(structureRef);
                        break;
                    case StructureUtils.PROVISIONING_METADATA:
                        List<String> provisioningReferenceUris = ReaderUtils.getListStrings(parser);
                        List<ArtefactReference> provisionRef = provisioningReferenceUris.stream().filter(Objects::nonNull)
                            .map(MaintainableArtefactReference::new)
                            .collect(Collectors.toList());
                        reportingCategory.setFlows(provisionRef);
                        break;
                    default:
                        nameableReader.read(reportingCategory, parser);
                        break;
                }
            }
            return reportingCategory;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }
}