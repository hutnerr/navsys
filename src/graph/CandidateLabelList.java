package graph;

import java.util.ArrayList;
import java.util.List;

import feature.StreetSegment;

/**
 * CandidateLabelList class.
 *
 * @author Hunter Baker
 */
public class CandidateLabelList extends AbstractLabelManager implements CandidateLabelManager
{
  public static final String OLDEST = "O";
  public static final String NEWEST = "N";
  private List<Integer> candidates;
  private String policy;

  /**
   * Constructor.
   *
   * @param policy
   *          The Policy to use (OLDEST for FIFO, NEWEST for LIFO)
   * @param networkSize
   *          The network size to use
   */
  public CandidateLabelList(final String policy, final int networkSize)
  {
    super(networkSize);
    this.policy = policy;
    this.candidates = new ArrayList<Integer>();

    // init all to infinity
    for (int i = 0; i < networkSize; i++)
    {
      labels[i].setValue(Double.MAX_VALUE);
    }
  }

  @Override
  public void adjustHeadValue(final StreetSegment segment)
  {
    int tailID = segment.getTail();
    int headID = segment.getHead();

    Label tailLabel = getLabel(tailID);
    Label headLabel = getLabel(headID);

    if (tailLabel.getValue() == Double.MAX_VALUE)
    {
      return;
    }

    double newValue = tailLabel.getValue() + segment.getLength();

    // if we found a better path
    if (newValue < headLabel.getValue())
    {
      headLabel.adjustValue(newValue, segment);
    }
  }

  @Override
  public Label getCandidateLabel()
  {
    if (candidates.isEmpty())
    {
      return null;
    }

    int intersectionID;
    if (policy.equals(NEWEST))
    {
      intersectionID = candidates.remove(candidates.size() - 1);
    }
    else
    {
      intersectionID = candidates.remove(0);
    }

    return getLabel(intersectionID);
  }
}
