![CI](https://github.com/epam/jsdmx/actions/workflows/ci.yml/badge.svg)
# jSDMX: Java Library for SDMX 3.0

Based on the latest version of the [SDMX 3.0 Specifications](https://sdmx.org/?page_id=5008), this library provides Java classes and utilities for working with the SDMX 3.0 Information Model. It is designed to help developers implement SDMX-compliant applications for exchanging
statistical data and metadata among international organizations, central banks, and national statistical institutes.

## Packages
- `sdmx30-infomodel` module contains java classes which represent SDMX 3.0 Information Model (Structure Artifacts) such as Data Structure Definitions (DSDs), Metadata Structure Definitions (MSDs), Code Lists, Concept Schemes, and Data and Metadata flows and others. 
- `sdmx-json10` module providing serialization to JSON 1.0 format for SDMX 2.1
- `sdmx-json20` module providing serialization to JSON 2.0 format for SDMX 3.0
- `sdmx-ml21` module providing serialization to ML 2.1 format for SDMX 2.1
- `sdmx-ml30` module providing serialization to ML 3.0 format for SDMX 3.0


## Requirements

- Java JDK 17 and higher

## Installation

### sdmx30-infomodel

#### Maven:
    <dependency>
        <groupId>com.epam.jsdmx</groupId>
        <artifactId>sdmx30-infomodel</artifactId>
        <version>2.0.0</version>
    </dependency>

#### Gradle:
    compile(group: 'com.epam.jsdmx', name: 'sdmx30-infomodel', version: '2.0.0')

### sdmx-json10

#### Maven:
    <dependency>
        <groupId>com.epam.jsdmx</groupId>
        <artifactId>sdmx-json10</artifactId>
        <version>2.0.0</version>
    </dependency>

#### Gradle:
    compile(group: 'com.epam.jsdmx', name: 'sdmx-json10', version: '2.0.0')

### sdmx-json20

#### Maven:
    <dependency>
        <groupId>com.epam.jsdmx</groupId>
        <artifactId>sdmx-json20</artifactId>
        <version>2.0.0</version>
    </dependency>

#### Gradle:
    compile(group: 'com.epam.jsdmx', name: 'sdmx-json20', version: '2.0.0')

### sdmx-ml21

#### Maven:
    <dependency>
        <groupId>com.epam.jsdmx</groupId>
        <artifactId>sdmx-ml21</artifactId>
        <version>2.0.0</version>
    </dependency>

#### Gradle:
    compile(group: 'com.epam.jsdmx', name: 'sdmx-ml21', version: '2.0.0')


### sdmx-ml30

#### Maven:
    <dependency>
        <groupId>com.epam.jsdmx</groupId>
        <artifactId>sdmx-ml30</artifactId>
        <version>2.0.0</version>
    </dependency>

#### Gradle:
    compile(group: 'com.epam.jsdmx', name: 'sdmx-ml30', version: '2.0.0')

## License
Project is distributed under the Apache License 2.0. See [LICENSE](LICENSE) for more information.
