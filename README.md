# jSDMX: Java Library for SDMX 3.0

Based on the latest version of the [SDMX 3.0 Specifications](https://sdmx.org/?page_id=5008), this library provides Java classes and utilities for working with the SDMX 3.0 Information Model. It is designed to help developers implement SDMX-compliant applications for exchanging
statistical data and metadata among international organizations, central banks, and national statistical institutes.

## Packages
- `sdmx30-infomodel` module contains java classes which represent SDMX 3.0 Information Model (Structure Artifacts) such as Data Structure Definitions (DSDs), Metadata Structure Definitions (MSDs), Code Lists, Concept Schemes, and Data and Metadata flows and others.

## Roadmap

The following modules are currently on project's roadmap for 2023:
- `sdmx30-serialization` module providing (de)serialization to SDMX-JSON, SDMX-ML and SDMX-CSV formats for data and metadata.
- `sdmx30-reporting` module with models describing to Data and Metadata Reporting.
- `sdmx30-client` REST client for SDMX registry.

Listed above is a subject for extension and other modules might be implemented as well.

## Requirements

- Java JDK 11 and higher

## Installation

### sdmx30-infomodel

#### Maven:
    <dependency>
        <groupId>com.epam.jsdmx</groupId>
        <artifactId>sdmx30-infomodel</artifactId>
        <version>1.0.0</version>
    </dependency>

#### Gradle:
    compile(group: 'com.epam.jsdmx', name: 'sdmx30-infomodel', version: '1.0.0')

## License
Project is distributed under the Apache License 2.0. See [LICENSE](LICENSE) for more information.
