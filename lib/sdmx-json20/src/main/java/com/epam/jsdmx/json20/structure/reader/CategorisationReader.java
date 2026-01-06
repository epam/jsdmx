package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CategorisationImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.lang3.StringUtils;

public class CategorisationReader extends MaintainableReader<CategorisationImpl> {

    public CategorisationReader(VersionableReader versionableArtefact) {
        super(versionableArtefact);
    }

    @Override
    protected CategorisationImpl createMaintainableArtefact() {
        return new CategorisationImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, CategorisationImpl categorisation) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.TARGET:
                getTarget(parser).ifPresent(categorisation::setCategorizedBy);
                break;
            case StructureUtils.SOURCE:
                getSource(parser).ifPresent(categorisation::setCategorizedArtefact);
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "Categorisation: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.CATEGORISATIONS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<CategorisationImpl> artefacts) {
        artefact.getCategorisations().addAll(artefacts);
    }

    private Optional<IdentifiableArtefactReferenceImpl> getTarget(JsonParser parser) throws IOException {
        String target = ReaderUtils.getStringJsonField(parser);
        if (StringUtils.isBlank(target)) {
            return Optional.empty();
        }
        return Optional.of(new IdentifiableArtefactReferenceImpl(target));
    }

    private Optional<MaintainableArtefactReference> getSource(JsonParser parser) throws IOException {
        String source = ReaderUtils.getStringJsonField(parser);
        if (StringUtils.isBlank(source)) {
            return Optional.empty();
        }
        return Optional.of(new IdentifiableArtefactReferenceImpl(source));
    }

}
