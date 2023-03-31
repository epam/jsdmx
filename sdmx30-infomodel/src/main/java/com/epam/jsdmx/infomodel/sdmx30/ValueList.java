package com.epam.jsdmx.infomodel.sdmx30;

/**
 * A list from which some statistical concepts (enumerated concepts) take
 * their values.
 */
public interface ValueList extends EnumeratedList<ValueItem> {
    @Override
    ValueList toStub();

    @Override
    ValueList toCompleteStub();
}
