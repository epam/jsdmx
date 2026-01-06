package com.epam.jsdmx.infomodel.sdmx30;

/**
 * The following relationship types are defined by SDMX:
 * <ul>
 *  <li>
 *      <b>DataflowRelationship</b> - The value of the attribute is fixed for all data contained in the dataset.
 *      The Attribute value applies to all data defined by the underlying Dataflow.
 *      <br> The attribute is reported at the Dataset level.
 *  </li>
 *  <li>
 *      <b>Dimension (1..n)</b> - The value of the attribute will vary with the value(s) of the referenced Dimension(s).
 *      In this case, Group(s) to which the attribute should be attached may optionally be specified.
 *      <br> The attribute is reported at the lowest level of the Dimension to which the Attribute is related,
 *      otherwise at the level of the Group if Attachment Group(s) is specified.
 *  </li>
 *  <li>
 *      <b>Group</b> - The value of the Attribute varies with combination of values for all of the Dimensions contained in the Group.
 *      This is added as a convenience to listing all Dimensions and the attachment Group,
 *      but should only be used when the Attribute value varies based on all Group Dimension values.
 *      <br> The attribute is reported at the level of Group.
 *  </li>
 *  <li>
 *      <b>Observation</b> - The value of the Attribute varies with the observed value.
 *      <br> The attribute is reported at the level of Observation.
 *  </li>
 * </ul>
 */
public interface AttributeRelationship extends Copyable {
}
