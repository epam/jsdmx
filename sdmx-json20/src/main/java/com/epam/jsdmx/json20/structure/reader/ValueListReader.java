package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getLocalizedField;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.ValueItem;
import com.epam.jsdmx.infomodel.sdmx30.ValueItemImpl;
import com.epam.jsdmx.infomodel.sdmx30.ValueListImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class ValueListReader extends MaintainableReader<ValueListImpl> {

    private final AnnotableReader annotableReader;

    public ValueListReader(VersionableReader versionableArtefact,
                           AnnotableReader annotableReader) {
        super(versionableArtefact);
        this.annotableReader = annotableReader;
    }

    @Override
    protected ValueListImpl createMaintainableArtefact() {
        return new ValueListImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, ValueListImpl valueList) throws IOException {
        String fieldName = parser.getCurrentName();
        if (StructureUtils.VALUE_ITEMS.equals(fieldName)) {
            List<ValueItem> valueItems = ReaderUtils.getArray(parser, (this::getValueItem));
            if (CollectionUtils.isNotEmpty(valueItems)) {
                valueList.setItems(valueItems);
            }
        } else {
            throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "ValueList: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.VALUE_LISTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<ValueListImpl> artefacts) {
        artefact.getValueLists().addAll(artefacts);
    }

    private ValueItem getValueItem(JsonParser parser) {
        try {
            ValueItemImpl valueItem = new ValueItemImpl();
            while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
                ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.ID:
                        parser.nextToken();
                        String id = parser.getText();
                        valueItem.setId(id);
                        break;
                    case StructureUtils.NAMES:
                        Map<String, String> localizedNames = getLocalizedField(parser);
                        if (localizedNames != null) {
                            valueItem.setName(new InternationalString(localizedNames));
                        }
                        break;
                    case StructureUtils.DESCRIPTIONS:
                        Map<String, String> localizedDescriptions = getLocalizedField(parser);
                        if (localizedDescriptions != null) {
                            valueItem.setDescription(new InternationalString(localizedDescriptions));
                        }
                        break;
                    case StructureUtils.DESCRIPTION:
                    case StructureUtils.NAME:
                        parser.nextToken();
                        parser.skipChildren();
                        break;
                    default:
                        annotableReader.read(valueItem, parser);
                        break;
                }
            }
            return valueItem;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }
}