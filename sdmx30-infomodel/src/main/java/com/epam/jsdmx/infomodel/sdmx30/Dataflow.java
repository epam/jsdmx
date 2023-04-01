package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Abstract concept (i.e., the structure without any data) of a flow of data that
 * providers will provide for different reference periods. Each Dataflow has a maximum of one
 * DataStructureDefinition specified which defines the structure of any DataSets to be reported/disseminated.
 */
public interface Dataflow extends StructureUsage, ConstrainableArtefact {
    @Override
    Dataflow toStub();

    @Override
    Dataflow toCompleteStub();
}
