package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getStringJsonField;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MaintainableReader<T extends MaintainableArtefactImpl> {

    private final VersionableReader versionableArtefact;

    protected MaintainableReader(VersionableReader versionableArtefact) {
        this.versionableArtefact = versionableArtefact;
    }

    protected abstract T createMaintainableArtefact();

    public abstract void readArtefact(JsonParser parser, T maintainableArtefact) throws IOException;

    public T read(JsonParser parser) throws IOException {
        T maintainable = createMaintainableArtefact();
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            if (!checkIsNotEmptyObjectAndSkipUntilFieldName(parser)) {
                return null;
            }
            String fieldname = parser.getCurrentName();
            switch (fieldname) {
                case StructureUtils.ID:
                case StructureUtils.ANNOTATIONS:
                case StructureUtils.VERSION:
                case StructureUtils.VALID_TO:
                case StructureUtils.VALID_FROM:
                case StructureUtils.NAMES:
                case StructureUtils.DESCRIPTIONS:
                case StructureUtils.DESCRIPTION:
                case StructureUtils.NAME:
                    versionableArtefact.read(maintainable, parser);
                    break;
                case StructureUtils.AGENCY_ID:
                    maintainable.setOrganizationId(getStringJsonField(parser));
                    break;
                case StructureUtils.LINKS:
                    parser.nextToken();
                    parser.skipChildren();
                    break;
                case StructureUtils.IS_EXTERNAL_REFERENCE:
                    parser.nextToken();
                    break;
                default:
                    readArtefact(parser, maintainable);
            }
        }
        return maintainable;
    }

    public MaintainableArtefactImpl readAndClose(JsonParser parser) throws IOException {
        try (parser) {
            return read(parser);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    protected abstract String getName();

    protected abstract void setArtefacts(Artefacts artefact, Set<T> artefacts);

    protected void clean() {
        // individual for each artefact
    }
}
