package graph;

/**
 * PermanentLabelManager.
 * 
 * @author Hunter Baker
 */
public interface PermanentLabelManager extends LabelManager
{
  /**
   * Get the smallest label.
   * 
   * @return The smallest label
   */
  public Label getSmallestLabel();

  /**
   * Make a label permanent.
   * 
   * @param intersectionID
   */
  public void makePermanent(int intersectionID);
}
