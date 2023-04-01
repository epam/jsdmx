package com.epam.jsdmx.infomodel.sdmx30;

/**
 * The descriptive information for an arrangement or division of categories
 * into groups based on characteristics, which the objects have in common
 */
public interface CategoryScheme extends ItemScheme<Category> {
    @Override
    CategoryScheme toStub();

    @Override
    CategoryScheme toCompleteStub();
}
