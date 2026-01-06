package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.infomodel.sdmx30.ReportingTaxonomyMapImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.collections.CollectionUtils;

public class ReportingTaxonomyMapReader extends MaintainableReader<ReportingTaxonomyMapImpl> implements ItemMapReader {

    public ReportingTaxonomyMapReader(VersionableReader versionableArtefact) {
        super(versionableArtefact);
    }

    @Override
    protected ReportingTaxonomyMapImpl createMaintainableArtefact() {
        return new ReportingTaxonomyMapImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, ReportingTaxonomyMapImpl reportingTaxonomyMap) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.TARGET:
                getTargetSource(parser).ifPresent(reportingTaxonomyMap::setTarget);
                break;
            case StructureUtils.SOURCE:
                getTargetSource(parser).ifPresent(reportingTaxonomyMap::setSource);
                break;
            case StructureUtils.ITEM_MAPS:
                List<ItemMap> itemMaps = ReaderUtils.getArray(parser, (this::getItemMap));
                if (CollectionUtils.isNotEmpty(itemMaps)) {
                    reportingTaxonomyMap.setItemMaps(itemMaps);
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "ReportingTaxonomyMap: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.REPORTING_TAXONOMY_MAPS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<ReportingTaxonomyMapImpl> artefacts) {
        artefact.getReportingTaxonomyMaps().addAll(artefacts);
    }
}
