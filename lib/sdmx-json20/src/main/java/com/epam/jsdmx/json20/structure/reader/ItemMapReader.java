package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getFieldAsString;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getInstantObj;

import java.io.IOException;
import java.util.Optional;

import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.infomodel.sdmx30.ItemMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.lang3.StringUtils;

public interface ItemMapReader {

    default ItemMap getItemMap(JsonParser parser) {
        ItemMapImpl itemMap = new ItemMapImpl();
        try {
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.SOURCE_VALUE:
                        itemMap.setSource(getFieldAsString(parser));
                        break;
                    case StructureUtils.TARGET_VALUE:
                        itemMap.setTarget(getFieldAsString(parser));
                        break;
                    case StructureUtils.VALID_FROM:
                        itemMap.setValidFrom(getInstantObj(parser));
                        break;
                    case StructureUtils.VALID_TO:
                        itemMap.setValidTo(getInstantObj(parser));
                        break;
                    default:
                        AnnotableReader annotableReader = new AnnotableReader();
                        annotableReader.read(itemMap, parser);
                        break;
                }
            }
            return itemMap;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    default Optional<MaintainableArtefactReference> getTargetSource(JsonParser parser) throws IOException {
        String target = ReaderUtils.getStringJsonField(parser);
        if (StringUtils.isBlank(target)) {
            return Optional.empty();
        }
        return Optional.of(new MaintainableArtefactReference(target));
    }
}
