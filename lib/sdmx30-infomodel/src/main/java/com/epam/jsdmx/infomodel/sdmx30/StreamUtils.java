package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Collection;
import java.util.stream.Stream;

public final class StreamUtils {
    private StreamUtils() { }

    public static <T> Stream<T> streamOfNullable(Collection<T> nullableCollection) {
        return Stream.ofNullable(nullableCollection)
            .flatMap(Collection::stream);
    }

}
