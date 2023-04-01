package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * The MetadataAttribute defines reference metadata that may be collected or disseminated
 * and is grouped together with DataAttribute under the AttributeDescriptor.
 */
public interface MetadataAttribute extends AttributeComponent {

    /**
     * @return Indication that the Metadata Attribute is present for structural purposes (i.e. it
     * has child attributes) and that no value for this attribute is expected to be reported in a Metadata Set.
     */
    boolean isPresentational();

    /**
     * @return Specifies how many occurrences of the Metadata Attribute may be
     * reported at this point in the Metadata Report.
     */
    int getMinOccurs();

    /**
     * @return Specifies how many occurrences of the Metadata Attribute may be
     * reported at this point in the Metadata Report.
     */
    int getMaxOccurs();

    /**
     * @return Association to one or more child Metadata Attribute
     */
    List<MetadataAttribute> getHierarchy();

    /**
     * @return True if occurrences of the Metadata Attribute is present
     */
    boolean isMandatory();
}
