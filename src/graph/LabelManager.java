package graph;

import feature.StreetSegment;

/**
 * Label Manager Interface.
 * 
 * @author Hunter Baker
 */
public interface LabelManager
{
  /**
   * Must adjust the Label at the head node of the given StreetSegment. It must invoke the
   * adjustValue method of the appropriate Label object so that the label is only update if its
   * value would be reduced.
   * 
   * @param segment
   */
  public void adjustHeadValue(StreetSegment segment);

  /**
   * Get the appropriate label.
   * 
   * @param intersectionID
   *          The intersectionID
   * @return The label
   */
  public Label getLabel(int intersectionID);
}
