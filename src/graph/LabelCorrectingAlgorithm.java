package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import feature.Intersection;
import feature.StreetSegment;

/**
 * Label Correcting Algorithm.
 *
 * @author Hunter Baker
 */
public class LabelCorrectingAlgorithm extends AbstractShortestPathAlgorithm
{
  private CandidateLabelManager labels;

  /**
   * Constructor.
   *
   * @param labels
   *          The CandidateLabelManager to use
   */
  public LabelCorrectingAlgorithm(final CandidateLabelManager labels)
  {
    super();
    this.labels = labels;
  }

  @Override
  public Map<String, StreetSegment> findPath(final int origin, final int destination,
      final StreetNetwork net)
  {
    // setup our strutctures
    Map<String, StreetSegment> result = new HashMap<String, StreetSegment>();
    List<String> highlightIDs = new ArrayList<String>();

    // init all values to infnity
    for (int i = 0; i < net.size(); i++)
    {
      labels.getLabel(i).setValue(Double.MAX_VALUE);
    }

    // init the origin label to 0
    Label originLabel = labels.getLabel(origin);
    originLabel.adjustValue(0.0, null);

    // for as many iterations as there are vertices
    for (int i = 0; i < net.size() - 1; i++)
    {
      highlightIDs.clear();
      boolean anyLabelChanged = false;

      for (int nodeID = 0; nodeID < net.size(); nodeID++)
      {
        Label nodeLabel = labels.getLabel(nodeID);

        // skip bad
        if (nodeLabel.getValue() == Double.MAX_VALUE)
        {
          continue;
        }

        Intersection intersection = net.getIntersection(nodeID);
        if (intersection != null)
        {
          // outbound edges
          for (StreetSegment segment : intersection.getOutbound())
          {
            labels.adjustHeadValue(segment);
            int headID = segment.getHead();
            StreetSegment predecessor = labels.getLabel(headID).getPredecessor();
            if (predecessor != null && predecessor.equals(segment))
            {
              anyLabelChanged = true;
              highlightIDs.add(segment.getID());
            }
          }
        }
      }
      notifyStreetSegmentObservers(highlightIDs);

      if (!anyLabelChanged)
      {
        break;
      }
    }
    int currentID = destination;

    if (labels.getLabel(destination).getValue() == Double.MAX_VALUE)
    {
      return new HashMap<String, StreetSegment>();
    }

    while (currentID != origin)
    {
      StreetSegment segment = labels.getLabel(currentID).getPredecessor();
      if (segment != null)
      {
        result.put(segment.getID(), segment);
        currentID = segment.getTail();
      }
      else
      {
        return new HashMap<String, StreetSegment>();
      }
    }

    return result;
  }
}
