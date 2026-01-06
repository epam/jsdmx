package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Wrapper around {@link Cloneable} to define a clone method without checked exception
 */
public interface Copyable extends Cloneable {

    Object clone();

}
