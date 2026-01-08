package com.epam.jsdmx.json20.structure.writer;

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
