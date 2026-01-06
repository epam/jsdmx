package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Category;
import com.epam.jsdmx.infomodel.sdmx30.CategoryScheme;

import org.apache.commons.collections.CollectionUtils;

public class CategorySchemeWriter extends XmlWriter<CategoryScheme> {

    public CategorySchemeWriter(NameableWriter nameableWriter,
                                AnnotableWriter annotableWriter,
                                CommonAttributesWriter commonAttributesWriter,
                                LinksWriter linksWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
    }

    @Override
    protected void writeAttributes(CategoryScheme categoryScheme, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(categoryScheme, writer);
        writer.writeAttribute(XmlConstants.IS_PARTIAL, String.valueOf(categoryScheme.isPartial()));
    }

    @Override
    protected void writeCustomAttributeElements(CategoryScheme categoryScheme, XMLStreamWriter writer) throws XMLStreamException {
        writeCategory(categoryScheme.getItems(), writer);
    }

    @Override
    protected String getName() {
        return XmlConstants.CATEGORY_SCHEME;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.CATEGORY_SCHEMES;
    }

    @Override
    protected Set<CategoryScheme> extractArtefacts(Artefacts artefacts) {
        return artefacts.getCategorySchemes();
    }

    private void writeCategory(List<? extends Category> items, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(items)) {
            for (Category category : items) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.CATEGORY);
                XmlWriterUtils.writeIdUriAttributes(writer, category.getId(), category.getUri());
                if (category.getContainer() != null) {
                    writer.writeAttribute(XmlConstants.URN, category.getUrn());
                }
                this.annotableWriter.write(category, writer);
                this.nameableWriter.write(category, writer);
                writeCategory(category.getHierarchy(), writer);
                writer.writeEndElement();
            }
        }
    }
}
