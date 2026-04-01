package graph;

/**
 * CandidateLabelManager interface.
 * 
 * @author Hunter Baker
 */
public interface CandidateLabelManager extends LabelManager
{
  /**
   * This must return an appropriate candidate label.
   * 
   * @return The appropriate label
   */
  public Label getCandidateLabel();
}
