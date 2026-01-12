package com.epam.jsdmx.xml30.structure.reader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MappedComponent {
    private String source;
    private String target;
}
