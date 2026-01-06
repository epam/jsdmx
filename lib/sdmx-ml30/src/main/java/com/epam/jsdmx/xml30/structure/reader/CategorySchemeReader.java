package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Category;
import com.epam.jsdmx.infomodel.sdmx30.CategoryImpl;
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class CategorySchemeReader extends XmlReader<CategorySchemeImpl> {

    private final List<Category> categories = new ArrayList<>();

    public CategorySchemeReader(AnnotableReader annotableReader, NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected CategorySchemeImpl createMaintainableArtefact() {
        return new CategorySchemeImpl();
    }

    @Override
    protected CategorySchemeImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        CategorySchemeImpl categoryScheme = super.read(reader);
        categoryScheme.setItems(new ArrayList<>(categories));
        return categoryScheme;
    }

    @Override
    protected void read(XMLStreamReader reader, CategorySchemeImpl maintainableArtefact) throws URISyntaxException, XMLStreamException {
        String name = reader.getLocalName();
        if (!XmlConstants.CATEGORY.equals(name)) {
            throw new IllegalArgumentException("CategoryScheme " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
        }

        Category categoryList = addCategory(reader);
        categories.add(categoryList);
    }

    @Override
    protected String getName() {
        return XmlConstants.CATEGORY_SCHEME;
    }

    @Override
    protected String getNames() {
        return XmlConstants.CATEGORY_SCHEMES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<CategorySchemeImpl> artefacts) {
        artefact.getCategorySchemes().addAll(artefacts);
    }

    private Category addCategory(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        List<Category> hierarchy = new ArrayList<>();
        var category = new CategoryImpl();
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(category::setId);

        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            category.setUri(new URI(uri));
        }

        Map<String, String> names = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.CATEGORY)) {
            String name = reader.getLocalName();
            switch (name) {
                case XmlConstants.COM_ANNOTATIONS:
                    this.annotableReader.setAnnotations(reader, category);
                    break;
                case XmlConstants.COM_NAME:
                    this.nameableReader.setNameable(reader, names);
                    break;
                case XmlConstants.COM_DESCRIPTION:
                    this.nameableReader.setNameable(reader, descriptions);
                    break;
                case XmlConstants.CATEGORY:
                    hierarchy.add(addCategory(reader));
                    break;
                default:
                    throw new IllegalArgumentException("Category " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
            }
            moveToNextTag(reader);
        }
        category.setName(names.isEmpty() ? null : new InternationalString(names));
        category.setDescription(descriptions.isEmpty() ? null : new InternationalString(descriptions));
        category.setHierarchy(hierarchy);
        return category;
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, CategorySchemeImpl categoryScheme) throws XMLStreamException {
        XmlReaderUtils.setCommonAttributes(reader, categoryScheme);
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_PARTIAL))
            .map(Boolean::parseBoolean)
            .ifPresent(categoryScheme::setPartial);
    }

    @Override
    protected void clean() {
        categories.clear();
    }
}
